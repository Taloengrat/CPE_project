package com.example.projectcpe.CreateMission.Import;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.TimeAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.CreateMission.Export.ExportOnGoogleDrive;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Csv;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ImportOnGoogledrive extends AppCompatActivity implements MissionAdapter.OnCustomerItemClick{


    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    TextView Nameee;
    ImageView imageUser;
    RecyclerView recyclerView;
    Mission missionClone;
    ProgressDialog progressDialog;
    StorageReference storageRef;
    private String[] nextLine;
    private volatile boolean stopThread = false;
    ArrayList<Mission> missionArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_googledrive);

        Nameee = findViewById(R.id.Nameeee);
        imageUser = findViewById(R.id.imageUser);
        recyclerView = findViewById(R.id.recyclerView);

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        Nameee.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


//        forestRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        listAllFiles();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void listAllFiles() {
        progressDialog = ProgressDialog.show(ImportOnGoogledrive.this, "search all file in cloud", "searching...", true, false);

        // [START storage_list_all]

        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                            Mission mission = new Mission();

                            mission.setUniqueKey(childDataSnapshot.getKey());
                            mission.setMissionName(String.valueOf(childDataSnapshot.child("MissionName").getValue()));
                            mission.setAge(Integer.valueOf(String.valueOf(childDataSnapshot.child("AGE").getValue())));
                            mission.setNumberofMission(Integer.valueOf(String.valueOf(childDataSnapshot.child("NumberQuiz").getValue())));

                            missionArrayList.add(mission);

                        }


                        loadData();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


    }


    @Override
    public void onCustomerClick(int pos, int result, Mission missionList) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Import Mission On Could");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to import this mission?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                missionClone = missionList;


                progressDialog = ProgressDialog.show(ImportOnGoogledrive.this, "Import on Could ", "importing...", true, false);


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

    public void startThread() {
        stopThread = false;

        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();

    }
//
//    @Override
//    public void onCustomerLongClick(int pos, Mission missionList) {
//        Toast.makeText(getApplicationContext(), "OnLongClick", Toast.LENGTH_LONG).show();
//    }


    class ExampleRunnable implements Runnable {


        ExampleRunnable() {

        }

        @Override
        public void run() {

            if (MissionDATABASE.getInstance(ImportOnGoogledrive.this).missionDAO().CheckNameMission(missionClone.getMissionName()) == null){
                for (int i = 0; i < missionClone.getNumberofMission(); i++) {

                    downloadFile(i + 1);
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ImportOnGoogledrive.this, "มีแบบทดสอบนี้อยู่แล้ว",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

            }




        }


    }

    public void loadData() {

        RecyclerView.Adapter adapter = new MissionAdapter(missionArrayList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressDialog.dismiss();
    }


    //long ONE_MEGABYTE = 1024 * 1024;


    @Override
    public void onBackPressed() {
        // your code.

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout Accout");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to logout google?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                signOut();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        new ButtonServiceEffect(ImportOnGoogledrive.this).startEffect(); // Sound button effect
        dialog.show();
        // your code.
    }


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "ออกจากระบบแล้ว", Toast.LENGTH_LONG).show();


                    }
                });
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

        stopService(new Intent(ImportOnGoogledrive.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(ImportOnGoogledrive.this, MusicService.class));
        startService(new Intent(ImportOnGoogledrive.this, MusicService.class));
    }

    private void    downloadFile(int i) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + missionClone.getUniqueKey());
        StorageReference islandRef = storageRef.child("picture" + i + ".jpg");

        StorageReference RefCSV = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + missionClone.getUniqueKey())
                .child(missionClone.getMissionName() + "Data.csv");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "EnglishPractice/" + missionClone.getMissionName());
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, "picture" + i + ".jpg");
        final File localFileCSV = new File(rootPath, missionClone.getMissionName() + "Data.csv");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);

                if (i == missionClone.getNumberofMission()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            RefCSV.getFile(localFileCSV).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                    startThreadCSVimportData();

                                }

                                private void startThreadCSVimportData() {
                                    ExampleRunnableCSV runnable = new ExampleRunnableCSV();
                                    new Thread(runnable).start();
                                }

                                class ExampleRunnableCSV implements Runnable {


                                    ExampleRunnableCSV() {

                                    }

                                    @Override
                                    public void run() {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.setTitle("จัดเรียงข้อมูล File.csv");
                                                progressDialog.setMessage("กำลังจัดเรียงข้อมูล ...");
                                            }
                                        });

                                        String path = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/" + missionClone.getMissionName() + "/" + missionClone.getMissionName() + "Data.csv";


                                        try {
                                            File csvfile = new File(path);
                                            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));

                                            while ((nextLine = reader.readNext()) != null) {
                                                // nextLine[] is an array of values from the line

                                                if (reader.getLinesRead() == 2) {
                                                    Log.v("Lineeeeeeeee", String.valueOf(reader.getLinesRead()));

                                                    SwitchImport(Integer.valueOf(nextLine[3]));
                                                }


                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }


                                        if (stopThread) {


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {



                                                    progressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), "นำเข้าแบบทดสอบเสร็จเรียบร้อย", Toast.LENGTH_LONG).show();
