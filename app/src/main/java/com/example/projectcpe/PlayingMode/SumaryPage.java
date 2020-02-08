package com.example.projectcpe.PlayingMode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import com.example.projectcpe.Adapter.MemberAdapterBegin;
import com.example.projectcpe.Adapter.SumaryAdapter;
import com.example.projectcpe.AdminPage;
import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Sumary;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SumaryPage extends AppCompatActivity {

    Button btNext;
    private int id, memberId;
    private float Score;

    List<Mission> missionList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumary_page);



        recyclerView = findViewById(R.id.recyclerViewSumary);

        btNext = findViewById(R.id.btNext);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("IDmission");
        memberId = bundle.getInt("memberId");
        Score = bundle.getFloat("score");

        missionList = getData(id);

        SumaryAdapter adapter = new SumaryAdapter(getSumary(missionList.get(0).getNumberofMission()), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ButtonServiceEffect(SumaryPage.this).startEffect(); // Sound button effect

                Intent i = new Intent(new Intent(SumaryPage.this, Feedback.class));
                i.putExtra("IDmission", id);
                i.putExtra("memberId", memberId);
                i.putExtra("score", Score);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        });
    }
    private List<Sumary> getSumary(int num){

        List<Sumary> sumaryList = new ArrayList<>();



        for (int i =0 ; i<num; i++) {
            Sumary sumary = new Sumary();
            switch (i){
                case 0 : sumary.setWord(missionList.get(0).getA1());
                    break;
                case 1 : sumary.setWord(missionList.get(0).getA2());
                    break;
                case 2 : sumary.setWord(missionList.get(0).getA3());
                    break;
                case 3 : sumary.setWord(missionList.get(0).getA4());
                    break;
                case 4 : sumary.setWord(missionList.get(0).getA5());
                    break;
                case 5 : sumary.setWord(missionList.get(0).getA6());
                    break;
                case 6 : sumary.setWord(missionList.get(0).getA7());
                    break;
                case 7 : sumary.setWord(missionList.get(0).getA8());
                    break;
                case 8 : sumary.setWord(missionList.get(0).getA9());
                    break;
                case 9 : sumary.setWord(missionList.get(0).getA10());
                    break;
                    default:
            }

            sumaryList.add(sumary);
        }
        return  sumaryList;
    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(SumaryPage.this).missionDAO().getAllinfoOfMission(id);
    }


}
