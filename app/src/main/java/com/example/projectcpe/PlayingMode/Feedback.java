package com.example.projectcpe.PlayingMode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.BeginMember;
import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.LogoIntro;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MemberStatic;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Static;

import java.util.List;

public class Feedback extends AppCompatActivity {

    int id, memberId, numstar;
    List<Mission> missionList;
    List<Member> memberList;
    TextView MissionName, UserName,UserScore;
    ImageView  imUser, fullstar1, fullstar2, fullstar3, fullstar4, fullstar5, halfstar1, halfstar2, halfstar3, halfstar4, halfstar5, backMission;
    MediaPlayer mediaPlayer;
    float Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Initiali();


        missionList = getDataMission(id);
        memberList = getDataMember(memberId);

        Bitmap bitmap = BitmapFactory.decodeByteArray(memberList.get(0).getProfile(), 0, memberList.get(0).getProfile().length);

        MissionName.setText(missionList.get(0).getMissionName());
        UserName.setText(memberList.get(0).getName());
        imUser.setImageBitmap(bitmap);

        UserScore.setText("Your score is " + String.format("%.1f",Score) + " of 100");

        ShowStar(Score); //Show Star


        Static statistic = new Static(id, memberId, memberList.get(0).getProfile(), memberList.get(0).getName(), Integer.valueOf(memberList.get(0).getAge())
                , Score, numstar);

        Log.v("star", String.valueOf(numstar));


        if (CheckForUpdate(memberId, id) == null) {

            MissionDATABASE.getInstance(Feedback.this).missionDAO().createStatistic(statistic);

        } else {

            if (CheckForUpdate(memberId, id).getScore() < Score) {
                MissionDATABASE.getInstance(Feedback.this).missionDAO().updateStatistic2(Score, CheckForUpdate(memberId,id).getId(),numstar);
            } else {

            }
        }


        MemberStatic memberStatic = new MemberStatic(memberId, missionList.get(0).getMissionName(), String.valueOf(missionList.get(0).getAge()), numstar, Score);

        MissionDATABASE.getInstance(Feedback.this).missionDAO().createMemberStatic(memberStatic);




    }

    private void Initiali() {

        mediaPlayer = MediaPlayer.create(Feedback.this, R.raw.starsound);
        MissionName = findViewById(R.id.missionName);

        UserName = findViewById(R.id.userName);
        UserScore = findViewById(R.id.userScore);

        imUser = findViewById(R.id.imUser);
        fullstar1 = findViewById(R.id.fullstar1);
        fullstar2 = findViewById(R.id.fullstar2);
        fullstar3 = findViewById(R.id.fullstar3);
        fullstar4 = findViewById(R.id.fullstar4);
        fullstar5 = findViewById(R.id.fullstar5);
        halfstar1 = findViewById(R.id.halfstar1);
        halfstar2 = findViewById(R.id.halfstar2);
        halfstar3 = findViewById(R.id.halfstar3);
        halfstar4 = findViewById(R.id.halfstar4);
        halfstar5 = findViewById(R.id.halfstar5);
        backMission = findViewById(R.id.missionHome);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("IDmission");
        memberId = bundle.getInt("memberId");
        Score = bundle.getFloat("score");
    }

    private List<Mission> getDataMission(int id) {
        return MissionDATABASE.getInstance(Feedback.this).missionDAO().getAllinfoOfMission(id);
    }

    private Static CheckForUpdate(int id, int missionId) {
        return MissionDATABASE.getInstance(Feedback.this).missionDAO().CheckForUpdateStatistic(id, missionId);
    }

    private List<Member> getDataMember(int id) {
        return MissionDATABASE.getInstance(Feedback.this).missionDAO().getAllinfoOfMember(id);
    }



    private void ShowStar(float score) {
        Runnable star1 = new Runnable() {
            @Override
            public void run() {

                mediaPlayer.start();
                halfstar1.setVisibility(View.VISIBLE);
            }
        };

        Runnable star2 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);

            }
        };

        Runnable star3 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                halfstar1.setVisibility(View.VISIBLE);

            }
        };

        Runnable star4 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);

            }
        };

        Runnable star5 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);
                halfstar3.setVisibility(View.VISIBLE);

            }
        };

        Runnable star6 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);
                fullstar3.setVisibility(View.VISIBLE);
            }
        };

        Runnable star7 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);
                fullstar3.setVisibility(View.VISIBLE);
                halfstar4.setVisibility(View.VISIBLE);
            }
        };

        Runnable star8 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);
                fullstar3.setVisibility(View.VISIBLE);
                fullstar4.setVisibility(View.VISIBLE);
            }
        };

        Runnable star9 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);
                fullstar3.setVisibility(View.VISIBLE);
                fullstar4.setVisibility(View.VISIBLE);
                halfstar5.setVisibility(View.VISIBLE);
            }
        };

        Runnable star10 = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
                fullstar1.setVisibility(View.VISIBLE);
                fullstar2.setVisibility(View.VISIBLE);
                fullstar3.setVisibility(View.VISIBLE);
                fullstar4.setVisibility(View.VISIBLE);
                fullstar5.setVisibility(View.VISIBLE);
            }
        };

        Handler pd = new Handler();


        if (score < 10 && score > 0) {
            numstar = 1;
            pd.postDelayed(star1, 1000);
        } else if (score < 20 && score >= 10) {
            numstar = 2;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
        } else if (score < 30 && score >= 20) {
            numstar = 3;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
        } else if (score < 40 && score >= 30) {
            numstar = 4;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
        } else if (score <= 50 && score >= 40) {
            numstar = 5;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
            pd.postDelayed(star5, 3000);
        } else if (score <= 60 && score >= 50) {
            numstar = 6;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
            pd.postDelayed(star5, 3000);
            pd.postDelayed(star6, 3500);

        } else if (score <= 70 && score >= 60) {
            numstar = 7;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
            pd.postDelayed(star5, 3000);
            pd.postDelayed(star6, 3500);
            pd.postDelayed(star7, 4000);
        } else if (score <= 80 && score >= 70) {
            numstar = 8;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
            pd.postDelayed(star5, 3000);
            pd.postDelayed(star6, 3500);
            pd.postDelayed(star7, 4000);
            pd.postDelayed(star8, 4500);
        } else if (score <= 90 && score >= 80) {
            numstar = 9;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
            pd.postDelayed(star5, 3000);
            pd.postDelayed(star6, 3500);
            pd.postDelayed(star7, 4000);
            pd.postDelayed(star8, 4500);
            pd.postDelayed(star9, 5000);
        } else if (score <= 100 && score >= 90) {
            numstar = 10;
            pd.postDelayed(star1, 1000);
            pd.postDelayed(star2, 1500);
            pd.postDelayed(star3, 2000);
            pd.postDelayed(star4, 2500);
            pd.postDelayed(star5, 3000);
            pd.postDelayed(star6, 3500);
            pd.postDelayed(star7, 4000);
            pd.postDelayed(star8, 4500);
            pd.postDelayed(star9, 5000);
            pd.postDelayed(star10, 5500);
        } else {
            numstar = 0;
        }
    }


    public void Onclickbacktohome(View view) {

        startService(new Intent(Feedback.this, MusicService.class));
        new ButtonServiceEffect(Feedback.this).startEffect(); // Sound button effect
        Intent i = new Intent(Feedback.this, DetailMission.class);
        i.putExtra("MissionId", id);
        i.putExtra("MemberId", memberId);
        startActivity(i);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(Feedback.this, MusicService.class));
    }

    @Override
    public void onBackPressed() {



    }

}
