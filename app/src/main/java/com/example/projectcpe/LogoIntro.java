package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcpe.Adapter.MemberAdapterBegin;
import com.example.projectcpe.CreateMission.MissionCreate;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

public class    LogoIntro extends AppCompatActivity {

    ImageView imLogo;
    TextView txLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_intro);

        imLogo = findViewById(R.id.logoApp);
        txLogo = findViewById(R.id.textView);



        AnimationFade();


    }

    private void AnimationFade() {

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(imLogo, View.ALPHA, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(txLogo, View.ALPHA, 0f);

        anim1.setDuration(3400);
        anim1.start();

        anim2.setDuration(3400);
        anim2.start();


        //Delay
        Runnable Delay = new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(LogoIntro.this, BeginMember.class));
                finish();

            }
        };

        Handler pd = new Handler();
        pd.postDelayed(Delay, 3000);
    }


}
