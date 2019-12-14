package com.example.projectcpe.PlayingMode;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projectcpe.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PlayPage extends AppCompatActivity {

    private int ms = 0;
    private int seconds = 0;
    private int minutes = 0;

    private TextView textclock;
    private Timer timer;

    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        Initial();

    }

    public void Initial(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(2);

        textclock = findViewById(R.id.textClock);
    }

    private void stopTimer(){
        running = false;
//        btnStart.setText("Start");
        timer.cancel();
    }

    private void startTimer(){
        timer = new Timer();
//        btnStart.setText("Stop");
        running = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runTimer();
            }
        }, 0, 100);
    }

    private void runTimer(){
        this.runOnUiThread(timerTick);
    }

    private void updateMs(){
        ms++;
        if(ms == 10){
            updateSeconds();
        }
    }

    private void updateSeconds(){
        ms = 0;
        seconds++;
        if(seconds == 60){
            seconds = 0;
            minutes++;
        }
    }

    private void updateTimerText(){
        textclock.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d", minutes, seconds,ms));
    }

    private Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            updateTimerText();
            updateMs();
        }
    };
}
