package com.example.alihasan.vistaraprototype.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.vistaraprototype.R;
import com.example.alihasan.vistaraprototype.Service.Client;
import com.example.alihasan.vistaraprototype.Service.ServerURL;
import com.example.alihasan.vistaraprototype.Singleton.UploadSingleton;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlightActivity extends AppCompatActivity {

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    EditText flightNo;

    private UploadSingleton counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        flightNo = findViewById(R.id.flightNoEditText);
        counter = UploadSingleton.getInstance();

        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flightNo.getText().toString().isEmpty())
                {
                    SharedPreferences p=getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor edit=p.edit();
                    edit.putString("value",flightNo.getText().toString());
                    edit.apply();

                    Intent i = new Intent(FlightActivity.this,CounterActivity.class);
                    startActivity(i);
                }

                else    Toast.makeText(FlightActivity.this, "EMPTY", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.uploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences p=getSharedPreferences("data",MODE_PRIVATE);
                String value=p.getString("value",null);
                if(value!=null){
                    flightNo.setText(value);
                    flightNo.setEnabled(false);
                }

                String EMAIL = LoginActivity.getEMAIL();
                String FLIGHTNO = flightNo.getText().toString();
                int flightTime = CounterActivity.flightTime();

                float hundreds,seconds,minutes,hours;
                hundreds=flightTime%100;
                seconds=(flightTime/=100)%60;
                minutes=(flightTime/=60)%60;
                hours = (flightTime/=60)%60;
                float display = hours+(minutes/60);
                String HOURS = String.format("%.2f", display);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Client client = retrofit.create(Client.class);

                if(isNetworkAvailable())
                {
                    if(counter.getCounter() == 1){
                        Call<String> call = client.flightData(EMAIL,FLIGHTNO,HOURS);

                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.body()==null)
                                {
                                    Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().equals("Success"))
                                {
                                    SharedPreferences preferences =getSharedPreferences("data",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    editor.apply();
                                    finish();

                                    Toast.makeText(getApplicationContext(), "SUCCESSFULLY UPLOADED  ", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG "+response.body(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }


                    else Toast.makeText(FlightActivity.this, "Cannot Upload", Toast.LENGTH_SHORT).show();
                }

                else Toast.makeText(FlightActivity.this, "YOU'RE OFFLINE", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:

                SharedPreferences preferences =getSharedPreferences("LOGINDATA",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();

                Toast.makeText(FlightActivity.this, "LOGGED OUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(FlightActivity.this,LoginActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
