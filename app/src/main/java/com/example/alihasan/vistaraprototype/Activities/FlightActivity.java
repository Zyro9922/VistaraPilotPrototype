package com.example.alihasan.vistaraprototype.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.vistaraprototype.R;
import com.example.alihasan.vistaraprototype.Singleton.UploadSingleton;

public class FlightActivity extends AppCompatActivity {

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
                    Intent i = new Intent(FlightActivity.this,CounterActivity.class);
                    startActivity(i);
                }

                else    Toast.makeText(FlightActivity.this, "EMPTY", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.uploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable())
                {
                    if(counter.getCounter() == 1)
                        Toast.makeText(FlightActivity.this, "UPLOAD SUCCESSFUL", Toast.LENGTH_SHORT).show();

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
}
