package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcpe.Adapter.MemberStatisticAdapter;
import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MemberStatic;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class StatisticMember extends AppCompatActivity {

    List<Member> memberList;
    Bitmap bitmap;
    ImageView imProfile;
    TextView txName, txAge;
    RecyclerView recyclerView;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_member);

        Initial();

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        memberList = getDataMember(id); //get Data Of Member
        bitmap = BitmapFactory.decodeByteArray(memberList.get(0).getProfile(), 0, memberList.get(0).getProfile().length);

        imProfile.setImageBitmap(bitmap);
        txName.setText("Name : " + memberList.get(0).getName());
        txAge.setText("Age : " + memberList.get(0).getAge());

        loadData();
    }

    private void Initial() {
        txAge = findViewById(R.id.txAge);
        txName = findViewById(R.id.txName);
        imProfile = findViewById(R.id.imProfile);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private List<Member> getDataMember(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMember(id);
    }

    private List<MemberStatic> getDataMission(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllMemberStatic(id);
    }

    public void loadData() {
        if (getDataMission(id).isEmpty()) {
            findViewById(R.id.textnohave).setVisibility(View.VISIBLE);
        } else {
            RecyclerView.Adapter adapter = new MemberStatisticAdapter(getDataMission(id), this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(StatisticMember.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(StatisticMember.this, MusicService.class));
        startService(new Intent(StatisticMember.this, MusicService.class));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editDetail : ShowdialogEdit();
                break;
        }
    }

    private void ShowdialogEdit() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_custom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_editdetail_member);
        dialog.setCancelable(true);

        dialog.show();

    }
}
