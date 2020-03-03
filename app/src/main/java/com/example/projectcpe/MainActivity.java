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
    EditText _etPass;
    ImageView imMedium;
    Button _btBegin;
    boolean box1=true, box2=true, box3=true, box4=true, box5=true;

    private int ms = 0;
    private int seconds = 0;
    private int minutes;

    ProgressBar progressBar;
    private Timer timer;
    protected boolean running = false;

    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/";

    public final int WRITE_PERMISSON_REQUEST_CODE = 1;


    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
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


        _btBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                CreateMissionDefault();

                startService(new Intent(MainActivity.this, ButtonServiceEffect.class)); // Sound button effect
                _etPass.setVisibility(View.VISIBLE);
                _btBegin.setText("LET's GO !");

                _btBegin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startService(new Intent(MainActivity.this, ButtonServiceEffect.class)); // Sound button effect
                        String directory_path1 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/Color";
                        File file1 = new File(directory_path1);
                        if (!file1.exists()) {
                            file1.mkdirs();
                        }

                        String directory_path2 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/TalktoJame";
                        File file2 = new File(directory_path2);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }

                        String directory_path3 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/Animal";
                        File file3 = new File(directory_path3);
                        if (!file3.exists()) {
                            file3.mkdirs();
                        }

                        String directory_path4 = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/Career";
                        File file4 = new File(directory_path4);
                        if (!file4.exists()) {
                            file4.mkdirs();
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


        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.red)).getBitmap(), "picture1", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.green)).getBitmap(), "picture2", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.blue)).getBitmap(), "picture3", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.yellow)).getBitmap(), "picture4", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.black)).getBitmap(), "picture5", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.white)).getBitmap(), "picture6", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.brown)).getBitmap(), "picture7", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.purple)).getBitmap(), "picture8", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.gray)).getBitmap(), "picture9", "Color");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.orange)).getBitmap(), "picture10", "Color");


        Mission mission = new Mission("Color", "Various colors", 5, 10
                , directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color", directory_path + "Color"
                , getResources().getString(R.string.q1_1), getResources().getString(R.string.q1_2), getResources().getString(R.string.q1_3), getResources().getString(R.string.q1_4), getResources().getString(R.string.q1_5)
                , getResources().getString(R.string.q1_6), getResources().getString(R.string.q1_7), getResources().getString(R.string.q1_8)
                , getResources().getString(R.string.q1_9), getResources().getString(R.string.q1_10)
                , getResources().getString(R.string.a1_1).trim(), getResources().getString(R.string.a1_2).trim(), getResources().getString(R.string.a1_3).trim()
                , getResources().getString(R.string.a1_4).trim(), getResources().getString(R.string.a1_5).trim()
                , getResources().getString(R.string.a1_6).trim(), getResources().getString(R.string.a1_7).trim(), getResources().getString(R.string.a1_8).trim()
                , getResources().getString(R.string.a1_9).trim(), getResources().getString(R.string.a1_10).trim()

                , getResources().getString(R.string.s1_1).trim(), getResources().getString(R.string.s1_2).trim(), getResources().getString(R.string.s1_3).trim()
                , getResources().getString(R.string.s1_4).trim(), getResources().getString(R.string.s1_5).trim(), getResources().getString(R.string.s1_6).trim()
                , getResources().getString(R.string.s1_7).trim(), getResources().getString(R.string.s1_8).trim(), getResources().getString(R.string.s1_9).trim()
                , getResources().getString(R.string.s1_10).trim()

                , getResources().getString(R.string.h1_1).trim(), getResources().getString(R.string.h1_2).trim(), getResources().getString(R.string.h1_3).trim()
                , getResources().getString(R.string.h1_4).trim(), getResources().getString(R.string.h1_5).trim(), getResources().getString(R.string.h1_6).trim()
                , getResources().getString(R.string.h1_7).trim(), getResources().getString(R.string.h1_8).trim(), getResources().getString(R.string.h1_9).trim()
                , getResources().getString(R.string.h1_10).trim());

        mission.setTime("1:00");
        mission.setTimeDeduction("0:30");


        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission);



    }

    private void CreateMissionTwo() {


        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt1)).getBitmap(), "picture1", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt2)).getBitmap(), "picture2", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt3)).getBitmap(), "picture3", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt4)).getBitmap(), "picture4", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt5)).getBitmap(), "picture5", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt6)).getBitmap(), "picture6", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt7)).getBitmap(), "picture7", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt8)).getBitmap(), "picture8", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt9)).getBitmap(), "picture9", "TalktoJame");
        saveToInternalStorage(((BitmapDrawable) getResources().getDrawable(R.drawable.mt10)).getBitmap(), "picture10", "TalktoJame");


        Mission mission2 = new Mission("TalktoJame", "Talk to KJame", 18, 10
                , directory_path + "Talktojame" + "/picture1.jpg", directory_path + "Talktojame" + "/picture2.jpg", directory_path + "Talktojame" + "/picture3.jpg", directory_path + "Talktojame" + "/picture4.jpg", directory_path + "Talktojame" + "/picture5.jpg", directory_path + "Talktojame" + "/picture6.jpg", directory_path + "Talktojame" + "/picture7.jpg", directory_path + "Talktojame" + "/picture8.jpg", directory_path + "Talktojame" + "/picture9.jpg", directory_path + "Talktojame" + "/picture10.jpg"
                , getResources().getString(R.string.q2_1), getResources().getString(R.string.q2_2), getResources().getString(R.string.q2_3), getResources().getString(R.string.q2_4), getResources().getString(R.string.q2_5)
                , getResources().getString(R.string.q2_6), getResources().getString(R.string.q2_7), getResources().getString(R.string.q2_8)
                , getResources().getString(R.string.q2_9), getResources().getString(R.string.q2_10)
                , getResources().getString(R.string.a2_1).trim(), getResources().getString(R.string.a2_2).trim(), getResources().getString(R.string.a2_3).trim()
                , getResources().getString(R.string.a2_4).trim(), getResources().getString(R.string.a2_5).trim()
                , getResources().getString(R.string.a2_6).trim(), getResources().getString(R.string.a2_7).trim(), getResources().getString(R.string.a2_8).trim()
                , getResources().getString(R.string.a2_9).trim(), getResources().getString(R.string.a2_10).trim()

                , getResources().getString(R.string.s2_1).trim(), getResources().getString(R.string.s2_2).trim(), getResources().getString(R.string.s2_3).trim()
                , getResources().getString(R.string.s2_4).trim(), getResources().getString(R.string.s2_5).trim(), getResources().getString(R.string.s2_6).trim()
                , getResources().getString(R.string.s2_7).trim(), getResources().getString(R.string.s2_8).trim(), getResources().getString(R.string.s2_9).trim()
                , getResources().getString(R.string.s2_10).trim()

                , getResources().getString(R.string.h2_1).trim(), getResources().getString(R.string.h2_2).trim(), getResources().getString(R.string.h2_3).trim()
                , getResources().getString(R.string.h2_4).trim(), getResources().getString(R.string.h2_5).trim(), getResources().getString(R.string.h2_6).trim()
                , getResources().getString(R.string.h2_7).trim(), getResources().getString(R.string.h2_8).trim(), getResources().getString(R.string.h2_9).trim()
                , getResources().getString(R.string.h2_10).trim());

        mission2.setTime("1:10");
        mission2.setTimeDeduction("0:35");

        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission2);



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

                , getResources().getString(R.string.s3_1).trim(), getResources().getString(R.string.s3_2).trim(), getResources().getString(R.string.s3_3).trim()
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


        Mission career = new Mission("Career", "Various career", 9, 10
                , directory_path + "Career" + "/picture1.jpg", directory_path + "Career" + "/picture2.jpg", directory_path + "Career" + "/picture3.jpg", directory_path + "Career" + "/picture4.jpg", directory_path + "Career" + "/picture5.jpg", directory_path + "Career" + "/picture6.jpg", directory_path + "Career" + "/picture7.jpg", directory_path + "Career" + "/picture8.jpg", directory_path + "Career" + "/picture9.jpg", directory_path + "Career" + "/picture10.jpg"
                , getResources().getString(R.string.q4_1), getResources().getString(R.string.q4_2), getResources().getString(R.string.q4_3), getResources().getString(R.string.q4_4), getResources().getString(R.string.q4_5)
                , getResources().getString(R.string.q4_6), getResources().getString(R.string.q4_7), getResources().getString(R.string.q4_8)
                , getResources().getString(R.string.q4_9), getResources().getString(R.string.q4_10)
                , getResources().getString(R.string.a4_1).trim(), getResources().getString(R.string.a4_2).trim(), getResources().getString(R.string.a4_3).trim()
                , getResources().getString(R.string.a4_4).trim(), getResources().getString(R.string.a4_5).trim()
                , getResources().getString(R.string.a4_6).trim(), getResources().getString(R.string.a4_7).trim(), getResources().getString(R.string.a4_8).trim()
                , getResources().getString(R.string.a4_9).trim(), getResources().getString(R.string.a4_10).trim()

                , getResources().getString(R.string.s4_1).trim(), getResources().getString(R.string.s4_2).trim(), getResources().getString(R.string.s4_3).trim()
                , getResources().getString(R.string.s4_4).trim(), getResources().getString(R.string.s4_5).trim(), getResources().getString(R.string.s4_6).trim()
                , getResources().getString(R.string.s4_7).trim(), getResources().getString(R.string.s4_8).trim(), getResources().getString(R.string.s4_9).trim()
                , getResources().getString(R.string.s4_10).trim()

                , getResources().getString(R.string.h4_1).trim(), getResources().getString(R.string.h4_2).trim(), getResources().getString(R.string.h4_3).trim()
                , getResources().getString(R.string.h4_4).trim(), getResources().getString(R.string.h4_5).trim(), getResources().getString(R.string.h4_6).trim()
                , getResources().getString(R.string.h4_7).trim(), getResources().getString(R.string.h4_8).trim(), getResources().getString(R.string.h4_9).trim()
                , getResources().getString(R.string.h4_10).trim());

        career.setTime("1:10");
        career.setTimeDeduction("0:35");
        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(career);



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

            if (box3) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startTimer();
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 3");


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
                        loadingDialog.setTitle("กำลังเตรียมแบบทดสอบที่ 4");


                    }
                });
                CreateMissionCareer();

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



