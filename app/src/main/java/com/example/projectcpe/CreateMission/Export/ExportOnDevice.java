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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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

public class ExportOnDevice extends AppCompatActivity implements  MissionAdapter.OnCustomerItemClick{

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

    public void loadData(){
        RecyclerView.Adapter adapter = new MissionAdapter(getMissionList(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Mission> getMissionList() {
        return MissionDATABASE.getInstance(this).missionDAO().getAllMission();
    }

    @Override
    public void onCustomerClick(final int id, int result, Mission mission) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Export Mission On Device");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to export this mission?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {





                missionList = MissionDATABASE.getInstance(ExportOnDevice.this).missionDAO().getAllinfoOfMission(id);


                progressDialog  = ProgressDialog.show(ExportOnDevice.this, "Fetch photos", "Loading...", true, false);

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







    private void SwitchAddListString(int number) {



        switch (number){
            case 5 :
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5" +
                        ".Score1.Score2.Score3.Score4.Score5" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5-");

                dataList.add(missionList.get(0).getMissionName()+"."+missionList.get(0).getDetailMission()+"."+missionList.get(0).getAge()+"."+missionList.get(0).getNumberofMission()
                        +"."+missionList.get(0).getP1() +"."+ missionList.get(0).getP2() +"."+ missionList.get(0).getP3() +"."+ missionList.get(0).getP4() +"."+ missionList.get(0).getP5()
                        +"."+missionList.get(0).getQ1()+"."+missionList.get(0).getQ2()+"."+missionList.get(0).getQ3()+"."+missionList.get(0).getQ4()+"."+missionList.get(0).getQ5()
                        +"."+missionList.get(0).getA1()+"."+missionList.get(0).getA2()+"."+missionList.get(0).getA3()+"."+missionList.get(0).getA4()+"."+missionList.get(0).getA5()
                        +"."+missionList.get(0).getS1()+"."+missionList.get(0).getS2()+"."+missionList.get(0).getS3()+"."+missionList.get(0).getS4()+"."+missionList.get(0).getS5()
                        +"."+missionList.get(0).getH1()+"."+missionList.get(0).getH2()+"."+missionList.get(0).getH3()+"."+missionList.get(0).getH4()+"."+missionList.get(0).getH5());

                createFileCsv();


                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture1.png"))),"picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture2.png"))),"picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture3.png"))),"picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture4.png"))),"picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture5.png"))),"picture5");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 6 :
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6-");

                dataList.add(missionList.get(0).getMissionName()+"."+missionList.get(0).getDetailMission()+"."+missionList.get(0).getAge()+"."+missionList.get(0).getNumberofMission()
                        +"."+ missionList.get(0).getP1() +"."+ missionList.get(0).getP2() +"."+ missionList.get(0).getP3() +"."+ missionList.get(0).getP4() +"."+ missionList.get(0).getP5()+"."+ missionList.get(0).getP6()
                        +"."+missionList.get(0).getQ1()+"."+missionList.get(0).getQ2()+"."+missionList.get(0).getQ3()+"."+missionList.get(0).getQ4()+"."+missionList.get(0).getQ5()+"."+missionList.get(0).getQ6()
                        +"."+missionList.get(0).getA1()+"."+missionList.get(0).getA2()+"."+missionList.get(0).getA3()+"."+missionList.get(0).getA4()+"."+missionList.get(0).getA5()+"."+missionList.get(0).getA6()
                        +"."+missionList.get(0).getS1()+"."+missionList.get(0).getS2()+"."+missionList.get(0).getS3()+"."+missionList.get(0).getS4()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS5()
                        +"."+missionList.get(0).getH1()+"."+missionList.get(0).getH2()+"."+missionList.get(0).getH3()+"."+missionList.get(0).getH4()+"."+missionList.get(0).getH5()+"."+missionList.get(0).getH6()+"-");

                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture1.png"))),"picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture2.png"))),"picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture3.png"))),"picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture4.png"))),"picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture5.png"))),"picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture6.png"))),"picture6");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 7 :
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7-");

                dataList.add(missionList.get(0).getMissionName()+"."+missionList.get(0).getDetailMission()+"."+missionList.get(0).getAge()+"."+missionList.get(0).getNumberofMission()
                        +"."+ missionList.get(0).getP1() +"."+ missionList.get(0).getP2() +"."+ missionList.get(0).getP3() +"."+ missionList.get(0).getP4() +"."+ missionList.get(0).getP5()+"."+ missionList.get(0).getP6()+"."+ missionList.get(0).getP7()
                        +"."+missionList.get(0).getQ1()+"."+missionList.get(0).getQ2()+"."+missionList.get(0).getQ3()+"."+missionList.get(0).getQ4()+"."+missionList.get(0).getQ5()+"."+missionList.get(0).getQ6()+"."+missionList.get(0).getQ7()
                        +"."+missionList.get(0).getA1()+"."+missionList.get(0).getA2()+"."+missionList.get(0).getA3()+"."+missionList.get(0).getA4()+"."+missionList.get(0).getA5()+"."+missionList.get(0).getA6()+"."+missionList.get(0).getA7()
                        +"."+missionList.get(0).getS1()+"."+missionList.get(0).getS2()+"."+missionList.get(0).getS3()+"."+missionList.get(0).getS4()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS7()
                        +"."+missionList.get(0).getH1()+"."+missionList.get(0).getH2()+"."+missionList.get(0).getH3()+"."+missionList.get(0).getH4()+"."+missionList.get(0).getH5()+"."+missionList.get(0).getH6()+"."+missionList.get(0).getH7()+"-");

                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture1.png"))),"picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture2.png"))),"picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture3.png"))),"picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture4.png"))),"picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture5.png"))),"picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture6.png"))),"picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture7.png"))),"picture7");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 8 :
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7.Question8" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7.Answer8" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7.Score8" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7.Hint8-");

                dataList.add(missionList.get(0).getMissionName()+"."+missionList.get(0).getDetailMission()+"."+missionList.get(0).getAge()+"."+missionList.get(0).getNumberofMission()
                        +"."+ missionList.get(0).getP1() +"."+ missionList.get(0).getP2() +"."+ missionList.get(0).getP3() +"."+ missionList.get(0).getP4() +"."+ missionList.get(0).getP5()+"."+ missionList.get(0).getP6()+"."+missionList.get(0).getP7()+"."+ missionList.get(0).getP8()
                        +"."+missionList.get(0).getQ1()+"."+missionList.get(0).getQ2()+"."+missionList.get(0).getQ3()+"."+missionList.get(0).getQ4()+"."+missionList.get(0).getQ5()+"."+missionList.get(0).getQ6()+"."+missionList.get(0).getQ7()+"."+missionList.get(0).getQ8()
                        +"."+missionList.get(0).getA1()+"."+missionList.get(0).getA2()+"."+missionList.get(0).getA3()+"."+missionList.get(0).getA4()+"."+missionList.get(0).getA5()+"."+missionList.get(0).getA6()+"."+missionList.get(0).getA7()+"."+missionList.get(0).getA8()
                        +"."+missionList.get(0).getS1()+"."+missionList.get(0).getS2()+"."+missionList.get(0).getS3()+"."+missionList.get(0).getS4()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS7()+"."+missionList.get(0).getS8()
                        +"."+missionList.get(0).getH1()+"."+missionList.get(0).getH2()+"."+missionList.get(0).getH3()+"."+missionList.get(0).getH4()+"."+missionList.get(0).getH5()+"."+missionList.get(0).getH6()+"."+missionList.get(0).getH7()+"."+missionList.get(0).getH8()+"-");

                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture1.png"))),"picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture2.png"))),"picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture3.png"))),"picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture4.png"))),"picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture5.png"))),"picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture6.png"))),"picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture7.png"))),"picture7");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture8.png"))),"picture8");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case 9 :
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7.Question8.Question9" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7.Answer8.Answer9" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7.Score8.Score9" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7.Hint8.Hint9-");

                dataList.add(missionList.get(0).getMissionName()+"."+missionList.get(0).getDetailMission()+"."+missionList.get(0).getAge()+"."+missionList.get(0).getNumberofMission()
                        +"."+ missionList.get(0).getP1() +"."+ missionList.get(0).getP2()+"."+ missionList.get(0).getP3() +"."+ missionList.get(0).getP4() +"."+ missionList.get(0).getP5()+"."+ missionList.get(0).getP6()+"."+ missionList.get(0).getP7()+"."+ missionList.get(0).getP8()+"."+ missionList.get(0).getP9()
                        +"."+missionList.get(0).getQ1()+"."+missionList.get(0).getQ2()+"."+missionList.get(0).getQ3()+"."+missionList.get(0).getQ4()+"."+missionList.get(0).getQ5()+"."+missionList.get(0).getQ6()+"."+missionList.get(0).getQ7()+"."+missionList.get(0).getQ8()+"."+missionList.get(0).getQ9()
                        +"."+missionList.get(0).getA1()+"."+missionList.get(0).getA2()+"."+missionList.get(0).getA3()+"."+missionList.get(0).getA4()+"."+missionList.get(0).getA5()+"."+missionList.get(0).getA6()+"."+missionList.get(0).getA7()+"."+missionList.get(0).getA8()+"."+missionList.get(0).getA9()
                        +"."+missionList.get(0).getS1()+"."+missionList.get(0).getS2()+"."+missionList.get(0).getS3()+"."+missionList.get(0).getS4()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS7()+"."+missionList.get(0).getS8()+"."+missionList.get(0).getS9()
                        +"."+missionList.get(0).getH1()+"."+missionList.get(0).getH2()+"."+missionList.get(0).getH3()+"."+missionList.get(0).getH4()+"."+missionList.get(0).getH5()+"."+missionList.get(0).getH6()+"."+missionList.get(0).getH7()+"."+missionList.get(0).getH8()+"."+missionList.get(0).getH9()+"-");

              createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture1.png"))),"picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture2.png"))),"picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture3.png"))),"picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture4.png"))),"picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture5.png"))),"picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture6.png"))),"picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture7.png"))),"picture7");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture8.png"))),"picture8");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture9.png"))),"picture9");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                break;

            case 10 :

                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7.Question8.Question9.Question10" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7.Answer8.Answer9.Answer10" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7.Score8.Score9.Score10" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7.Hint8.Hint9.Hint10-");

                dataList.add(missionList.get(0).getMissionName()+"."+missionList.get(0).getDetailMission()+"."+missionList.get(0).getAge()+"."+missionList.get(0).getNumberofMission()
                        +"."+missionList.get(0).getQ1()+"."+missionList.get(0).getQ2()+"."+missionList.get(0).getQ3()+"."+missionList.get(0).getQ4()+"."+missionList.get(0).getQ5()+"."+missionList.get(0).getQ6()+"."+missionList.get(0).getQ7()+"."+missionList.get(0).getQ8()+"."+missionList.get(0).getQ9()+"."+missionList.get(0).getQ10()
                        +"."+missionList.get(0).getA1()+"."+missionList.get(0).getA2()+"."+missionList.get(0).getA3()+"."+missionList.get(0).getA4()+"."+missionList.get(0).getA5()+"."+missionList.get(0).getA6()+"."+missionList.get(0).getA7()+"."+missionList.get(0).getA8()+"."+missionList.get(0).getA9()+"."+missionList.get(0).getA10()
                        +"."+missionList.get(0).getS1()+"."+missionList.get(0).getS2()+"."+missionList.get(0).getS3()+"."+missionList.get(0).getS4()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS5()+"."+missionList.get(0).getS7()+"."+missionList.get(0).getS8()+"."+missionList.get(0).getS9()+"."+missionList.get(0).getS10()
                        +"."+missionList.get(0).getH1()+"."+missionList.get(0).getH2()+"."+missionList.get(0).getH3()+"."+missionList.get(0).getH4()+"."+missionList.get(0).getH5()+"."+missionList.get(0).getH6()+"."+missionList.get(0).getH7()+"."+"10ง10"+"."+missionList.get(0).getH9()+"."+missionList.get(0).getH10()+"-");

                createFileCsv();

                try {
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture1.png"))),"picture1");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture2.png"))),"picture2");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture3.png"))),"picture3");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture4.png"))),"picture4");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture5.png"))),"picture5");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture6.png"))),"picture6");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture7.png"))),"picture7");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture8.png"))),"picture8");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture9.png"))),"picture9");
                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/MyMission/"+missionList.get(0).getMissionName(),"picture10.png"))),"picture10");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }



                break;
        }

        stopThread = true;

        MissionDATABASE.getInstance(ExportOnDevice.this).missionDAO().delete(missionList.get(0));


    }

    private String saveToInternalStorage(Bitmap bitmapImage,String foldername){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = new File(Environment.getExternalStorageDirectory() + "/MyMissionExport/"+missionList.get(0).getMissionName()+"/");
        // Create imageDir
        File mypath=new File(directory,foldername+".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

    private void createFileCsv(){


        EasyCsv easyCsv = new EasyCsv(ExportOnDevice.this);

        easyCsv.setSeparatorColumn(".");
        easyCsv.setSeperatorLine("-");

        /// create folder
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/MyMissionExport/" +missionList.get(0).getMissionName();
        File file = new File(directory_path);
        if (!file.exists())
        {
            file.mkdirs();
        }

        String fileName="Data";

        easyCsv.createCsvFile(missionList.get(0).getMissionName(),missionList.get(0).getMissionName()+fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {


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


            SwitchAddListString(missionList.get(0).getNumberofMission());

            if (stopThread) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                        progressDialog.dismiss();
                        recreate();



                    }
                });
            }


        }



    }
}
