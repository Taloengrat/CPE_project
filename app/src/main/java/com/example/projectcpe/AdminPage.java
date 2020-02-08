package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectcpe.CreateMission.MissionCreate;
import com.example.projectcpe.CreateMission.MissionDelete;
import com.example.projectcpe.CreateMission.Export.MissionExport;
import com.example.projectcpe.CreateMission.Import.MissionImport;
import com.example.projectcpe.PlayingMode.DetailMission;
import com.example.projectcpe.ViewModel.Admin;

public class AdminPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
    public void FunctionAdmin(View view) {
        new ButtonServiceEffect(AdminPage.this).startEffect(); // Sound button effect
        switch (view.getId())
        {
            case R.id.CardCreate : startActivity(new Intent(AdminPage.this, MissionCreate.class)); break;
            case R.id.CardEditmember : startActivity(new Intent(this, FunctionEditProfile.class));break;
            case R.id.CardDelete : startActivity(new Intent(AdminPage.this, MissionDelete.class)); break;
            case R.id.CardImport : startActivity(new Intent(AdminPage.this, MissionImport.class)); break;
            case R.id.CardExport : startActivity(new Intent(AdminPage.this, MissionExport.class)); break;
            case R.id.CardChagepassword : startActivity(new Intent(AdminPage.this, FunctionEditPassword.class)); break;
        }

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {

        new ButtonServiceEffect(AdminPage.this).startEffect(); // Sound button effect
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(AdminPage.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(AdminPage.this, MusicService.class));
        startService(new Intent(AdminPage.this, MusicService.class));
    }
}
