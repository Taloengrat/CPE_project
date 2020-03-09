package com.example.projectcpe;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.projectcpe.CSV.Utility.PermissionUtility;
import com.example.projectcpe.PlayingMode.PlayPage;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    ImageView _logoApp;
    EditText _etPass,_etNameAdmin;
    ImageView imMedium;
    Button _btBegin;
    boolean box1=true, box2=true, box3=true, box4=true, box5=true, box6=true;

    private int ms = 0;
    private int seconds = 0;
    private int minutes;

    ProgressBar progressBar;
    private Timer timer;
    protected boolean running = false;

    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/";

    public final int WRITE_PERMISSON_REQUEST_CODE = 1;


    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
    public static final String MY_PRE_NAME_ADMIN = "com.example.projectcpe.nameadmin";
    private volatile boolean stopThread = false;
    ProgressDialog loadingDialog;
    private int percent = 0;

    ///////////////////// progress dialog percent

    private void updateTimerText() {

        loadingDialog.setMessage(String.format("ความคืบหน้า : %02d ", percent)+"%");

    }

    private void updateSeconds() {
        ms = 0;

        percent+=2;
        if (percent > 100) {
            percent = 0;

        }
    }

    private void updateMs() {
        ms+=2;
        if (ms == 10) {
            updateSeconds();
        }
    }


    private void startTimer() {


        timer = new Timer();

        running = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runTimer();
            }
        }, 0, 100);


    }

    private void stopTimer() {
        running = false;
//        btnStart.setText("Start");
        timer.cancel();
    }

    Runnable percentTick = new Runnable() {
        @Override
        public void run() {

            if (percent == 100) {

                stopTimer();
                percent = 0;

            } else {

                updateTimerText();
                updateMs();
            }
        }
    };

    private void runTimer() {
        {
            this.runOnUiThread(percentTick);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PermissionUtility.askPermissionForActivity(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_PERMISSON_REQUEST_CODE)) {


        }


        _logoApp = (ImageView) findViewById(R.id.logoApp);
        _btBegin = (Button) findViewById(R.id._etBegin);

        _etPass = (EditText) findViewById(R.id.etPass);
        _etNameAdmin = findViewById(R.id.etAdminname);


        _btBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                CreateMissionDefault();

                startService(new Intent(MainActivity.this, ButtonServiceEffect.class)); // Sound button effect
                _etPass.setVisibility(View.VISIBLE);
                _etNameAdmin.setVisibility(View.VISIBLE);
                _btBegin.setText("LET's GO !");

                _btBegin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startService(new Intent(MainActivity.this, ButtonServiceEffect.class)); // Sound button effect
                        String directory_path1 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/Introduction";
                        File file1 = new File(directory_path1);
                        if (!file1.exists()) {
                            file1.mkdirs();
                        }

                        String directory_path3 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/Animal";
                        File file3 = new File(directory_path3);
                        if (!file3.exists()) {
                            file3.mkdirs();
                        }

                        String directory_path6 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/Career";
                        File file6 = new File(directory_path6);
                        if (!file6.exists()) {
                            file6.mkdirs();
                        }

                        String directory_path2 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/SayHello";
                        File file2 = new File(directory_path2);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }



                        String directory_path4 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/WhyJamieSad";
                        File file4 = new File(directory_path4);
                        if (!file4.exists()) {
                            file4.mkdirs();
                        }
                        String directory_path5 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/BuyFlower";
                        File file5 = new File(directory_path5);
                        if (!file5.exists()) {
                            file5.mkdirs();
                        }

                        if (_etNameAdmin.getText().toString().isEmpty()){
                            Snackbar.make(_etPass, "Plese enter your name for Admin !", Snackbar.LENGTH_SHORT).show();
                        }else {
                            SharedPreferences.Editor name = getSharedPreferences(MY_PRE_NAME_ADMIN, MODE_PRIVATE).edit();
                            name.putString("Name", _etNameAdmin.getText().toString().trim());
                            name.commit();
                        }

                        if (_etPass.getText().toString().isEmpty()) {
                            Snackbar.make(_etPass, "Plese enter your password for Admin !", Snackbar.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor password = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE).edit();
                            password.putInt("Pass", Integer.parseInt(_etPass.getText().toString().trim()));
                            password.commit();
                            Snackbar.make(_etPass, "Created Password For Admin", Snackbar.LENGTH_SHORT).show();


//
                            loadingDialog = ProgressDialog.show(MainActivity.this, "เตรียมข้อมูลแบบทดสอบ", "กำลังเตรียมแบบทดสอบ...", true, false);


                            startThread();


                        }
                    }
                });
            }


        });

    }

    private void CreateMissionDefault() {


        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mission1_1)).getBitmap(), "picture1", "Introduction");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mission1_2)).getBitmap(), "picture2", "Introduction");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mission1_3)).getBitmap(), "picture3", "Introduction");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mission1_4)).getBitmap(), "picture4", "Introduction");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mission1_5)).getBitmap(), "picture5", "Introduction");


        Mission mission = new Mission("Introduction", "Suggestions for playing the quiz.", 7, 5
                , directory_path + "Introduction" + "/picture1.jpg", directory_path + "Introduction" + "/picture2.jpg", directory_path + "Introduction" + "/picture3.jpg", directory_path + "Introduction" + "/picture4.jpg", directory_path + "Introduction" + "/picture5.jpg"
                ,"Follow the instructions.","Follow the instructions.","Follow the instructions.","Follow the instructions.","Say \"I am understand\" to finish.","Hello","I am understand","I am understand","I am understand","I am understand"
                ,"10","10","10","10","10"
                ,"Say\"Hello\"","Say \"I am understand\"","Say \"I am understand\"","Say \"I am understand\"","Say \"I am understand\""
        );






