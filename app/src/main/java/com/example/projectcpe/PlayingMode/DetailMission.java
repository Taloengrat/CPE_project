package com.example.projectcpe.PlayingMode;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class DetailMission extends AppCompatActivity {

    ImageView btPlay;
    TextView txName, txNumstep, txAge, DetailMission, txDetail;
    public int id;
    public List<Mission> missionData;
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


        Bundle bundle = getIntent().getExtras();
       id = bundle.getInt("MissionId");

        Toast.makeText(getApplicationContext(), String.valueOf(id),Toast.LENGTH_SHORT).show();

        this.missionData = getData(id);




//                Toast.makeText(getApplicationContext(),missionData.get(0).getMissionName(),Toast.LENGTH_SHORT).show();

        txName.setText(missionData.get(0).getMissionName());
        txAge.setText(String.valueOf(missionData.get(0).getAge()));
        txNumstep.setText(String.valueOf(missionData.get(0).getNumberofMission())+" step" );
        txDetail.setText(missionData.get(0).getDetailMission()+" step");




    }

//    private void bindData() {
//
//    }

    private void setDetailMission(){


    }

    public void setBtPlayClick(View view){
        startActivity(new Intent(DetailMission.this, PlayPage.class));
        finish();
    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMission(id);
    }
}
