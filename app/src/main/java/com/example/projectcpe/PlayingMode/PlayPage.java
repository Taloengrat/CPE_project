package com.example.projectcpe.PlayingMode;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectcpe.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PlayPage extends AppCompatActivity {

    private int ms = 0;
    private int seconds = 0;
    private int minutes = 0;

    private TextView textclock,timecount;
    ImageView recogni, back, next, voice, help,start;
    private Timer timer;
    private CountDownTimer countDownTimer;

    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        Initial();




    }

    public void Initial(){
        LinearLayout navigation = findViewById(R.id.bar);
        recogni = findViewById(R.id.mic);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        help = findViewById(R.id.hint);
        voice = findViewById(R.id.speak);
        start = findViewById(R.id.startClick);
        timecount =findViewById(R.id.counttime);




        textclock = findViewById(R.id.textClock);


        recogni.setOnTouchListener(Hilight);
        back.setOnTouchListener(Hilight);
        next.setOnTouchListener(Hilight);
        help.setOnTouchListener(Hilight);
        voice.setOnTouchListener(Hilight);


        start.setOnClickListener(startTimeBegin);
    }

    private void stopTimer(){
        running = false;
//        btnStart.setText("Start");
        timer.cancel();
    }

    private void countDownTimer(){
        countDownTimer = new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                timecount.setText(String.valueOf(millisUntilFinished / 1000) );
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timecount.setText("Start !!!");

                startTimer();
            }

        }.start();
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
        textclock.setText(String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds,ms));
    }

    private Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            updateTimerText();
            updateMs();
        }
    };


    View.OnTouchListener Hilight = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {


            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN :

                    Log.d("motionEvent","Action was DOWN");
                    view.setBackgroundResource(R.drawable.fram_hilight);
//                    speechRecognizer.startListening(speechRecognizerIntent);
//                    Keeper = "";
                    return true;

                case MotionEvent.ACTION_UP :
                    Log.d("motionEvent","Action was UP");
                    view.setBackgroundResource(R.drawable.fram_unhilight);
//                    speechRecognizer.stopListening();
                    return true;
                default :
                    return false;
            }
        }
    };

    View.OnClickListener startTimeBegin = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            view.setVisibility(View.GONE);
           timecount.setVisibility(View.VISIBLE);
           countDownTimer();

        }
    };

}
