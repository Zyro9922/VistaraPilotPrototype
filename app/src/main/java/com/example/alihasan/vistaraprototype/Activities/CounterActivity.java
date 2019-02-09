package com.example.alihasan.vistaraprototype.Activities;

import com.example.alihasan.vistaraprototype.Singleton.UploadSingleton;
import com.example.alihasan.vistaraprototype.Util.TimeFormatUtil;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alihasan.vistaraprototype.R;

import java.util.Timer;
import java.util.TimerTask;

public class CounterActivity extends AppCompatActivity {

    private Button startButton;
    private TextView textView;

    private Timer timer;
    private int currentTime = 0;
    private int mId = 1;
    private boolean isButtonStartPressed = false;

    private UploadSingleton counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        counter = UploadSingleton.getInstance();
        counter.setFalse();

        startButton = findViewById(R.id.btn_start);
        textView = findViewById(R.id.stopwatch_view);
        textView.setTextSize(55);

    }

    public void onSWatchStart(View view) {
        if (isButtonStartPressed) {
            onSWatchStop();
        } else {
            isButtonStartPressed = true;

            startButton.setBackgroundResource(R.drawable.btn_stop_states);
            startButton.setText(R.string.btn_stop);

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            currentTime += 1;

                            // update ui
                            textView.setText(TimeFormatUtil.toDisplayString(currentTime));
                        }
                    });
                }
            }, 0, 10);
        }
    }

    public void onSWatchStop() {
        customDialog("Warning","End Flight Captain?");
    }

    public void customDialog(String title, String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.ic_cancel);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogNo();
                    }
                });

        builderSingle.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogYes();
                    }
                });

        builderSingle.show();
    }

    public void dialogYes()
    {
        isButtonStartPressed = false;
        timer.cancel();

        counter.setTrue();
        Intent i = new Intent(CounterActivity.this,FlightActivity.class);
        startActivity(i);
    }

    public void dialogNo()
    {
        Toast.makeText(CounterActivity.this, "Don’t land until you get to the airport Captain", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
