package com.example.projectcpe.PlayingMode;

import com.example.projectcpe.Adapter.SlidePageAdapter;
import com.example.projectcpe.CSV.Utility.PermissionUtility;
import com.example.projectcpe.MainActivity;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    String Hint;
    int numHint;


    LinearLayout frameHint;

    private SlidePageAdapter adapter;
    private TextView textclock, timecount, Answer;
    ImageView recogni, back, next, voice, help, start;
    private Timer timer;
    private CountDownTimer countDownTimer;
    ViewPager pager;
    ImageView check, keyboard, confirm;

    boolean threadreset;
    EditText etKeyboard;
    protected int id, stepnum;
    protected boolean running = false;
    TextView hint1, hint2, hint3, hint4;

    boolean h1 = false, h2 = false, h3 = false, h4 = false;

    SpeechRecognizer speechRecognizer;
    Intent speechRecognizerIntent;
    String Keeper;
    private String[] textReturn, ScoreString, HintString;
    int memberId;
    List<Mission> missionList;
    List<String> stringList, scoreList, hintList;
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;

    @Override
    protected void onStart() {
        super.onStart();

        stopService(new Intent(PlayPage.this, MusicService.class));
        if (PermissionUtility.askPermissionForActivity(PlayPage.this, Manifest.permission.RECORD_AUDIO, WRITE_PERMISSON_REQUEST_CODE)) {


        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);

        Initial();

        getHint(pager.getCurrentItem());


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

                if (matchesFound != null) {
                    Keeper = matchesFound.get(0);
                    Answer.setVisibility(View.VISIBLE);
                    Answer.setText(Keeper.replace(".", ""));

                    getAnswerFun(pager.getCurrentItem());
                    VerifyAnswer(stringList, Keeper.replace(".", "").toLowerCase());

//                    Bundle bundleAnswer = new Bundle();
//                    bundleAnswer.putString("answerATV", Answer.getText().t
//                    oString().trim());
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

    View.OnClickListener HintStep = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (frameHint.getVisibility() == View.GONE) {
                frameHint.setVisibility(View.VISIBLE);
                switch (numHint) {
                    case 1:
                        hint2.setVisibility(View.GONE);
                        hint3.setVisibility(View.GONE);
                        hint4.setVisibility(View.GONE);
                        break;
                    case 2:
                        hint3.setVisibility(View.GONE);
                        hint4.setVisibility(View.GONE);
                        break;
                    case 3:
                        hint1.setVisibility(View.VISIBLE);
                        hint2.setVisibility(View.VISIBLE);
                        hint3.setVisibility(View.VISIBLE);
                        hint4.setVisibility(View.GONE);
                        break;
                    case 4:
                        hint1.setVisibility(View.VISIBLE);
                        hint2.setVisibility(View.VISIBLE);
                        hint3.setVisibility(View.VISIBLE);
                        hint4.setVisibility(View.VISIBLE);

                }

            } else {
                frameHint.setVisibility(View.GONE);
            }
        }
    };

    View.OnClickListener hintOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Dialog dialog = new Dialog(PlayPage.this);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_hint);
            dialog.setCancelable(true);

            TextView data = dialog.findViewById(R.id.dataHint);
            TextView head = dialog.findViewById(R.id.txHeadHint);

            head.setText("Hint number : 1");

            h1 = true;

            hint1.setBackgroundResource(R.drawable.elevetorcircle_used);
            data.setText(HintString[0]);


            dialog.show();
        }
    };

    View.OnClickListener hintTwo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Dialog dialog = new Dialog(PlayPage.this);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_hint);
            dialog.setCancelable(true);

            final TextView data = dialog.findViewById(R.id.dataHint);
            TextView head = dialog.findViewById(R.id.txHeadHint);

            head.setText("Hint number : 2");


            if (h1) {
                hint2.setBackgroundResource(R.drawable.elevetorcircle_used);

                h2 = true;

                data.setText(HintString[1]);

                hint1.setOnClickListener(hintTwo);

                dialog.show();
            } else {
                AlertDialog.Builder dialogAsk = new AlertDialog.Builder(PlayPage.this);
                dialogAsk.setTitle("Show Hint");
                dialogAsk.setCancelable(true);
                dialogAsk.setMessage("Do you want to croos hint 1 ?");
                dialogAsk.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        hint1.setBackgroundResource(R.drawable.elevetorcircle_used);
                        hint2.setBackgroundResource(R.drawable.elevetorcircle_used);
                        h1 = true;
                        h2 = true;


                        data.setText(HintString[1]);

                        dialog.show();

                        hint1.setOnClickListener(hintTwo);
                    }
                });

                dialogAsk.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogAsk.show();
            }


        }
    };

    View.OnClickListener hintThree = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Dialog dialog = new Dialog(PlayPage.this);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_hint);
            dialog.setCancelable(true);


            final TextView data = dialog.findViewById(R.id.dataHint);
            TextView head = dialog.findViewById(R.id.txHeadHint);

            head.setText("Hint number : 3");

            if (h2) {
                hint3.setBackgroundResource(R.drawable.elevetorcircle_used);

                h3 = true;

                data.setText(HintString[2]);

                hint1.setOnClickListener(hintThree);
                hint2.setOnClickListener(hintThree);

                dialog.show();
            } else {
                AlertDialog.Builder dialogAsk = new AlertDialog.Builder(PlayPage.this);
                dialogAsk.setTitle("Show Hint");
                dialogAsk.setCancelable(true);
                dialogAsk.setMessage("Do you want to croos hint 2 ?");
                dialogAsk.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        hint1.setBackgroundResource(R.drawable.elevetorcircle_used);
                        hint2.setBackgroundResource(R.drawable.elevetorcircle_used);
                        hint3.setBackgroundResource(R.drawable.elevetorcircle_used);
                        h1 = true;
                        h2 = true;
                        h3 = true;


                        data.setText(HintString[2]);

                        dialog.show();

                        hint1.setOnClickListener(hintThree);
                        hint2.setOnClickListener(hintThree);

                    }
                });

                dialogAsk.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogAsk.show();
            }


        }
    };

    View.OnClickListener hintFour = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Dialog dialog = new Dialog(PlayPage.this);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
            dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
            dialog.setContentView(R.layout.dialog_hint);
            dialog.setCancelable(true);

            final TextView data = dialog.findViewById(R.id.dataHint);
            TextView head = dialog.findViewById(R.id.txHeadHint);

            head.setText("Hint number : 4");

            if (h3) {
                hint4.setBackgroundResource(R.drawable.elevetorcircle_used);

                h4 = true;

                data.setText(HintString[3]);

                hint1.setOnClickListener(hintFour);
                hint2.setOnClickListener(hintFour);
                hint3.setOnClickListener(hintFour);

                dialog.show();
            } else {
                AlertDialog.Builder dialogAsk = new AlertDialog.Builder(PlayPage.this);
                dialogAsk.setTitle("Show Hint");
                dialogAsk.setCancelable(true);
                dialogAsk.setMessage("Do you want to croos hint 3 ?");
                dialogAsk.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        hint1.setBackgroundResource(R.drawable.elevetorcircle_used);
                        hint2.setBackgroundResource(R.drawable.elevetorcircle_used);
                        hint3.setBackgroundResource(R.drawable.elevetorcircle_used);
                        hint4.setBackgroundResource(R.drawable.elevetorcircle_used);
                        h1 = true;
                        h2 = true;
                        h3 = true;
                        h4 = true;


                        data.setText(HintString[3]);

                        dialog.show();

                        hint1.setOnClickListener(hintFour);
                        hint2.setOnClickListener(hintFour);
                        hint3.setOnClickListener(hintFour);

                    }
                });

                dialogAsk.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialogAsk.show();
            }


        }
    };

    View.OnTouchListener Hilight = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {


            if (PermissionUtility.askPermissionForActivity(PlayPage.this, Manifest.permission.RECORD_AUDIO, WRITE_PERMISSON_REQUEST_CODE)) {


            }

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if (frameHint.getVisibility() == View.VISIBLE) {
                        frameHint.setVisibility(View.GONE);
                    }


                    Log.d("motionEvent", "Action was DOWN");
                    view.setBackgroundResource(R.drawable.fram_hilight);

                    speechRecognizer.startListening(speechRecognizerIntent);
                    Keeper = "";
                    return true;

                case MotionEvent.ACTION_UP:
                    Log.d("motionEvent", "Action was UP");
                    view.setBackgroundResource(R.drawable.backgroundmic);
                    speechRecognizer.stopListening();
                    return true;
                default:
                    return false;
            }
        }
    };

    private void getHint(int position) {

        switch (position) {
            case 0:
                HintString = missionList.get(0).getH1().split("/");
                break;
            case 1:
                HintString = missionList.get(0).getH2().split("/");
                break;
            case 2:
                HintString = missionList.get(0).getH3().split("/");
                break;
            case 3:
                HintString = missionList.get(0).getH4().split("/");
                break;
            case 4:
                HintString = missionList.get(0).getH5().split("/");
                break;
            case 5:
                HintString = missionList.get(0).getH6().split("/");
                break;
            case 6:
                HintString = missionList.get(0).getH7().split("/");
                break;
            case 7:
                HintString = missionList.get(0).getH8().split("/");
                break;
            case 8:
                HintString = missionList.get(0).getH9().split("/");
                break;
            case 9:
                HintString = missionList.get(0).getH10().split("/");
                break;
        }
        hintList = Arrays.asList(HintString);
        numHint = HintString.length;

        Log.v("currentHint", String.valueOf(numHint));
    }

    private void VerifyAnswer(List<String> missionAnswer, String userAnswer) {
        if (missionAnswer.contains(userAnswer)) {
            CorrectStep();
            getScore(pager.getCurrentItem());

            switch (Integer.valueOf(missionAnswer.indexOf(userAnswer))) {
                case 0:
                    Score = Float.valueOf(ScoreString[0]);

                    break;
                case 1:
                    Score = Float.valueOf(ScoreString[1]);

                    break;
                case 2:
                    Score = Float.valueOf(ScoreString[2]);

                    break;
                case 3:
                    Score = Float.valueOf(ScoreString[3]);

                    break;
                case 4:
                    Score = Float.valueOf(ScoreString[4]);

                    break;
                case 5:
                    Score = Float.valueOf(ScoreString[5]);

                    break;
                case 6:
                    Score = Float.valueOf(ScoreString[6]);
                    break;

            }

            SumScore += Score;
            Toast.makeText(getApplicationContext(), "point this step : " + String.valueOf(Score)
                    + "\n All Score : " + SumScore, Toast.LENGTH_SHORT).show();


        } else {
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


                if (pager.getCurrentItem() + 1 == missionList.get(0).getNumberofMission()) {
                    Toast.makeText(PlayPage.this, "end of step", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PlayPage.this, SumaryPage.class);
                    i.putExtra("IDmission", id);
                    i.putExtra("score", SumScore);
                    i.putExtra("memberId", memberId);
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    check.setVisibility(View.GONE);
                    pager.setCurrentItem(pager.getCurrentItem() + 1);

                    getHint(pager.getCurrentItem()); //เปลี่ยนค่าของ hint
                    frameHint.setVisibility(View.GONE); // ซ่อนกรอบของ Hint ก่อนที่จะเปลี่ยนเพจ

                    Answer.setText("");
                    Answer.setVisibility(View.INVISIBLE);

//                    changeHint(numHint); //เปลี่ยนจำนวนของ Hint


                    recogni.setOnTouchListener(Hilight);
                    textclock.setTextColor(getResources().getColor(R.color.black));

                    stopTimer();
                    ms = 0;
                    minutes = 0;
                    seconds = 0;

                    if (etKeyboard.getVisibility() == View.VISIBLE) {
                        etKeyboard.setText(null);
                        etKeyboard.setVisibility(View.GONE);
                        keyboard.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                    }

                    hint1.setOnClickListener(hintOne);
                    hint2.setOnClickListener(hintTwo);
                    hint3.setOnClickListener(hintThree);
                    hint4.setOnClickListener(hintFour);

                    h1 = false;
                    h2 = false;
                    h3 = false;
                    h4 = false;

                    hint1.setBackgroundResource(R.drawable.elevetorcircle);
                    hint2.setBackgroundResource(R.drawable.elevetorcircle);
                    hint3.setBackgroundResource(R.drawable.elevetorcircle);
                    hint4.setBackgroundResource(R.drawable.elevetorcircle);

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
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

    public class LanguageDetailsChecker extends BroadcastReceiver {
        private List<String> supportedLanguages;

        private String languagePreference;

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle results = getResultExtras(true);
            if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
                languagePreference =
                        results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
            }
            if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
                supportedLanguages =
                        results.getStringArrayList(
                                RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
            }
        }
    }

    private void ShowViewPage() {
        adapter = new SlidePageAdapter(getSupportFragmentManager(), id, stepnum, getData(id));
        pager.setAdapter(adapter);
    }

    public void Initial() {

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("missionId");
        stepnum = bundle.getInt("step");
        memberId = bundle.getInt("memberId");


        frameHint = findViewById(R.id.frameHint);
        hint1 = findViewById(R.id.hint1);
        hint2 = findViewById(R.id.hint2);
        hint3 = findViewById(R.id.hint3);
        hint4 = findViewById(R.id.hint4);
        keyboard = findViewById(R.id.keyboard);
        etKeyboard = findViewById(R.id.etKeyboard);
        confirm = findViewById(R.id.confirm);

        check = findViewById(R.id.check);
        recogni = findViewById(R.id.mic);
//        back = findViewById(R.id.back);
//        next = findViewById(R.id.next);
        help = findViewById(R.id.hint);
        voice = findViewById(R.id.speak);
        start = findViewById(R.id.startClick);
        timecount = findViewById(R.id.counttime);
        pager = findViewById(R.id.ViewPage);
        Answer = findViewById(R.id.answer);
        textclock = findViewById(R.id.textClock);

        missionList = getData(id);


        SettingRecognizi();


//        back.setOnTouchListener(Hilight);
//        next.setOnTouchListener(Hilight);


        hint1.setOnClickListener(hintOne);
        hint2.setOnClickListener(hintTwo);
        hint3.setOnClickListener(hintThree);
        hint4.setOnClickListener(hintFour);

        keyboard.setOnClickListener(keyboardClick);
        confirm.setOnClickListener(confirmClick);

        start.setOnClickListener(startTimeBegin);


    }

    private void getScore(int position) {

        switch (position) {
            case 0:
                ScoreString = missionList.get(0).getS1().split("/");
                break;
            case 1:
                ScoreString = missionList.get(0).getS2().split("/");
                break;
            case 2:
                ScoreString = missionList.get(0).getS3().split("/");
                break;
            case 3:
                ScoreString = missionList.get(0).getS4().split("/");
                break;
            case 4:
                ScoreString = missionList.get(0).getS5().split("/");
                break;
            case 5:
                ScoreString = missionList.get(0).getS6().split("/");
                break;
            case 6:
                ScoreString = missionList.get(0).getS7().split("/");
                break;
            case 7:
                ScoreString = missionList.get(0).getS8().split("/");
                break;
            case 8:
                ScoreString = missionList.get(0).getS9().split("/");
                break;
            case 9:
                ScoreString = missionList.get(0).getS10().split("/");
                break;
        }
        scoreList = Arrays.asList(ScoreString);
    }

    private void stopTimer() {
        running = false;
//        btnStart.setText("Start");
        timer.cancel();
    }

    private void countDownTimer() {
        countDownTimer = new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                MediaPlayer.create(PlayPage.this, R.raw.starsound).start();
                timecount.setText(String.valueOf(millisUntilFinished / 1000));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                timecount.setText("Start !!!");

                MediaPlayer.create(PlayPage.this, R.raw.start).start();
                Runnable Delay = new Runnable() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void run() {

                        timecount.setVisibility(View.GONE);
                        startTimer();
                        ShowViewPage();

                        voice.setOnTouchListener(Hilight);// ใหปุ่ม speech อ่านออกเสียง เริ่มทำงานหลังจาก นับถอยหลังเสร็จ
                        help.setOnClickListener(HintStep);// ใหปุ่ม speech hint เริ่มทำงานหลังจาก นับถอยหลังเสร็จ
                        recogni.setOnTouchListener(Hilight);// ใหปุ่ม speech recog เริ่มทำงานหลังจาก นับถอยหลังเสร็จ

                    }
                };

                Handler pd = new Handler();
                pd.postDelayed(Delay, 2000);


            }

        }.start();
    }

    private void startTimer() {

        timer = new Timer();

        running = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runTimer();
            }
        }, 0, 100);


    }

    private void runTimer() {
        this.runOnUiThread(timerTick);
    }

    private void updateMs() {
        ms++;
        if (ms == 10) {
            updateSeconds();
        }
    }

    private void updateSeconds() {
        ms = 0;
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
    }


    private void updateTimerText() {


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

                keyboard.setVisibility(View.VISIBLE);

            } else {
                updateTimerText();
                updateMs();
            }
        }
    };


    View.OnClickListener startTimeBegin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            view.setVisibility(View.GONE);
            timecount.setVisibility(View.VISIBLE);
            countDownTimer();

        }
    };

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(PlayPage.this).missionDAO().getAllinfoOfMission(id);
    }

    private void getAnswerFun(int position) {
        Toast.makeText(getApplicationContext(), String.valueOf(pager.getCurrentItem()), Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                textReturn = missionList.get(0).getA1().toLowerCase().split("/");
                break;
            case 1:
                textReturn = missionList.get(0).getA2().toLowerCase().split("/");
                break;
            case 2:
                textReturn = missionList.get(0).getA3().toLowerCase().split("/");
                break;
            case 3:
                textReturn = missionList.get(0).getA4().toLowerCase().split("/");
                break;
            case 4:
                textReturn = missionList.get(0).getA5().toLowerCase().split("/");
                break;
            case 5:
                textReturn = missionList.get(0).getA6().toLowerCase().split("/");
                break;
            case 6:
                textReturn = missionList.get(0).getA7().toLowerCase().split("/");
                break;
            case 7:
                textReturn = missionList.get(0).getA8().toLowerCase().split("/");
                break;
            case 8:
                textReturn = missionList.get(0).getA9().toLowerCase().split("/");
                break;
            case 9:
                textReturn = missionList.get(0).getA10().toLowerCase().split("/");
                break;
            default:
                Toast.makeText(getApplicationContext(), "not answer", Toast.LENGTH_SHORT).show();
        }

        stringList = Arrays.asList(textReturn);
    }


    View.OnClickListener keyboardClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            etKeyboard.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);

            if (Answer.getVisibility() == View.VISIBLE) {
                Answer.setVisibility(View.INVISIBLE);
            } else {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etKeyboard, InputMethodManager.SHOW_IMPLICIT);
            }

        }
    };

    View.OnClickListener confirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            getAnswerFun(pager.getCurrentItem());
            VerifyAnswer(stringList, etKeyboard.getText().toString().toLowerCase());

            Log.v("Para1", Arrays.toString(new List[]{stringList}));
            Log.v("Para2", etKeyboard.getText().toString().toLowerCase());


        }
    };
}