//        Mission mission = new Mission("Introduction", "Suggestions for playing the quiz.", 5, 5
//                , directory_path + "Introduction", directory_path + "Introduction", directory_path + "Introduction", directory_path + "Introduction", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color"
//                , getResources().getString(R.string.q1_1), getResources().getString(R.string.q1_2), getResources().getString(R.string.q1_3), getResources().getString(R.string.q1_4), getResources().getString(R.string.q1_5)
//                , getResources().getString(R.string.q1_6), getResources().getString(R.string.q1_7), getResources().getString(R.string.q1_8)
//                , getResources().getString(R.string.q1_9), getResources().getString(R.string.q1_10)
//                , getResources().getString(R.string.a1_1).trim(), getResources().getString(R.string.a1_2).trim(), getResources().getString(R.string.a1_3).trim()
//                , getResources().getString(R.string.a1_4).trim(), getResources().getString(R.string.a1_5).trim()
//                , getResources().getString(R.string.a1_6).trim(), getResources().getString(R.string.a1_7).trim(), getResources().getString(R.string.a1_8).trim()
//                , getResources().getString(R.string.a1_9).trim(), getResources().getString(R.string.a1_10).trim()
//
//                , getResources().getString(R.string.s1_1).trim(), getResources().getString(R.string.s1_2).trim(), getResources().getString(R.string.s1_3).trim()
//                , getResources().getString(R.string.s1_4).trim(), getResources().getString(R.string.s1_5).trim(), getResources().getString(R.string.s1_6).trim()
//                , getResources().getString(R.string.s1_7).trim(), getResources().getString(R.string.s1_8).trim(), getResources().getString(R.string.s1_9).trim()
//                , getResources().getString(R.string.s1_10).trim()
//
//                , getResources().getString(R.string.h1_1).trim(), getResources().getString(R.string.h1_2).trim(), getResources().getString(R.string.h1_3).trim()
//                , getResources().getString(R.string.h1_4).trim(), getResources().getString(R.string.h1_5).trim(), getResources().getString(R.string.h1_6).trim()
//                , getResources().getString(R.string.h1_7).trim(), getResources().getString(R.string.h1_8).trim(), getResources().getString(R.string.h1_9).trim()
//                , getResources().getString(R.string.h1_10).trim());

        mission.setTime("1:00");
        mission.setTimeDeduction("0:30");


        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission);



    }

    private void CreateMissionZoo() {

        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z1)).getBitmap(), "picture1", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z2)).getBitmap(), "picture2", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z3)).getBitmap(), "picture3", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z4)).getBitmap(), "picture4", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z5)).getBitmap(), "picture5", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z6)).getBitmap(), "picture6", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z7)).getBitmap(), "picture7", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z8)).getBitmap(), "picture8", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z9)).getBitmap(), "picture9", "Animal");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z10)).getBitmap(), "picture10", "Animal");

        Mission mission3 = new Mission("Animal", "Various animals", 9, 10
                , directory_path + "Animal" + "/picture1.jpg", directory_path + "Animal" + "/picture2.jpg", directory_path + "Animal" + "/picture3.jpg", directory_path + "Animal" + "/picture4.jpg", directory_path + "Animal" + "/picture5.jpg", directory_path + "Animal" + "/picture6.jpg", directory_path + "Animal" + "/picture7.jpg", directory_path + "Animal" + "/picture8.jpg", directory_path + "Animal" + "/picture9.jpg", directory_path + "Animal" + "/picture10.jpg"
                , getResources().getString(R.string.q3_1), getResources().getString(R.string.q3_2), getResources().getString(R.string.q3_3), getResources().getString(R.string.q3_4), getResources().getString(R.string.q3_5)
                , getResources().getString(R.string.q3_6), getResources().getString(R.string.q3_7), getResources().getString(R.string.q3_8)
                , getResources().getString(R.string.q3_9), getResources().getString(R.string.q3_10)
                , getResources().getString(R.string.a3_1).trim(), getResources().getString(R.string.a3_2).trim(), getResources().getString(R.string.a3_3).trim()
                , getResources().getString(R.string.a3_4).trim(), getResources().getString(R.string.a3_5).trim()
                , getResources().getString(R.string.a3_6).trim(), getResources().getString(R.string.a3_7).trim(), getResources().getString(R.string.a3_8).trim()
                , getResources().getString(R.string.a3_9).trim(), getResources().getString(R.string.a3_10).trim()

                , "10/7".trim(), "10/7".trim(), getResources().getString(R.string.s3_3).trim()
                , getResources().getString(R.string.s3_4).trim(), getResources().getString(R.string.s3_5).trim(), getResources().getString(R.string.s3_6).trim()
                , getResources().getString(R.string.s3_7).trim(), getResources().getString(R.string.s3_8).trim(), getResources().getString(R.string.s3_9).trim()
                , getResources().getString(R.string.s3_10).trim()

                , getResources().getString(R.string.h3_1).trim(), getResources().getString(R.string.h3_2).trim(), getResources().getString(R.string.h3_3).trim()
                , getResources().getString(R.string.h3_4).trim(), getResources().getString(R.string.h3_5).trim(), getResources().getString(R.string.h3_6).trim()
                , getResources().getString(R.string.h3_7).trim(), getResources().getString(R.string.h3_8).trim(), getResources().getString(R.string.h3_9).trim()
                , getResources().getString(R.string.h3_10).trim());

        mission3.setTime("1:00");
        mission3.setTimeDeduction("0:30");
        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission3);


    }

    private void CreateMissionColor() {

        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z1)).getBitmap(), "picture1", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z2)).getBitmap(), "picture2", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z3)).getBitmap(), "picture3", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z4)).getBitmap(), "picture4", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z5)).getBitmap(), "picture5", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z6)).getBitmap(), "picture6", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z7)).getBitmap(), "picture7", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z8)).getBitmap(), "picture8", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z9)).getBitmap(), "picture9", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.z10)).getBitmap(), "picture10", "Color");

        Mission mission3 = new Mission("Color", "Various color", 9, 10
                , directory_path + "Animal" + "/picture1.jpg", directory_path + "Animal" + "/picture2.jpg", directory_path + "Animal" + "/picture3.jpg", directory_path + "Animal" + "/picture4.jpg", directory_path + "Animal" + "/picture5.jpg", directory_path + "Animal" + "/picture6.jpg", directory_path + "Animal" + "/picture7.jpg", directory_path + "Animal" + "/picture8.jpg", directory_path + "Animal" + "/picture9.jpg", directory_path + "Animal" + "/picture10.jpg"
                , getResources().getString(R.string.q3_1), getResources().getString(R.string.q3_2), getResources().getString(R.string.q3_3), getResources().getString(R.string.q3_4), getResources().getString(R.string.q3_5)
                , getResources().getString(R.string.q3_6), getResources().getString(R.string.q3_7), getResources().getString(R.string.q3_8)
                , getResources().getString(R.string.q3_9), getResources().getString(R.string.q3_10)
                , getResources().getString(R.string.a3_1).trim(), getResources().getString(R.string.a3_2).trim(), getResources().getString(R.string.a3_3).trim()
                , getResources().getString(R.string.a3_4).trim(), getResources().getString(R.string.a3_5).trim()
                , getResources().getString(R.string.a3_6).trim(), getResources().getString(R.string.a3_7).trim(), getResources().getString(R.string.a3_8).trim()
                , getResources().getString(R.string.a3_9).trim(), getResources().getString(R.string.a3_10).trim()

                , "10/7".trim(), "10/7".trim(), getResources().getString(R.string.s3_3).trim()
                , getResources().getString(R.string.s3_4).trim(), getResources().getString(R.string.s3_5).trim(), getResources().getString(R.string.s3_6).trim()
                , getResources().getString(R.string.s3_7).trim(), getResources().getString(R.string.s3_8).trim(), getResources().getString(R.string.s3_9).trim()
                , getResources().getString(R.string.s3_10).trim()

                , getResources().getString(R.string.h3_1).trim(), getResources().getString(R.string.h3_2).trim(), getResources().getString(R.string.h3_3).trim()
                , getResources().getString(R.string.h3_4).trim(), getResources().getString(R.string.h3_5).trim(), getResources().getString(R.string.h3_6).trim()
                , getResources().getString(R.string.h3_7).trim(), getResources().getString(R.string.h3_8).trim(), getResources().getString(R.string.h3_9).trim()
                , getResources().getString(R.string.h3_10).trim());

        mission3.setTime("1:00");
        mission3.setTimeDeduction("0:30");
        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission3);


    }

    private void CreateMissionCareer() {

        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c1)).getBitmap(), "picture1", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c2)).getBitmap(), "picture2", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c3)).getBitmap(), "picture3", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c4)).getBitmap(), "picture4", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c5)).getBitmap(), "picture5", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c6)).getBitmap(), "picture6", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c7)).getBitmap(), "picture7", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c8)).getBitmap(), "picture8", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c9)).getBitmap(), "picture9", "Career");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.c10)).getBitmap(), "picture10", "Career");

        Mission mission3 = new Mission("Career", "Various Career.", 9, 10
                , directory_path + "Career" + "/picture1.jpg"
                , directory_path + "Career" + "/picture2.jpg"
                , directory_path + "Career" + "/picture3.jpg"
                , directory_path + "Career" + "/picture4.jpg"
                , directory_path + "Career" + "/picture5.jpg"
                , directory_path + "Career" + "/picture6.jpg"
                , directory_path + "Career" + "/picture7.jpg"
                , directory_path + "Career" + "/picture8.jpg"
                , directory_path + "Career" + "/picture9.jpg"
                , directory_path + "Career" + "/picture10.jpg"

                , getResources().getString(R.string.q4_1)
                , getResources().getString(R.string.q4_2)
                , getResources().getString(R.string.q4_3)
                , getResources().getString(R.string.q4_4)
                , getResources().getString(R.string.q4_5)
                , getResources().getString(R.string.q4_6)
                , getResources().getString(R.string.q4_7)
                , getResources().getString(R.string.q4_8)
                , getResources().getString(R.string.q4_9)
                , getResources().getString(R.string.q4_10)

                , getResources().getString(R.string.a4_1).trim()
                , getResources().getString(R.string.a4_2).trim()
                , getResources().getString(R.string.a4_3).trim()
                , getResources().getString(R.string.a4_4).trim()
                , getResources().getString(R.string.a4_5).trim()
                , getResources().getString(R.string.a4_6).trim()
                , getResources().getString(R.string.a4_7).trim()
                , getResources().getString(R.string.a4_8).trim()
                , getResources().getString(R.string.a4_9).trim()
                , getResources().getString(R.string.a4_10).trim()

                , getResources().getString(R.string.s4_1).trim()
                , getResources().getString(R.string.s4_2).trim()
                , getResources().getString(R.string.s4_3).trim()
                , getResources().getString(R.string.s4_4).trim()
                , getResources().getString(R.string.s4_5).trim()
                , getResources().getString(R.string.s4_6).trim()
                , getResources().getString(R.string.s4_7).trim()
                , getResources().getString(R.string.s4_8).trim()
                , getResources().getString(R.string.s4_9).trim()
                , getResources().getString(R.string.s4_10).trim()

                , getResources().getString(R.string.h4_1).trim()
                , getResources().getString(R.string.h4_2).trim()
                , getResources().getString(R.string.h4_3).trim()
                , getResources().getString(R.string.h4_4).trim()
                , getResources().getString(R.string.h4_5).trim()
                , getResources().getString(R.string.h4_6).trim()
                , getResources().getString(R.string.h4_7).trim()
                , getResources().getString(R.string.h4_8).trim()
                , getResources().getString(R.string.h4_9).trim()
                , getResources().getString(R.string.h4_10).trim());

        mission3.setTime("1:00");
        mission3.setTimeDeduction("0:30");
        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission3);


    }

    private void CreateMissionTwo() {


        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_1)).getBitmap(), "picture1", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_2)).getBitmap(), "picture2", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_3)).getBitmap(), "picture3", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_4)).getBitmap(), "picture4", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_5)).getBitmap(), "picture5", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_6)).getBitmap(), "picture6", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_7)).getBitmap(), "picture7", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_8)).getBitmap(), "picture8", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_9)).getBitmap(), "picture9", "SayHello");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m2_10)).getBitmap(), "picture10", "SayHello");


        Mission mission2 = new Mission("SayHello", "general description.", 15, 10
                , directory_path + "SayHello" + "/picture1.jpg", directory_path + "SayHello" + "/picture2.jpg", directory_path + "SayHello" + "/picture3.jpg", directory_path + "SayHello" + "/picture4.jpg", directory_path + "SayHello" + "/picture5.jpg", directory_path + "SayHello" + "/picture6.jpg", directory_path + "SayHello" + "/picture7.jpg", directory_path + "SayHello" + "/picture8.jpg", directory_path + "SayHello" + "/picture9.jpg", directory_path + "SayHello" + "/picture10.jpg"
                , "Say hello to Peter.", "How is Peter's body ?", "What color is Peter's shirt ?", "What color is Peter's pants ?", "How do you think Peter feel?"
                , "Say hello to Jamie.", "How is Jamie's body?", "What color is Jamie's shirt?"
                , "What color is Jamie's pants?", "How do you think Jamie feel?"
                , "hello peter/hello/hi peter/hi".trim(), "Peter's body is stout/Peter's body is fat/stout/fat".trim(), "Peter's shirt is black color/Peter's shirt is black/it is black/black".trim()
                , "Peter's pants is black color/Peter's pants is black/it is black/black".trim(), "I think Peter feel happy/Peter feel happy/Peter seems happy/Peter look like happy/happy".trim()
                , "hello Jamie/hello/hi Jamie/hi".trim(), "Jamie's body is thin/Jamie's body is skinny/thin/skinny".trim(), "Jamie's shirt is blue color/Jamie's shirt is blue/it is blue/blue".trim()
                , "Jamie's pants is black color/Jamie's pants is black/it is black/black".trim(), "I think Jamie feel sad/Jamie feel sad/Jamie seems sad/Jamie look like sad/sad".trim()

                , "10/9/8/8".trim(), "10/8/6/6".trim(), "10/9/7/5".trim()
                , "10/9/7/5".trim(), "10/9/8/8/5".trim(), "10/9/8/8".trim()
                , "10/8/6/6".trim(), "10/9/7/5".trim(), "10/9/7/5".trim()
                , "10/9/8/8/5".trim()

                , "H___o ____/Hello ____/Hello P____/Hello Peter".trim(), "_____ ____ is ____/Peter's ____ is ____/Peter's body is ____/Peter's body is stout".trim(), "____ shirt is ____ ____/Peter's shirt is ____ ____/Peter's shirt is ____ color/Peter's shirt is black color".trim()
                , "____ pants is ____ ____/Peter's pants is ____ ____/Peter's pants is ____ color/Peter's pants is black color".trim(), "I ____ ____ feel ____/I think ____ feel ____/I think Peter feel ____/I think Peter feel happy".trim(), "H___o ____/Hello ____/Hello J____/Hello Jamie".trim()
                , "_____ ____ is ____/Jamie's ____ is ____/Jamie's body is ____/Jamie's body is thin".trim(), "____ shirt is ____ ____/Jamie's shirt is ____ ____/Jamie's shirt is ____ color/Jamie's shirt is blue color".trim(), "____ pants is ____ ____/Jamie's pants is ____ ____/Jamie's pants is ____ color/Jamie's pants is black color".trim()
                , "I ____ ____ feel ____/I think ____ feel ____/I think Jamie feel ____/I think Jamie feel sad".trim());

        mission2.setTime("1:10");
        mission2.setTimeDeduction("0:35");

        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission2);



    }



    private void CreateMissionThree() {

        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m3_1)).getBitmap(), "picture1", "WhyJamieSad");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m3_2)).getBitmap(), "picture2", "WhyJamieSad");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m3_3)).getBitmap(), "picture3", "WhyJamieSad");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m3_4)).getBitmap(), "picture4", "WhyJamieSad");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m3_5)).getBitmap(), "picture5", "WhyJamieSad");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m3_6)).getBitmap(), "picture6", "WhyJamieSad");

        Mission mission = new Mission("WhyJamieSad", "Jamie's story", 12, 6
                , directory_path + "WhyJamieSad" + "/picture1.jpg"
                , directory_path + "WhyJamieSad" + "/picture2.jpg"
                , directory_path + "WhyJamieSad" + "/picture3.jpg"
                , directory_path + "WhyJamieSad" + "/picture4.jpg"
                , directory_path + "WhyJamieSad" + "/picture5.jpg"
                , directory_path + "WhyJamieSad" + "/picture6.jpg"

                ,"What do you think this women is related to jamie ?"
                ,"What are they doing ?"
                ,"How does she feel ?"
                ,"Why does she angry ?"
                ,"Where is she going ?"
                ,"Do you think why jamie is sad ?"

                ,"She is his girlfriend/She is jamie's girlfriend/her is girlfriend/girlfriend".trim()
                ,"They are talking/She is asking to Jamie/Talking".trim()
                ,"She feel angry/She is angry/angry".trim()
                ,"Because he forget the anniversary/Because Jamie forget the anniversary/he forget the anniversary/Because he forget anniversary/Because Jamie forget anniversary/he forget anniversary/forget the anniversary/forget anniversary".trim()
                ,"She is going home/going home/go to home/home"
                ,"I think he sad because he broken heart/I think jamie sad because he broken heart/because he broken heart/because jamie broken heart/he broken heart/jamie broken heart/broken heart".trim()

                ,"10/10/8/6".trim(),"10/9/6".trim(),"10/10/8".trim(),"10/10/9/8/8/7/6/5".trim(),"10/8/8/6".trim(),"10/10/8/8/7/7/6".trim()

                ,"She __ ___ ______/She is ___ ______/She is his ______/She is his girlfriend".trim()
                ,"They ___ ____/They are ____/They are talking".trim()
                ,"She ___ ____/She feel ____/She feel angry".trim()
                ,"Because ___ ____ ___ ________/Because ___ forget ___ ________/Because he forget the ________/Because he forget the anniversary".trim()
                ,"She __ ____ ____/She __ going ____/She is going ____/She is going home".trim()
                ,"I think ___ ___ because he ____ ____/I think he ___ because he _____ _____/I think he sad because he _____ heart/I think he sad because he broken heart".trim()
                );




