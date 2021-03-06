package com.example.projectcpe.CreateMission.Export;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.BeginMember;
import com.example.projectcpe.CSV.Utility.EasyCsv;
import com.example.projectcpe.CSV.Utility.FileCallback;
import com.example.projectcpe.CreateMission.MissionDelete;
import com.example.projectcpe.LogoIntro;
import com.example.projectcpe.MainActivity;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExportOnDevice extends AppCompatActivity implements MissionAdapter.OnCustomerItemClick {

    RecyclerView recyclerView;
    List<Mission> missionList;
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;
    List<String> headerList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();

    String getDate;

    private volatile boolean stopThread = false;
    Handler pd = new Handler();
    Runnable Delay;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_on_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadData();


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

    public void loadData() {
        RecyclerView.Adapter adapter = new MissionAdapter(getMissionList(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Mission> getMissionList() {
        return MissionDATABASE.getInstance(this).missionDAO().getMissionAdmin();
    }

    @Override
    public void onCustomerClick(final int id, int result, Mission mission) {

        MediaPlayer.create(getApplicationContext(), R.raw.button1).start();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Export Mission On Device");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to export this mission?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                missionList = MissionDATABASE.getInstance(ExportOnDevice.this).missionDAO().getAllinfoOfMission(id);


                progressDialog = ProgressDialog.show(ExportOnDevice.this, "Export on device", "exporting...", true, false);

                startThread();


            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();

    }


    private void SwitchAddListString(int number) throws IOException {


        switch (number) {
            case 5:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5" +
                        ",Score1,Score2,Score3,Score4,Score5" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Time,TimeDeduction-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5()
                        + "," + missionList.get(0).getTime()+ "," + missionList.get(0).getTimeDeduction()+ "-");
                createFileCsv();


                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.jpg"))), "picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.jpg"))), "picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.jpg"))), "picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.jpg"))), "picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.jpg"))), "picture5");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Time,TimeDeduction-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6()
                        + "," + missionList.get(0).getTime() + ","+ missionList.get(0).getTimeDeduction()+ "-");


                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.jpg"))), "picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.jpg"))), "picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.jpg"))), "picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.jpg"))), "picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.jpg"))), "picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.jpg"))), "picture6");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Time,TimeDeduction-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7()
                        + "," + missionList.get(0).getTime()+ "," + missionList.get(0).getTimeDeduction()+ "-");


                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.jpg"))), "picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.jpg"))), "picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.jpg"))), "picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.jpg"))), "picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.jpg"))), "picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.jpg"))), "picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.jpg"))), "picture7");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Time,TimeDeduction-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7() + "," + missionList.get(0).getQ8()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7() + "," + missionList.get(0).getA8()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7() + "," + missionList.get(0).getS8()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7() + "," + missionList.get(0).getH8()
                        + "," + missionList.get(0).getTime()+ ","+ missionList.get(0).getTimeDeduction() + "-");


                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.jpg"))), "picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.jpg"))), "picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.jpg"))), "picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.jpg"))), "picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.jpg"))), "picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.jpg"))), "picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.jpg"))), "picture7");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture8.jpg"))), "picture8");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case 9:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8,Question9" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8,Answer9" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8,Score9" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Hint9,Time,TimeDeduction-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7() + "," + missionList.get(0).getQ8() + "," + missionList.get(0).getQ9()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7() + "," + missionList.get(0).getA8() + "," + missionList.get(0).getA9()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7() + "," + missionList.get(0).getS8() + "," + missionList.get(0).getS9()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7() + "," + missionList.get(0).getH8() + "," + missionList.get(0).getH9()
                        + "," + missionList.get(0).getTime()+ ","+ missionList.get(0).getTimeDeduction() + "-");


                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.jpg"))), "picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.jpg"))), "picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.jpg"))), "picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.jpg"))), "picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.jpg"))), "picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.jpg"))), "picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.jpg"))), "picture7");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture8.jpg"))), "picture8");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture9.jpg"))), "picture9");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                break;

            case 10:


                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8,Question9,Question10" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8,Answer9,Answer10" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8,Score9,Score10" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Hint9,Hint10,Time,TimeDeduction-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7() + "," + missionList.get(0).getQ8() + "," + missionList.get(0).getQ9() + "," + missionList.get(0).getQ10()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7() + "," + missionList.get(0).getA8() + "," + missionList.get(0).getA9() + "," + missionList.get(0).getA10()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7() + "," + missionList.get(0).getS8() + "," + missionList.get(0).getS9() + "," + missionList.get(0).getS10()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7() + "," + missionList.get(0).getH8() + "," + missionList.get(0).getH9() + "," + missionList.get(0).getH10()
                        + "," + missionList.get(0).getTime()  + ","+ missionList.get(0).getTimeDeduction()+ "-");


                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.jpg"))), "picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.jpg"))), "picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.jpg"))), "picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.jpg"))), "picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.jpg"))), "picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.jpg"))), "picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.jpg"))), "picture7");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture8.jpg"))), "picture8");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture9.jpg"))), "picture9");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture10.jpg"))), "picture10");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;


        }

//        File dir = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName());

//        if (dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                new File(dir, children[i]).delete();
//            }
//
//            dir.delete();
//        }


        stopThread = true;

//        MissionDATABASE.getInstance(ExportOnDevice.this).missionDAO().delete(missionList.get(0));


    }

    private String saveToInternalStorage(Bitmap bitmapImage, String foldername) {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = new File(Environment.getExternalStorageDirectory() + "/MyMissionExport/" + missionList.get(0).getMissionName() + "/");
        // Create imageDir
        File mypath = new File(directory, foldername + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();

    }

    private void createFileCsv() throws IOException {


        EasyCsv easyCsv = new EasyCsv(ExportOnDevice.this);

        easyCsv.setSeparatorColumn(",");
        easyCsv.setSeperatorLine("-");

        /// create folder
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/MyMissionExport/" + missionList.get(0).getMissionName();
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String fileName = "Data";

        easyCsv.createCsvFile(missionList.get(0).getMissionName().trim(), missionList.get(0).getMissionName() + fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {


            @Override
            public void onSuccess(File file) {

//                Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFail(String err) {
//                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void startThread() {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();

    }

    class ExampleRunnable implements Runnable {


        ExampleRunnable() {

        }

        @Override
        public void run() {


            try {
                SwitchAddListString(missionList.get(0).getNumberofMission());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (stopThread) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        progressDialog.dismiss();
                        Toast.makeText(ExportOnDevice.this,"นำออกแบบทดสอบบนอุปกรณ์เรียบร้อย \n" +
                                "foldername : MissionExport ",Toast.LENGTH_LONG).show();


                    }
                });
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(ExportOnDevice.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(ExportOnDevice.this, MusicService.class));
        startService(new Intent(ExportOnDevice.this, MusicService.class));
    }
}
