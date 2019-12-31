package com.example.projectcpe.PlayingMode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectcpe.R;

public class SumaryPage extends AppCompatActivity {

Button btNext;
private int id, memberId;
private float Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumary_page);

        btNext = findViewById(R.id.btNext);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("IDmission");
        memberId = bundle.getInt("memberId");
        Score = bundle.getFloat("score");


        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