//        Mission careers = new Mission("WhyJamieSad", "Jamie's story", 9, 6
//                , directory_path + "WhyJamieSad" + "/picture1.jpg", directory_path + "WhyJamieSad" + "/picture2.jpg", directory_path + "WhyJamieSad" + "/picture3.jpg", directory_path + "WhyJamieSad" + "/picture4.jpg", directory_path + "WhyJamieSad" + "/picture5.jpg", directory_path + "WhyJamieSad" + "/picture6.jpg", directory_path + "Career" + "/picture7.jpg", directory_path + "Career" + "/picture8.jpg", directory_path + "Career" + "/picture9.jpg", directory_path + "Career" + "/picture10.jpg"
//                , getResources().getString(R.string.q4_1), getResources().getString(R.string.q4_2), getResources().getString(R.string.q4_3), getResources().getString(R.string.q4_4), getResources().getString(R.string.q4_5)
//                , getResources().getString(R.string.q4_6), getResources().getString(R.string.q4_7), getResources().getString(R.string.q4_8)
//                , getResources().getString(R.string.q4_9), getResources().getString(R.string.q4_10)
//                , getResources().getString(R.string.a4_1).trim(), getResources().getString(R.string.a4_2).trim(), getResources().getString(R.string.a4_3).trim()
//                , getResources().getString(R.string.a4_4).trim(), getResources().getString(R.string.a4_5).trim()
//                , getResources().getString(R.string.a4_6).trim(), getResources().getString(R.string.a4_7).trim(), getResources().getString(R.string.a4_8).trim()
//                , getResources().getString(R.string.a4_9).trim(), getResources().getString(R.string.a4_10).trim()
//
//                , getResources().getString(R.string.s4_1).trim(), getResources().getString(R.string.s4_2).trim(), getResources().getString(R.string.s4_3).trim()
//                , getResources().getString(R.string.s4_4).trim(), getResources().getString(R.string.s4_5).trim(), getResources().getString(R.string.s4_6).trim()
//                , getResources().getString(R.string.s4_7).trim(), getResources().getString(R.string.s4_8).trim(), getResources().getString(R.string.s4_9).trim()
//                , getResources().getString(R.string.s4_10).trim()
//
//                , getResources().getString(R.string.h4_1).trim(), getResources().getString(R.string.h4_2).trim(), getResources().getString(R.string.h4_3).trim()
//                , getResources().getString(R.string.h4_4).trim(), getResources().getString(R.string.h4_5).trim(), getResources().getString(R.string.h4_6).trim()
//                , getResources().getString(R.string.h4_7).trim(), getResources().getString(R.string.h4_8).trim(), getResources().getString(R.string.h4_9).trim()
//                , getResources().getString(R.string.h4_10).trim());

        mission.setTime("1:10");
        mission.setTimeDeduction("0:35");
        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission);
    }

    private void CreateMissionBuyFlower() {

        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m5_1)).getBitmap(), "picture1", "BuyFlower");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m5_2)).getBitmap(), "picture2", "BuyFlower");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m5_3)).getBitmap(), "picture3", "BuyFlower");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m5_4)).getBitmap(), "picture4", "BuyFlower");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m5_5)).getBitmap(), "picture5", "BuyFlower");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.m5_6)).getBitmap(), "picture6", "BuyFlower");

        Mission mission = new Mission("BuyFlower", "Jamie would like to buy flower", 9, 6
                , directory_path + "BuyFlower" + "/picture1.jpg"
                , directory_path + "BuyFlower" + "/picture2.jpg"
                , directory_path + "BuyFlower" + "/picture3.jpg"
                , directory_path + "BuyFlower" + "/picture4.jpg"
                , directory_path + "BuyFlower" + "/picture5.jpg"
                , directory_path + "BuyFlower" + "/picture6.jpg"

                ,"Help him find money."
                ,"Help him find car key."
                ,"Tell jamie where is his car."
                ,"Tell jamie where is flower shop."
                ,"He arrived at the flower shop. What flowers would he like to buy ?"
                ,"Who does he give a flower to ?"

                ,"Money is on the table/Money placed on the table/on the table/on table".trim()
                ,"Car key is under the table/under the table/under table".trim()
                ,"His car is in the parking lot/car is in the parking lot/in the parking lot/in parking lot".trim()
                ,"The flower shop is opposite the tesco lotus/The flower shop is near the tesco lotus/opposite the tesco lotus/near the tesco lotus/opposite tesco lotus/near tesco lotus".trim()
                ,"He would like to buy a rose/He wants to buy a rose/buy a rose/buy rose/a rose/rose/rose flower".trim()
                ,"He gave a flower to his girlfriend/his girlfriend/jamie's girlfriend/girlfriend/He give a flower to his girlfriend".trim()

                ,"10/10/8/6".trim(),"10/8/6".trim(),"10/10/7/5".trim(),"10/10/8/8/5/5".trim(),"10/10/8/7/6/5/5".trim(),"10/8/8/6/6".trim()

                ,"____ is __ ___ ____/____ is on ___ ____/Money is on the ____/Money is on the table".trim()
                ,"___ key is ____ ___ ____/Car key is ____ ___ ____/Car key is under the ____/Car key is under the table".trim()
                ,"His ___ is __ ___ _____ ___/His ___ is in ___ _____ lot/His ___ is in the _____ lot/His car is in the parking lot".trim()
                ,"The _____ ____ __ ______ the ____ ____/The _____ ____ is ______ the tesco lotus/The flower shop is ______ the tesco lotus/The flower shop is opposite the tesco lotus".trim()
                ,"He _____ ____ to ___ __ ____/He would ____ to ____ a ____/He would ____ to buy a ____/He would like to buy a rose".trim()
                ,"He ____ __ _____ to ___ ______/He ____ a _____ to his _______/He gave a _____ to his ______/He gave a flower to his girlfriend".trim()
        );


        mission.setTime("1:10");
        mission.setTimeDeduction("0:35");
        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission);
    }



    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open(filename + ".jpg")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    @Override
    protected void onStart() {
        super.onStart();

        startService(new Intent(this, MusicService.class));


        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
        int gettingPassword = 0;
        int gettedPassword = getPassword.getInt("Pass", gettingPassword);
        if (gettedPassword == 0) {

        } else {
            startActivity(new Intent(MainActivity.this, LogoIntro.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }


    private void CreateMember() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addmember_dialog);
        dialog.setCancelable(false);
        //

        final EditText _etName = dialog.findViewById(R.id.etname);
        final EditText _etAge = dialog.findViewById(R.id.checkbox2);
        final ImageView profile = dialog.findViewById(R.id.head);
        final EditText _etPassword = dialog.findViewById(R.id.checkbox3);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, ButtonServiceEffect.class));
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_profile);
                dialog.setCancelable(true);

                View.OnClickListener Clickkk = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        startService(new Intent(MainActivity.this, ButtonServiceEffect.class)); // Sound button effect
                        imMedium = (ImageView) view;
                        profile.setImageDrawable(imMedium.getDrawable());
                        dialog.cancel();
                    }
                };

                ImageView pro1, pro2, pro3, pro4, pro5, pro6, pro7, pro8, pro9, pro10, pro11, pro12, pro13, pro14, pro15;

                pro1 = dialog.findViewById(R.id.pro1);
                pro2 = dialog.findViewById(R.id.pro2);
                pro3 = dialog.findViewById(R.id.pro3);
                pro4 = dialog.findViewById(R.id.pro4);
                pro5 = dialog.findViewById(R.id.pro5);
                pro6 = dialog.findViewById(R.id.pro6);
                pro7 = dialog.findViewById(R.id.pro7);
                pro8 = dialog.findViewById(R.id.pro8);
                pro9 = dialog.findViewById(R.id.pro9);
                pro10 = dialog.findViewById(R.id.pro10);
                pro11 = dialog.findViewById(R.id.pro11);
                pro12 = dialog.findViewById(R.id.pro12);
                pro13 = dialog.findViewById(R.id.pro13);
                pro14 = dialog.findViewById(R.id.pro14);
                pro15 = dialog.findViewById(R.id.pro15);

                pro1.setOnClickListener(Clickkk);
                pro2.setOnClickListener(Clickkk);
                pro3.setOnClickListener(Clickkk);
                pro4.setOnClickListener(Clickkk);
                pro5.setOnClickListener(Clickkk);
                pro6.setOnClickListener(Clickkk);
                pro7.setOnClickListener(Clickkk);
                pro8.setOnClickListener(Clickkk);
                pro9.setOnClickListener(Clickkk);
                pro10.setOnClickListener(Clickkk);
                pro11.setOnClickListener(Clickkk);
                pro12.setOnClickListener(Clickkk);
                pro13.setOnClickListener(Clickkk);
                pro14.setOnClickListener(Clickkk);
                pro15.setOnClickListener(Clickkk);


                dialog.show();
            }
        });

        Button btSubmit = (Button) dialog.findViewById(R.id.ok);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, ButtonServiceEffect.class)); // Sound button effect
                if (_etName.getText().toString().isEmpty()) {
                    _etName.setError("Name is required");
                    _etName.requestFocus();
                    return;
                } else if ((_etName.length() > 13)) {

                    _etName.setError("Length your name is over");
                    _etName.requestFocus();
                } else if (_etAge.getText().toString().isEmpty()) {
                    _etAge.setError("Age is required");
                    _etAge.requestFocus();
                } else if (!(Integer.valueOf(_etAge.getText().toString()) <= 120)) {
                    _etAge.setError("Age Not defined");
                    _etAge.requestFocus();
                } else if (_etPassword.getText().toString().isEmpty()) {
                    _etPassword.setError("Password is required");
                    _etPassword.requestFocus();
                } else if (_etPassword.length() > 6) {
                    _etPassword.setError("Length Password is over 6");
                    _etPassword.requestFocus();
                } else if (imMedium != null) {

                    Bitmap bitmap = ((BitmapDrawable) imMedium.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();

                    Member newMember = new Member(_etName.getText().toString(), _etAge.getText().toString(), imageInByte, Integer.valueOf(_etPassword.getText().toString()));

                    MissionDATABASE.getInstance(MainActivity.this).missionDAO().createMember(newMember);
                    dialog.cancel();


                    startActivity(new Intent(MainActivity.this, LogoIntro.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                } else {
                    Toast.makeText(getApplicationContext(), "กรุณาเลือกรูปโปรไฟล์", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void ChooseMission() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameloadmission);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_mission);
        dialog.setCancelable(false);

        CheckBox checkBox1 = dialog.findViewById(R.id.checkbox1);
        CheckBox checkBox2 = dialog.findViewById(R.id.checkbox2);
        CheckBox checkBox3 = dialog.findViewById(R.id.checkbox3);
        CheckBox checkBox4 = dialog.findViewById(R.id.checkbox4);
        CheckBox checkBox5 = dialog.findViewById(R.id.checkbox5);

        Button ok = dialog.findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                loadingDialog = ProgressDialog.show(MainActivity.this, "เตรียมข้อมูลแบบทดสอบ", "กำลังเตรียมแบบทดสอบ...", true, false);

                dialog.dismiss();
                startThread();

            }
        });


        dialog.show();


    }

    private String saveToInternalStorage(Bitmap bitmapImage, String picturename, String missionName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionName + "/");
        // Create imageDir
        File mypath = new File(directory, picturename + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 30, fos);
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


            if (box1) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 1");
                    }
                });

                CreateMissionDefault();

                percent = 99;




            }

            if (box2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 2");


                    }
                });
                CreateMissionZoo();



                percent = 99;

            }

            if (box6) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 3");


                    }
                });
                CreateMissionCareer();




                percent = 99;

            }


            if (box3) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 4");


                    }
                });

                CreateMissionTwo();
                percent = 99;

            }
            if (box4) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 5");


                    }
                });
                CreateMissionThree();

                percent = 99;
            }
            if (box5) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 6");


                    }
                });
                CreateMissionBuyFlower();

                percent = 99;
            }


            stopThread = true;

            if (stopThread) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        loadingDialog.dismiss();
                        CreateMember();


                    }
                });
            }


        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(MainActivity.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MainActivity.this, MusicService.class));
        startService(new Intent(MainActivity.this, MusicService.class));
    }


}



