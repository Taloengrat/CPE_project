package com.example.projectcpe.CreateMission;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.AdminPage;
import com.example.projectcpe.BeginMember;
import com.example.projectcpe.HomePage;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionDelete extends AppCompatActivity implements MissionAdapter.OnCustomerItemClick{
    RecyclerView recyclerView;
    List<Mission> missionsList;
    private MissionAdapter.OnCustomerItemClick onCustomerItemClick;
    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_delete);
        missionsList = new ArrayList<>();
        recyclerView =findViewById(R.id.recyclerViewdelete);

        loadData();



    }



    private void loadData() {
        RecyclerView.Adapter adapter = new MissionAdapter( getMissionList(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }


    private List<Mission> getMissionList() {
        return MissionDATABASE.getInstance(getApplicationContext()).missionDAO().getAllMission();
    }



    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }


    @Override
    public void onCustomerClick(final int pos, int result, final Mission missionlist) {
switch (result){

    case 0 : new AlertDialog.Builder(MissionDelete.this)
            .setTitle("Delete this mission?")
            .setItems(new String[]{"Yes", "No"},
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:

                                    File dir = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionlist.getMissionName());

                                    if (dir.isDirectory()) {
                                        String[] children = dir.list();
                                        for (int l = 0; l < children.length; l++) {
                                            new File(dir, children[l]).delete();
                                        }

                                        dir.delete();
                                    }

                                    MissionDATABASE.getInstance(MissionDelete.this).missionDAO().delete(missionlist);
                                    missionsList.remove(missionlist);

                                    recreate();
                                    break;
                                case 1:

                                    break;
                            }
                        }
                    }).show();

}
    }



    @Override
    public void onBackPressed() {
        // your code.

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(MissionDelete.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MissionDelete.this, MusicService.class));
        startService(new Intent(MissionDelete.this, MusicService.class));
    }
}
