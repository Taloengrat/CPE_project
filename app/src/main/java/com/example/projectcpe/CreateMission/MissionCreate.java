package com.example.projectcpe.CreateMission;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

public class MissionCreate extends AppCompatActivity {

    Spinner spinner;
    Button btNext;
    EditText etName, etDetail, etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_create);

        bindVariable();
        bindMenuSpinner();


    }



    private void bindMenuSpinner() {
        String[] items = new String[]{"5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void bindVariable(){
        spinner = findViewById(R.id.snMissionStep);
        btNext = findViewById(R.id.btNext);
        etName = findViewById(R.id.etMissionName);
        etDetail = findViewById(R.id.etMisisionDetail);
        etAge = findViewById(R.id.etMissionAge);
    }

    public void setBtNextOnClick(View v){


        if (TextUtils.isEmpty(etName.getText().toString())){
            etName.setError("Current Password is required");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etDetail.getText().toString())){
            etDetail.setError("Current Password is required");
            etDetail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etAge.getText().toString())){
            etDetail.setError("Current Password is required");
            etDetail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(spinner.getSelectedItem().toString())){
            Toast.makeText(getApplicationContext(), "Please your number of step", Toast.LENGTH_SHORT).show();
            return;
        }

        Mission create = new Mission(etName.getText().toString().trim(), etDetail.getText().toString().trim()
                , Integer.parseInt(etAge.getText().toString()),Integer.parseInt(spinner.getSelectedItem().toString()));

        MissionDATABASE.getInstance(MissionCreate.this).missionDAO().create(create);


//          Intent i  = new Intent(MissionCreate.this, HomePage.class);
//          startActivity(i);
//          finish();

        Intent i = new Intent(this, FinallyCreate.class);
        i.putExtra("name",etName.getText().toString());
        i.putExtra("detail",etDetail.getText().toString());
        i.putExtra("age",Integer.parseInt(etAge.getText().toString()));
        i.putExtra("NumOfStep",Integer.parseInt(spinner.getSelectedItem().toString()));

        startActivity(i);
        Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }

}
