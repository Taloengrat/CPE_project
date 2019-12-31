package com.example.projectcpe.PlayingMode;

import com.example.projectcpe.Adapter.SlidePageAdapter;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewPage.CustomViewPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PlayPage extends AppCompatActivity {

    private int ms = 0;
    private int seconds = 0;
    private int minutes = 0;
    float Score;
    float SumScore;

    private SlidePageAdapter adapter;
    private TextView textclock,timecount, Answer;
    ImageView recogni, back, next, voice, help,start;
    private Timer timer;
    private CountDownTimer countDownTimer;
    ViewPager pager ;
    ImageView check;

    boolean threadreset;
    EditText putAnwer ;
    protected int id, stepnum;
    protected boolean running = false;

    SpeechRecognizer speechRecognizer;
    Intent speechRecognizerIntent;
    String Keeper;
    private String[] textReturn,ScoreString;
    int memberId ;
    List<Mission> missionList;
    List<String> stringList,scoreList;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        Initial();


//        Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> matchesFound = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if (matchesFound != null)
                {
                    Keeper = matchesFound.get(0);
                    Answer.setVisibility(View.VISIBLE);
                    Answer.setText(Keeper);
                    getAnswerFun(pager.getCurrentItem());
                    VerifyAnswer(stringList , Keeper.toLowerCase());

//                    Bundle bundleAnswer = new Bundle();
//                    bundleAnswer.putString("answerATV", Answer.getText().toString().trim());
//
//                    OneFragment oneFragment = new OneFragment();
//                    oneFragment.setArguments(bundleAnswer);

//                    Toast.makeText(getApplicationContext(), "Resault = "+ Keeper, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

    }

    View.OnTouchListener Hilight = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {


            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN :

                    Log.d("motionEvent","Action was DOWN");
                    view.setBackgroundResource(R.drawable.fram_hilight);
                    speechRecognizer.startListening(speechRecognizerIntent);
                    Keeper = "";
                    return true;

                case MotionEvent.ACTION_UP :
                    Log.d("motionEvent","Action was UP");
                    view.setBackgroundResource(R.drawable.fram_unhilight);
                    speechRecognizer.stopListening();
                    return true;
                default :
                    return false;
            }
        }
    };

    private void VerifyAnswer(List<String> missionAnswer, String userAnswer)  {
        if (missionAnswer.contains(userAnswer)) {
            CorrectStep();
            getScore(pager.getCurrentItem());

            switch (Integer.valueOf(missionAnswer.indexOf(userAnswer))){
                case 0 :
                    Score = Float.valueOf(ScoreString[0]);

                    break;
                case 1 :
                    Score = Float.valueOf(ScoreString[1]);

                    break;
                case 2 :
                    Score = Float.valueOf(ScoreString[2]);

                    break;
                case 3 :
                    Score = Float.valueOf(ScoreString[3]);

                    break;
                case 4 :
                    Score = Float.valueOf(ScoreString[4]);

                    break;
                case 5 :
                    Score = Float.valueOf(ScoreString[5]);

                    break;
                case 6 : Score = Float.valueOf(ScoreString[6]);
                    break;

            }

            SumScore+=Score;
            Toast.makeText(getApplicationContext(), "point this step : "+String.valueOf(Score)
                    +"\n All Score : "+SumScore, Toast.LENGTH_SHORT).show();



        }else {
            WrongStep();
        }

        Runnable Delay = new Runnable() {
            @Override
            public void run() {
                check.setVisibility(View.GONE);
            }
        };

        Handler pd = new Handler();
        pd.postDelayed(Delay, 2000);
    }

    private void WrongStep() {
        check.setVisibility(View.VISIBLE);
        check.setImageResource(R.drawable.wrong);
//        pager.setCurrentItem(pager.getCurrentItem()-1);
        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
    }

    private void CorrectStep() {
        check.setVisibility(View.VISIBLE);
        check.setImageResource(R.drawable.correct);




        Runnable Delay = new Runnable() {
            @Override
            public void run() {


                if (pager.getCurrentItem()+1 == missionList.get(0).getNumberofMission()){
                    Toast.makeText(PlayPage.this, "end of step", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PlayPage.this, SumaryPage.class);
                    i.putExtra("IDmission", id);
                    i.putExtra("score", SumScore);
                    i.putExtra("memberId", memberId);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else{
                    check.setVisibility(View.GONE);
                    pager.setCurrentItem(pager.getCurrentItem()+1);
                    Answer.setText("");
                    Answer.setVisibility(View.INVISIBLE);
                    recogni.setOnTouchListener(Hilight);
                    textclock.setTextColor(getResources().getColor(R.color.black));
                    startTimer();
                }


            }
        };

        Handler pd = new Handler();
        pd.postDelayed(Delay, 2000);
    }

    private void SettingRecognizi() {

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(PlayPage.this);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

    private void ShowViewPage()
    {
        adapter = new SlidePageAdapter(getSupportFragmentManager(), id, stepnum, getData(id));
        pager.setAdapter(adapter);
    }
    public void Initial(){

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("missionId");
        stepnum = bundle.getInt("step");
        memberId = bundle.getInt("memberId");


        check = findViewById(R.id.check);
        recogni = findViewById(R.id.mic);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        help = findViewById(R.id.hint);
        voice = findViewById(R.id.speak);
        start = findViewById(R.id.startClick);
        timecount =findViewById(R.id.counttime);
        pager = findViewById(R.id.ViewPage);
        Answer = findViewById(R.id.answer);
        textclock = findViewById(R.id.textClock);

        missionList = getData(id);

//        getAnswerFun(id);

        SettingRecognizi();

        recogni.setOnTouchListener(Hilight);
        back.setOnTouchListener(Hilight);
        next.setOnTouchListener(Hilight);
        help.setOnTouchListener(Hilight);
        voice.setOnTouchListener(Hilight);

        start.setOnClickListener(startTimeBegin);




    }

    private void getScore(int position) {

        switch (position) {
            case 0 : ScoreString = missionList.get(0).getS1().split("/");
                break;
            case 1 : ScoreString = missionList.get(0).getS2().split("/");
                break;
            case 2 : ScoreString = missionList.get(0).getS3().split("/");
                break;
            case 3 : ScoreString = missionList.get(0).getS4().split("/");
                break;
            case 4 : ScoreString = missionList.get(0).getS5().split("/");
                break;
            case 5 : ScoreString = missionList.get(0).getS6().split("/");
                break;
            case 6 : ScoreString = missionList.get(0).getS7().split("/");
                break;
            case 7 : ScoreString = missionList.get(0).getS8().split("/");
                break;
            case 8 : ScoreString = missionList.get(0).getS9().split("/");
                break;
            case 9 : ScoreString = missionList.get(0).getS10().split("/");
                break;
        }
        scoreList = Arrays.asList(ScoreString);
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

                Runnable Delay = new Runnable() {
                    @Override
                    public void run() {

                        timecount.setVisibility(View.GONE);
                        startTimer();
                        ShowViewPage();

                    }
                };

                Handler pd = new Handler();
                pd.postDelayed(Delay, 2000);


            }

        }.start();
    }

    private void startTimer(){

        timer = new Timer();

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


            textclock.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));

    }

    private Runnable timerTick = new Runnable() {
        @Override
        public void run() {

            if (minutes >= 1 || threadreset) {
                stopTimer();
                recogni.setOnTouchListener(null);
                textclock.setText(String.format(Locale.getDefault(), "%02d:%02d", 1, 0));
                textclock.setTextColor(getResources().getColor(R.color.red600));
            }
            else {
                updateTimerText();
                updateMs();
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

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(PlayPage.this).missionDAO().getAllinfoOfMission(id);
    }

    private void getAnswerFun(int position){
Toast.makeText(getApplicationContext(), String.valueOf(pager.getCurrentItem()), Toast.LENGTH_SHORT).show();
        switch (position){
            case 0 : textReturn = missionList.get(0).getA1().toLowerCase().split("/");
                break;
            case 1 : textReturn = missionList.get(0).getA2().toLowerCase().split("/");
                break;
            case 2 : textReturn = missionList.get(0).getA3().toLowerCase().split("/");
                break;
            case 3 : textReturn = missionList.get(0).getA4().toLowerCase().split("/");
                break;
            case 4 : textReturn = missionList.get(0).getA5().toLowerCase().split("/");
                break;
            case 5 : textReturn = missionList.get(0).getA6().toLowerCase().split("/");
                break;
            case 6 : textReturn = missionList.get(0).getA7().toLowerCase().split("/");
                break;
            case 7 : textReturn = missionList.get(0).getA8().toLowerCase().split("/");
                break;
            case 8 : textReturn = missionList.get(0).getA9().toLowerCase().split("/");
                break;
            case 9 : textReturn = missionList.get(0).getA10().toLowerCase().split("/");
                break;
            default: Toast.makeText(getApplicationContext(), "not answer", Toast.LENGTH_SHORT).show();
        }

         stringList = Arrays.asList(textReturn);
    }
}
