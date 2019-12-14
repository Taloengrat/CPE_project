package com.example.projectcpe.TestSystem;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.R;

import java.util.ArrayList;
import java.util.Locale;


public class TestSystem extends AppCompatActivity {

    ImageView recognizi;
    SpeechRecognizer speechRecognizer;
    Intent speechRecognizerIntent;
    TextView answer1, answer2, answer3, answer4;
    String Keeper;
    public TextView KeeperAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_system);

        bindingData(); // bindding method
        
        SettingRecognizi(); // settingRecognizi




        answer1.setOnClickListener(HilightAnswer);
        answer2.setOnClickListener(HilightAnswer);
        answer3.setOnClickListener(HilightAnswer);
        answer4.setOnClickListener(HilightAnswer);

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
                    KeeperAnswer.setText(Keeper);

                    Toast.makeText(getApplicationContext(), "Resault = "+ Keeper, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        recognizi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN :

                        Log.d("motionEvent","Action was DOWN");
                        recognizi.setBackgroundResource(R.drawable.fram_hilight);
                        speechRecognizer.startListening(speechRecognizerIntent);
                        Keeper = "";
                        return true;

                    case MotionEvent.ACTION_UP :
                        Log.d("motionEvent","Action was UP");
                        recognizi.setBackgroundResource(R.drawable.fram_unhilight);
                        speechRecognizer.stopListening();
                        return true;
                    default :
                        return false;
                }
            }
        });

    }

    private void SettingRecognizi() {

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(TestSystem.this);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }


    View.OnClickListener HilightAnswer = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            if (KeeperAnswer == null) {

                v.setBackgroundResource(R.drawable.fram_hilight);
                KeeperAnswer = (TextView) v;
            }
            if (KeeperAnswer.getId() != v.getId()){

                TextView lastAnswer = (TextView) KeeperAnswer;
                lastAnswer.setBackgroundResource(R.drawable.fram_unhilight);
                v.setBackgroundResource(R.drawable.fram_hilight);
                KeeperAnswer = (TextView) v;

            }
        }
    };



    private void bindingData() {
        recognizi = findViewById(R.id.recognizi);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
    }
}
