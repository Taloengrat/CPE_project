package com.example.projectcpe.PlayingMode;

import android.app.Dialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.Adapter.SlidePageAdapter;
import com.example.projectcpe.Adapter.StatiticAdapter;
import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.HomePage;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Static;

import java.util.List;

public class DetailMission extends AppCompatActivity implements View.OnClickListener {

    ImageView btPlay, expand;
    TextView txName, txNumstep, txAge, DetailMission, txDetail;
    public int MissionId, MemberId;
    public List<Mission> missionData;
    ImageView btShowDetailQuestion;
    RecyclerView recyclerView;
    ConstraintLayout constraintLayout;
    ViewPager viewPager;
    private SlidePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mission);

//        bindData();
        btPlay = findViewById(R.id.btPlay);
        txName = findViewById(R.id.Name);
        txAge = findViewById(R.id.Age);
        txNumstep = findViewById(R.id.numstep);
        DetailMission = findViewById(R.id.DetailMission);
        txDetail = findViewById(R.id.txDetail);
        recyclerView = findViewById(R.id.recyclerView);
        expand = findViewById(R.id.expand);
        constraintLayout = findViewById(R.id.constraintLayout3);
        btShowDetailQuestion = findViewById(R.id.btShowDetailQuestion);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle bundle = getIntent().getExtras();
        MissionId = bundle.getInt("MissionId");
        MemberId = bundle.getInt("MemberId");

        this.missionData = getData(MissionId);

        loadDataStatistic();

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (constraintLayout.getVisibility() == View.GONE) {
                    constraintLayout.setVisibility(View.VISIBLE);
                } else {
                    constraintLayout.setVisibility(View.GONE);
                }

            }
        });


//                Toast.makeText(getApplicationContext(), missionData.get(0).getQ1()
//                        +"\n"+missionData.get(0).getA1()
//                        +"\n"+missionData.get(0).getQ2()
//                        +"\n"+missionData.get(0).getA2(),Toast.LENGTH_SHORT).show();

//        Bitmap bmp = BitmapFactory.decodeByteArray(missionData.get(0).getP1(), 0, missionData.get(0).getP1().length);
//
//        btPlay.setImageBitmap(bmp);


        txName.setText(missionData.get(0).getMissionName());
        txAge.setText("age : " + String.valueOf(missionData.get(0).getAge()));
        txNumstep.setText("step : " + String.valueOf(missionData.get(0).getNumberofMission()));
        txDetail.setText("This Mission is about " + missionData.get(0).getDetailMission());


    }

//    private void bindData() {
//
//    }

    private void loadDataStatistic() {
        if (getStatistic(MissionId).isEmpty()) {
            findViewById(R.id.txNoStatic).setVisibility(View.VISIBLE);
            findViewById(R.id.imNoStatic).setVisibility(View.VISIBLE);
        } else {
            RecyclerView.Adapter adapter = new StatiticAdapter(getStatistic(MissionId), this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    public void setBtPlayClick(View view) {

        new ButtonServiceEffect(DetailMission.this).startEffect(); // Sound button effect

        Intent i = new Intent(DetailMission.this, PlayPage.class);
        i.putExtra("missionId", MissionId);
        i.putExtra("memberId", MemberId);
        i.putExtra("step", missionData.get(0).getNumberofMission());
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMission(id);
    }

    private List<Static> getStatistic(int subid) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllStatic(subid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDataStatistic();
    }

    @Override
    public boolean onSupportNavigateUp() {

        new ButtonServiceEffect(DetailMission.this).startEffect(); // Sound button effect
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(DetailMission.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(DetailMission.this, MusicService.class));
        startService(new Intent(DetailMission.this, MusicService.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btShowDetailQuestion:

                Intent i = new Intent(DetailMission.this, ShowDisplayDetail.class);
                i.putExtra("missionId", MissionId);
                i.putExtra("memberId", MemberId);
                i.putExtra("step", missionData.get(0).getNumberofMission());
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);







                break;
        }
    }




}
