package com.example.projectcpe.CreateMission;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectcpe.AdminPage;
import com.example.projectcpe.BeginMember;
import com.example.projectcpe.HomePage;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.PlayingMode.DetailMission;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

public class MissionCreate extends AppCompatActivity {

    Spinner spinner, spinnertime, spinerdeduction;
    Button btNext;
    EditText etName, etDetail, etAge;
    String asdf = "10/9/5/7";
    String[] asdfdsa = new String[]{"10", "9", "8", "7"};

    String[] items1_00 = new String[]{"1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};
    String[] items1_10 = new String[]{"1:10", "1:05", "1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};
    String[] items1_20 = new String[]{"1:20", "1:15", "1:10", "1:05", "1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};
    String[] items1_30 = new String[]{"1:30", "1:25", "1:20", "1:15", "1:10", "1:05", "1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};
    String[] items1_40 = new String[]{"1:40", "1:35", "1:30", "1:25", "1:20", "1:15", "1:10", "1:05", "1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};
    String[] items1_50 = new String[]{"1:50", "1:45", "1:40", "1:35", "1:30", "1:25", "1:20", "1:15", "1:10", "1:05", "1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};
    String[] items2_00 = new String[]{"2:00", "1:55", "1:50", "1:45", "1:40", "1:35", "1:30", "1:25", "1:20", "1:15", "1:10", "1:05", "1:00", "0:55", "0:50", "0:45", "0:40", "0:35", "0:30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_create);

        bindVariable();
        bindMenuSpinner();
        bindMenuSpinnertime();


        asdf = asdfdsa[0] + asdfdsa[1] + asdfdsa[2] + asdfdsa[3];

        spinnertime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                switch (position) {
                    case 0:
                        bindMenuSpinnerDeduction(items1_00);
                        break;
                    case 1:
                        bindMenuSpinnerDeduction(items1_10);
                        break;
                    case 2:
                        bindMenuSpinnerDeduction(items1_20);
                        break;
                    case 3:
                        bindMenuSpinnerDeduction(items1_30);
                        break;
                    case 4:
                        bindMenuSpinnerDeduction(items1_40);
                        break;
                    case 5:
                        bindMenuSpinnerDeduction(items1_50);
                        break;
                    case 6:
                        bindMenuSpinnerDeduction(items2_00);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void bindMenuSpinnertime() {
        String[] items = new String[]{"1:00", "1:10", "1:20", "1:30", "1:40", "1:50", "2:00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnertime.setAdapter(adapter);
    }

    //debug
    private void bindMenuSpinner() {
        String[] items = new String[]{"5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void bindVariable() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnertime = findViewById(R.id.spinertime);
        spinner = findViewById(R.id.snMissionStep);
        spinerdeduction = findViewById(R.id.spinerdeduction);
        btNext = findViewById(R.id.btNext);
        etName = findViewById(R.id.etMissionName);
        etDetail = findViewById(R.id.etMisisionDetail);
        etAge = findViewById(R.id.etMissionAge);

    }

    public void setBtNextOnClick(View v) {


        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Current Password is required");
            etName.requestFocus();
            return;
        }

        if (MissionDATABASE.getInstance(MissionCreate.this).missionDAO().CheckNameMission(etName.getText().toString()).getMissionName().toLowerCase().equals(etName.getText().toString().toLowerCase())){
            etName.setError("Mission name that is the same as that already exists");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etDetail.getText().toString())) {
            etDetail.setError("Current Password is required");
            etDetail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etAge.getText().toString())) {
            etDetail.setError("Current Password is required");
            etDetail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(spinner.getSelectedItem().toString())) {
            Toast.makeText(getApplicationContext(), "Please your number of step", Toast.LENGTH_SHORT).show();
            return;
        }

//        Mission create = new Mission(etName.getText().toString().trim(), etDetail.getText().toString().trim()
//                , Integer.parseInt(etAge.getText().toString()),Integer.parseInt(spinner.getSelectedItem().toString()));
//
//        MissionDATABASE.getInstance(MissionCreate.this).missionDAO().create(create);


//          Intent i  = new Intent(MissionCreate.this, HomePage.class);
//          startActivity(i);
//          finish();

        Intent i = new Intent(this, FinallyCreate.class);
        i.putExtra("name", etName.getText().toString());
        i.putExtra("detail", etDetail.getText().toString());
        i.putExtra("age", Integer.parseInt(etAge.getText().toString()));
        i.putExtra("NumOfStep", Integer.parseInt(spinner.getSelectedItem().toString()));
        i.putExtra("time", spinnertime.getSelectedItem().toString());
        i.putExtra("timededuction", spinerdeduction.getSelectedItem().toString());
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

        stopService(new Intent(MissionCreate.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MissionCreate.this, MusicService.class));
        startService(new Intent(MissionCreate.this, MusicService.class));
    }

    private void bindMenuSpinnerDeduction(String[] items) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinerdeduction.setAdapter(adapter);
    }
}
