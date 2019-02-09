package com.example.alihasan.vistaraprototype.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alihasan.vistaraprototype.R;

public class LoginActivity extends AppCompatActivity {

    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.userNameEditText);
        pass = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable())
                {
                    if(user.getText().toString().equals("PILOT123") && pass.getText().toString().equals("PASS123"))
                    {
                        Intent i = new Intent(LoginActivity.this,FlightActivity.class);
                        startActivity(i);
                    }

                    else    Toast.makeText(LoginActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
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
}
