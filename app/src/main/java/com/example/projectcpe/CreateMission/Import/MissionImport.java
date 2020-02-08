package com.example.projectcpe.CreateMission.Import;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;

import java.io.File;

public class MissionImport extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_import);

        BindVariable();

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/MyMission/" +"arm";
        File file = new File(directory_path);
        if (!file.exists())
        {
            file.mkdirs();
        }



    }

    private void BindVariable(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardDevice :
//                StoreCSV.storeCSV("test.csv");
                startActivity(new Intent(MissionImport.this, ImportOnDevice.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.cardGoogledrive :
                startActivity(new Intent(MissionImport.this, ImportOnGoogledrive.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(MissionImport.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MissionImport.this, MusicService.class));
        startService(new Intent(MissionImport.this, MusicService.class));
    }
}
