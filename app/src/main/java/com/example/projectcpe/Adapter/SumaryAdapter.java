package com.example.projectcpe.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.PlayingMode.SumaryPage;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Sumary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;

public class SumaryAdapter extends RecyclerView.Adapter<SumaryAdapter.SumaryViewHolder>  {


    private TextToSpeech mTTs;
    public List<Mission> missionData;
    Bitmap b;



    Activity activity;
    int id;
    public static List<Sumary> sumaryList;

    public SumaryAdapter(List<Sumary> sumaryList, Activity activity, List<Mission> missionData){
        this.activity = activity;
        this.sumaryList = sumaryList;
       this.missionData = missionData;
    }

    @NonNull
    @Override
    public SumaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent,false);
        return new SumaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SumaryViewHolder holder, int position) {
        final Sumary sumary = sumaryList.get(position);
        float totalScore = 100.0f / sumaryList.size();

        Log.e("totalScore", String.valueOf(totalScore));

        holder.word.setText(sumary.getWord());
        holder.scoreStep.setText("Your Score is " + sumary.getScoreStep() + " of " + String.format("%.1f",totalScore) );
        holder.scoreWrongStep.setText("Wrong speech " + sumary.getScoreWrongStep() + " time.");

        try {
            File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/"+missionData.get(0).getMissionName(),"picture"+(position+1)+".jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(directory));
            holder.picture.setImageBitmap(b);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_custom);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.showdetail_picture);
                dialog.setCancelable(true);

                ImageView imageDetail = dialog.findViewById(R.id.imDetail);

                try {
                    File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/"+missionData.get(0).getMissionName(),"picture"+(position+1)+".jpg");
                    Bitmap b2 = BitmapFactory.decodeStream(new FileInputStream(directory));
                    imageDetail.setImageBitmap(b2);

                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }


                imageDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

//        missionData = getData(id);

//        try {
//            File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/"+missionData.get(0).getMissionName(),"picture"+ position+1 +".jpg");
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(directory));
//            holder.picture.setImageBitmap(b);
//
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }

        mTTs = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
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


        holder.speker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                speak(holder.word.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return sumaryList.size();
    }

    public class SumaryViewHolder extends RecyclerView.ViewHolder {

        TextView word,scoreStep,scoreWrongStep;
        ImageView speker,picture;
        int numPic;

        public SumaryViewHolder(@NonNull View itemView) {
            super(itemView);


            word = itemView.findViewById(R.id.word);
            speker = itemView.findViewById(R.id.speak);
            scoreStep = itemView.findViewById(R.id.scoreStep);
            scoreWrongStep = itemView.findViewById(R.id.scoreWrongStep);
            picture = itemView.findViewById(R.id.imUserSumary);




        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speak(String text) {


        mTTs.speak(text.replace("/","  or  "), TextToSpeech.QUEUE_FLUSH, null, null);


    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(activity).missionDAO().getAllinfoOfMission(id);
    }



}
