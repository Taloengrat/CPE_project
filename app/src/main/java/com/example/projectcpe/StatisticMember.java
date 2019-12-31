package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class StatisticMember extends AppCompatActivity {

List<Member> memberList;
Bitmap bitmap;
ImageView imProfile;
TextView txName,txAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_member);

        Initial();

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        memberList = getDataMember(id); //get Data Of Member
        bitmap = BitmapFactory.decodeByteArray(memberList.get(0).getProfile(), 0, memberList.get(0).getProfile().length);

        imProfile.setImageBitmap(bitmap);
        txName.setText("Name : " + memberList.get(0).getName());
        txAge.setText("Age : "+memberList.get(0).getAge());
    }

    private void Initial() {
        txAge = findViewById(R.id.txAge);
        txName = findViewById(R.id.txName);
        imProfile = findViewById(R.id.imProfile);
    }

    private List<Member> getDataMember(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMember(id);
    }
}
