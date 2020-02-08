package com.example.projectcpe.Adapter;

import android.app.Activity;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.PlayingMode.SumaryPage;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Sumary;

import java.util.List;
import java.util.Locale;

public class SumaryAdapter extends RecyclerView.Adapter<SumaryAdapter.SumaryViewHolder>  {


    private TextToSpeech mTTs;


    Activity activity;
    public static List<Sumary> sumaryList;

    public SumaryAdapter(List<Sumary> sumaryList, Activity activity){
        this.activity = activity;
        this.sumaryList = sumaryList;
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

        holder.word.setText(sumary.getWord());

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

                new ButtonServiceEffect(activity).startEffect(); // Sound button effect
                speak(holder.word.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return sumaryList.size();
    }

    public class SumaryViewHolder extends RecyclerView.ViewHolder {

        TextView word;
        ImageView speker;

        public SumaryViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.word);
            speker = itemView.findViewById(R.id.speak);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speak(String text) {


        mTTs.speak(text.replace("/","  or  "), TextToSpeech.QUEUE_FLUSH, null, null);


    }



}
