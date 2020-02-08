package com.example.projectcpe.CreateMission.Export;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectcpe.CreateMission.Import.ImportOnDevice;
import com.example.projectcpe.CreateMission.Import.ImportOnGoogledrive;
import com.example.projectcpe.CreateMission.Import.MissionImport;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;

public class MissionExport extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_export);

        BindVariable();

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
                startActivity(new Intent(MissionExport.this, ExportOnDevice.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.cardGoogledrive :
                startActivity(new Intent(MissionExport.this, ExportOnGoogleDrive.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(MissionExport.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MissionExport.this, MusicService.class));
        startService(new Intent(MissionExport.this, MusicService.class));
    }

}
