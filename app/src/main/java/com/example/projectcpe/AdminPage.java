package com.example.projectcpe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.CreateMission.MissionCreate;
import com.example.projectcpe.CreateMission.MissionDelete;
import com.example.projectcpe.CreateMission.Export.MissionExport;
import com.example.projectcpe.CreateMission.Import.MissionImport;
import com.example.projectcpe.PlayingMode.DetailMission;
import com.example.projectcpe.ViewModel.Admin;

public class AdminPage extends AppCompatActivity {

    public static final String MY_PRE_NAME_ADMIN = "com.example.projectcpe.nameadmin";

    TextView txName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txName = findViewById(R.id.txName);

        txName.setText(getMyPreNameAdmin());

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

        return true;
    }

    @Override
    public void onBackPressed() {



        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout setup");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to logout?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AdminPage.this, BeginMember.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        new ButtonServiceEffect(this).startEffect(); // Sound button effect
        dialog.show();
        // your code.



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

    public String getMyPreNameAdmin() {


        SharedPreferences getPassword = getSharedPreferences(MY_PRE_NAME_ADMIN, MODE_PRIVATE);
        String gettingPassword = "";
        String name = getPassword.getString("Name", gettingPassword);


        return name;

    }
}
