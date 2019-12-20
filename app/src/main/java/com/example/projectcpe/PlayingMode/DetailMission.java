package com.example.projectcpe.PlayingMode;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        Toast.makeText(getApplicationContext(), String.valueOf(id),Toast.LENGTH_LONG).show();

        this.missionData = getData(id);




                Toast.makeText(getApplicationContext(), missionData.get(0).getQ1()
                        +"\n"+missionData.get(0).getA1()
                        +"\n"+missionData.get(0).getQ2()
                        +"\n"+missionData.get(0).getA2(),Toast.LENGTH_SHORT).show();

//        Bitmap bmp = BitmapFactory.decodeByteArray(missionData.get(0).getP1(), 0, missionData.get(0).getP1().length);
//
//        btPlay.setImageBitmap(bmp);



        txName.setText(missionData.get(0).getMissionName());
        txAge.setText("age : " +String.valueOf(missionData.get(0).getAge()));
        txNumstep.setText("step : " + String.valueOf(missionData.get(0).getNumberofMission()));
        txDetail.setText(missionData.get(0).getDetailMission());




    }

//    private void bindData() {
//
//    }

    private void setDetailMission(){


    }

    public void setBtPlayClick(View view){

        Intent i = new Intent(DetailMission.this, PlayPage.class);
        i.putExtra("getId", id);
        i.putExtra("step", missionData.get(0).getNumberofMission());
        startActivity(i);
        finish();
    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMission(id);
    }
}