//


                                                }
                                            });
                                        }


                                    }

                                    private void SwitchImport(int numberstep) throws FileNotFoundException {

                                        String createFolder = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/" + missionClone.getMissionName();
                                        File file = new File(createFolder);
                                        if (!file.exists()) {
                                            file.mkdirs();
                                        }
                                        Mission mission = new Mission();
                                        mission.setMissionName(nextLine[0]);
                                        mission.setDetailMission(nextLine[1]);
                                        mission.setAge(Integer.valueOf(nextLine[2]));
                                        mission.setNumberofMission(Integer.valueOf(nextLine[3]));

                                        switch (numberstep) {

                                            case 5:


                                                mission.setQ1(nextLine[4]);
                                                mission.setQ2(nextLine[5]);
                                                mission.setQ3(nextLine[6]);
                                                mission.setQ4(nextLine[7]);
                                                mission.setQ5(nextLine[8]);


                                                mission.setA1(nextLine[9]);
                                                mission.setA2(nextLine[10]);
                                                mission.setA3(nextLine[11]);
                                                mission.setA4(nextLine[12]);
                                                mission.setA5(nextLine[13]);

                                                mission.setS1(nextLine[14]);
                                                mission.setS2(nextLine[15]);
                                                mission.setS3(nextLine[16]);
                                                mission.setS4(nextLine[17]);
                                                mission.setS5(nextLine[18]);

                                                mission.setH1(nextLine[19]);
                                                mission.setH2(nextLine[20]);
                                                mission.setH3(nextLine[21]);
                                                mission.setH4(nextLine[22]);
                                                mission.setH5(nextLine[23]);


                                                mission.setTime(nextLine[24]);
                                                mission.setTimeDeduction(nextLine[25]);


                                                break;
                                            case 6:


                                                ////////


                                                mission.setQ1(nextLine[4]);
                                                mission.setQ2(nextLine[5]);
                                                mission.setQ3(nextLine[6]);
                                                mission.setQ4(nextLine[7]);
                                                mission.setQ5(nextLine[8]);
                                                mission.setQ6(nextLine[9]);


                                                mission.setA1(nextLine[10]);
                                                mission.setA2(nextLine[11]);
                                                mission.setA3(nextLine[12]);
                                                mission.setA4(nextLine[13]);
                                                mission.setA5(nextLine[14]);
                                                mission.setA6(nextLine[15]);


                                                mission.setS1(nextLine[16]);
                                                mission.setS2(nextLine[17]);
                                                mission.setS3(nextLine[18]);
                                                mission.setS4(nextLine[19]);
                                                mission.setS5(nextLine[20]);
                                                mission.setS6(nextLine[21]);


                                                mission.setH1(nextLine[22]);
                                                mission.setH2(nextLine[23]);
                                                mission.setH3(nextLine[24]);
                                                mission.setH4(nextLine[25]);
                                                mission.setH5(nextLine[26]);
                                                mission.setH6(nextLine[27]);

                                                mission.setTime(nextLine[28]);
                                                mission.setTimeDeduction(nextLine[29]);

                                                break;
                                            case 7:


                                                mission.setQ1(nextLine[4]);
                                                mission.setQ2(nextLine[5]);
                                                mission.setQ3(nextLine[6]);
                                                mission.setQ4(nextLine[7]);
                                                mission.setQ5(nextLine[8]);
                                                mission.setQ6(nextLine[9]);
                                                mission.setQ7(nextLine[10]);


                                                mission.setA1(nextLine[11]);
                                                mission.setA2(nextLine[12]);
                                                mission.setA3(nextLine[13]);
                                                mission.setA4(nextLine[14]);
                                                mission.setA5(nextLine[15]);
                                                mission.setA6(nextLine[16]);
                                                mission.setA7(nextLine[17]);


                                                mission.setS1(nextLine[18]);
                                                mission.setS2(nextLine[19]);
                                                mission.setS3(nextLine[20]);
                                                mission.setS4(nextLine[21]);
                                                mission.setS5(nextLine[22]);
                                                mission.setS6(nextLine[23]);
                                                mission.setS7(nextLine[24]);


                                                mission.setH1(nextLine[25]);
                                                mission.setH2(nextLine[26]);
                                                mission.setH3(nextLine[27]);
                                                mission.setH4(nextLine[28]);
                                                mission.setH5(nextLine[29]);
                                                mission.setH6(nextLine[30]);
                                                mission.setH7(nextLine[31]);

                                                mission.setTime(nextLine[32]);
                                                mission.setTimeDeduction(nextLine[33]);

                                                break;
                                            case 8:


                                                mission.setQ1(nextLine[4]);
                                                mission.setQ2(nextLine[5]);
                                                mission.setQ3(nextLine[6]);
                                                mission.setQ4(nextLine[7]);
                                                mission.setQ5(nextLine[8]);
                                                mission.setQ6(nextLine[9]);
                                                mission.setQ7(nextLine[10]);
                                                mission.setQ8(nextLine[11]);


                                                mission.setA1(nextLine[12]);
                                                mission.setA2(nextLine[13]);
                                                mission.setA3(nextLine[14]);
                                                mission.setA4(nextLine[15]);
                                                mission.setA5(nextLine[16]);
                                                mission.setA6(nextLine[17]);
                                                mission.setA7(nextLine[18]);
                                                mission.setA8(nextLine[19]);


                                                mission.setS1(nextLine[20]);
                                                mission.setS2(nextLine[21]);
                                                mission.setS3(nextLine[22]);
                                                mission.setS4(nextLine[23]);
                                                mission.setS5(nextLine[24]);
                                                mission.setS6(nextLine[25]);
                                                mission.setS7(nextLine[26]);
                                                mission.setS8(nextLine[27]);


                                                mission.setH1(nextLine[28]);
                                                mission.setH2(nextLine[29]);
                                                mission.setH3(nextLine[30]);
                                                mission.setH4(nextLine[31]);
                                                mission.setH5(nextLine[32]);
                                                mission.setH6(nextLine[33]);
                                                mission.setH7(nextLine[34]);
                                                mission.setS8(nextLine[35]);

                                                mission.setTime(nextLine[36]);
                                                mission.setTimeDeduction(nextLine[37]);
                                                break;
                                            case 9:


                                                mission.setQ1(nextLine[4]);
                                                mission.setQ2(nextLine[5]);
                                                mission.setQ3(nextLine[6]);
                                                mission.setQ4(nextLine[7]);
                                                mission.setQ5(nextLine[8]);
                                                mission.setQ6(nextLine[9]);
                                                mission.setQ7(nextLine[10]);
                                                mission.setQ8(nextLine[11]);
                                                mission.setQ9(nextLine[12]);


                                                mission.setA1(nextLine[13]);
                                                mission.setA2(nextLine[14]);
                                                mission.setA3(nextLine[15]);
                                                mission.setA4(nextLine[16]);
                                                mission.setA5(nextLine[17]);
                                                mission.setA6(nextLine[18]);
                                                mission.setA7(nextLine[19]);
                                                mission.setA8(nextLine[20]);
                                                mission.setA9(nextLine[21]);


                                                mission.setS1(nextLine[22]);
                                                mission.setS2(nextLine[23]);
                                                mission.setS3(nextLine[24]);
                                                mission.setS4(nextLine[25]);
                                                mission.setS5(nextLine[26]);
                                                mission.setS6(nextLine[27]);
                                                mission.setS7(nextLine[28]);
                                                mission.setS8(nextLine[29]);
                                                mission.setS9(nextLine[30]);


                                                mission.setH1(nextLine[31]);
                                                mission.setH2(nextLine[32]);
                                                mission.setH3(nextLine[33]);
                                                mission.setH4(nextLine[34]);
                                                mission.setH5(nextLine[35]);
                                                mission.setH6(nextLine[36]);
                                                mission.setH7(nextLine[37]);
                                                mission.setS8(nextLine[38]);
                                                mission.setH9(nextLine[39]);

                                                mission.setTime(nextLine[40]);
                                                mission.setTimeDeduction(nextLine[41]);
                                                break;
                                            case 10:



                                                mission.setQ1(nextLine[4]);
                                                mission.setQ2(nextLine[5]);
                                                mission.setQ3(nextLine[6]);
                                                mission.setQ4(nextLine[7]);
                                                mission.setQ5(nextLine[8]);
                                                mission.setQ6(nextLine[9]);
                                                mission.setQ7(nextLine[10]);
                                                mission.setQ8(nextLine[11]);
                                                mission.setQ9(nextLine[12]);
                                                mission.setQ10(nextLine[13]);


                                                mission.setA1(nextLine[14]);
                                                mission.setA2(nextLine[15]);
                                                mission.setA3(nextLine[16]);
                                                mission.setA4(nextLine[17]);
                                                mission.setA5(nextLine[18]);
                                                mission.setA6(nextLine[19]);
                                                mission.setA7(nextLine[20]);
                                                mission.setA8(nextLine[21]);
                                                mission.setA9(nextLine[22]);
                                                mission.setA10(nextLine[23]);

                                                mission.setS1(nextLine[24]);
                                                mission.setS2(nextLine[25]);
                                                mission.setS3(nextLine[26]);
                                                mission.setS4(nextLine[27]);
                                                mission.setS5(nextLine[28]);
                                                mission.setS6(nextLine[29]);
                                                mission.setS7(nextLine[30]);
                                                mission.setS8(nextLine[31]);
                                                mission.setS9(nextLine[32]);
                                                mission.setS10(nextLine[33]);

                                                mission.setH1(nextLine[34]);
                                                mission.setH2(nextLine[35]);
                                                mission.setH3(nextLine[36]);
                                                mission.setH4(nextLine[37]);
                                                mission.setH5(nextLine[38]);
                                                mission.setH6(nextLine[39]);
                                                mission.setH7(nextLine[40]);
                                                mission.setH8(nextLine[41]);
                                                mission.setH9(nextLine[42]);
                                                mission.setH10(nextLine[43]);

                                                mission.setTime(nextLine[44]);
                                                mission.setTimeDeduction(nextLine[45]);

                                                break;
                                        }

                                        MissionDATABASE.getInstance(ImportOnGoogledrive.this).missionDAO().create(mission);
                                        stopThread = true;
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
            }

        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull FileDownloadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                progressDialog.setTitle("นำเข้าแบบทดสอบ " + "(" + i + "/" + missionClone.getNumberofMission() + ")");
                progressDialog.setMessage("ความคืบหน้า : " + progress + "%");
            }
        });
    }

    private void downloadInLocalFile(int i) throws IOException {


        storageRef = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + "testttt");

        File dir = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionClone.getMissionName());
        final File file = new File(dir, "picture" + (i + 1) + ".jpg");
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final FileDownloadTask fileDownloadTask = storageRef.getFile(file);


        fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "successs", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                progressDialog.setTitle("นำเข้าแบบทดสอบ");
                progressDialog.setMessage("ความคืบหน้า : " + progress + "%");
            }
        });
    }



}
