package com.example.projectcpe.CreateMission;

import android.app.Dialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectcpe.AdminPage;
import com.example.projectcpe.R;

public class MissionExport extends AppCompatActivity {

    Button btSelect, btSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_export);

        BindVariable();

    }
    public void setBtSelectClick(View v){
        final Dialog dialog = new Dialog(MissionExport.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);

        Button btOndevice = (Button)dialog.findViewById(R.id.ChooseCamera);
        btOndevice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "On Device", Toast.LENGTH_SHORT).show();
            }
        });

        Button button1 = (Button)dialog.findViewById(R.id.ok);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()
                        , "Google Drive", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
    }

    public void setBtSubmit(View v){

    }

    private void BindVariable(){
        btSelect = findViewById(R.id.btSelectIm);
        btSubmit = findViewById(R.id.btSubmitIm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // your code.
        startActivity(new Intent(MissionExport.this, AdminPage.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

}
