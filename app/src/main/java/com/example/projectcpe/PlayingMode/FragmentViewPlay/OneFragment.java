package com.example.projectcpe.PlayingMode.FragmentViewPlay;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.PlayingMode.PlayPage;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    public List<Mission> missionData;
    private String compareAnswer, textReturn;
    private TextToSpeech mTTs;



    public OneFragment() {
        // Required empty public constructor
    }

   protected Bitmap  bmp ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.one_frag, container, false);
        TextView textView = view.findViewById(R.id.text);
        TextView Question = view.findViewById(R.id.Question);
        ImageView imQuestion = view.findViewById(R.id.imQuestion);
        ImageView speaker = view.findViewById(R.id.speakerQuestion);

        mTTs = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(new Locale("en-US"));

                    mTTs.setSpeechRate(0.6f);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                    } else {
                        //                        speakbt.setEnabled(true);

                    }
                } else {
                }
            }
        });




        String step = getArguments().getString("step");
        int position = getArguments().getInt("position");
        String question = getArguments().getString("question");
        int id = getArguments().getInt("message");
        String numberPicture = getArguments().getString("numberpicture");
         String pic = getArguments().getString("code");

        missionData = getData(id);
//Toast.makeText(getActivity(), missionData.get(0).getMissionName(), Toast.LENGTH_LONG).show();


        try {
            File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/"+missionData.get(0).getMissionName(),"picture"+numberPicture+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(directory));
            imQuestion.setImageBitmap(b);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        textView.setText(String.valueOf(step));
        Question.setText(question);


        speaker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                speak(question);
            }
        });



        return view;
    }




    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(getActivity()).missionDAO().getAllinfoOfMission(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speak(String text) {


        mTTs.speak(text.replace("/","      "), TextToSpeech.QUEUE_FLUSH, null, null);


    }
}
