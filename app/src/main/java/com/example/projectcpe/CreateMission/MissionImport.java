package com.example.projectcpe.CreateMission;

import android.app.Dialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectcpe.R;

public class MissionImport extends AppCompatActivity {

    Button btSelect, btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_import);

        BindVariable();




    }
    public void setBtSelectClick(View v){
        final Dialog dialog = new Dialog(MissionImport.this);
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
    }
}
