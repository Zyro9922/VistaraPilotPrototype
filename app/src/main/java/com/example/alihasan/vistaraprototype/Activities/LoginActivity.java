package com.example.alihasan.vistaraprototype.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.vistaraprototype.R;
import com.example.alihasan.vistaraprototype.Service.Client;
import com.example.alihasan.vistaraprototype.Service.ServerURL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;

    private static String EMAIL;

    static String SERVER_URL = new ServerURL().getSERVER_URL();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.userNameEditText);
        pass = findViewById(R.id.passwordEditText);

        if(getSharedPreferences("LOGINDATA", Context.MODE_PRIVATE).getString("EMAIL", "")!=null &&
            !getSharedPreferences("LOGINDATA", Context.MODE_PRIVATE).getString("EMAIL", "").equals("")){

            Intent i = new Intent(LoginActivity.this,FlightActivity.class);
            startActivity(i);

        }

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Client client = retrofit.create(Client.class);

                if(isNetworkAvailable())
                {
                    Call<String> call = client.getAuth(email.getText().toString(),pass.getText().toString());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            if(response.body()==null)
                            {
                                Toast.makeText(getApplicationContext(), "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
                            }

                            else if(response.body().equals("Success"))
                            {

                                SharedPreferences loginData = getSharedPreferences("LOGINDATA", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = loginData.edit();
                                editor.putString("EMAIL", email.getText().toString().trim());
                                editor.putString("PASS",pass.getText().toString().trim());
                                editor.apply();

                                Intent i = new Intent(LoginActivity.this,FlightActivity.class);
                                EMAIL = email.getText().toString();
                                startActivity(i);
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "INV U/P" + response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), "No Internet/FAILURE", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                else Toast.makeText(LoginActivity.this, "YOU'RE OFFLINE", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getEMAIL(){
        return EMAIL;
    }
}
