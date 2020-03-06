package com.example.projectcpe.CreateMission.Export;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projectcpe.ButtonServiceEffect;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.CSV.Utility.EasyCsv;
import com.example.projectcpe.CSV.Utility.FileCallback;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.android.gms.drive.Drive.getDriveResourceClient;

public class ExportOnGoogleDrive extends AppCompatActivity implements MissionAdapter.OnCustomerItemClick {

    Drive googleDriveService;

    RecyclerView recyclerView;
    List<Mission> missionList;
    Mission missionClone;
    ProgressDialog progressDialog;
    TextView txName;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean stopThread = false;
    private volatile boolean stopThreadFirebase = false;
    GoogleSignInClient googleSignInClient;
    List<String> headerList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;
    private String folderId;


    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ImageView imageUser;

    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_on_google_drive);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();


//        imageUser.setImageURI(currentUser.getPhotoUrl());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        txName = findViewById(R.id.Nameeee);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        requestSignIn();

        recyclerView = findViewById(R.id.recyclerView);

        loadData();

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("username");
        String Uid = bundle.getString("Uid");

//        Glide.with(getApplicationContext()).load(currentUser.getPhotoUrl().toString())
//                .thumbnail(0.5f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imageUser);


        txName.setText(name);
        Toast.makeText(getApplicationContext(), Uid, Toast.LENGTH_LONG).show();


    }

    public void loadData() {
        RecyclerView.Adapter adapter = new MissionAdapter(getMissionList(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Mission> getMissionList() {
        return MissionDATABASE.getInstance(this).missionDAO().getAllMission();
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

        stopService(new Intent(ExportOnGoogleDrive.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(ExportOnGoogleDrive.this, MusicService.class));
        startService(new Intent(ExportOnGoogleDrive.this, MusicService.class));
    }

    @Override
    public void onCustomerClick(int id, int result, Mission mission) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Export Mission On Google drive");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to export this mission?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                missionClone = mission;


                progressDialog = ProgressDialog.show(ExportOnGoogleDrive.this, "Export to google drive ", "exporting...", true, false);


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

                dataList.add(missionClone.getMissionName() + "," + missionClone.getDetailMission() + "," + missionClone.getAge() + "," + missionClone.getNumberofMission()
                        + "," + missionClone.getQ1() + "," + missionClone.getQ2() + "," + missionClone.getQ3() + "," + missionClone.getQ4() + "," + missionClone.getQ5()
                        + "," + missionClone.getA1() + "," + missionClone.getA2() + "," + missionClone.getA3() + "," + missionClone.getA4() + "," + missionClone.getA5()
                        + "," + missionClone.getS1() + "," + missionClone.getS2() + "," + missionClone.getS3() + "," + missionClone.getS4() + "," + missionClone.getS5()
                        + "," + missionClone.getH1() + "," + missionClone.getH2() + "," + missionClone.getH3() + "," + missionClone.getH4() + "," + missionClone.getH5()
                        + "," + missionClone.getTime() + "," + missionClone.getTimeDeduction() + "-");
                createFileCsv();

                break;
            case 6:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Time,TimeDeduction-");

                dataList.add(missionClone.getMissionName() + "," + missionClone.getDetailMission() + "," + missionClone.getAge() + "," + missionClone.getNumberofMission()
                        + "," + missionClone.getQ1() + "," + missionClone.getQ2() + "," + missionClone.getQ3() + "," + missionClone.getQ4() + "," + missionClone.getQ5() + "," + missionClone.getQ6()
                        + "," + missionClone.getA1() + "," + missionClone.getA2() + "," + missionClone.getA3() + "," + missionClone.getA4() + "," + missionClone.getA5() + "," + missionClone.getA6()
                        + "," + missionClone.getS1() + "," + missionClone.getS2() + "," + missionClone.getS3() + "," + missionClone.getS4() + "," + missionClone.getS5() + "," + missionClone.getS6()
                        + "," + missionClone.getH1() + "," + missionClone.getH2() + "," + missionClone.getH3() + "," + missionClone.getH4() + "," + missionClone.getH5() + "," + missionClone.getH6()
                        + "," + missionClone.getTime() + "," + missionClone.getTimeDeduction() + "-");


                createFileCsv();


                break;
            case 7:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Time,TimeDeduction-");

                dataList.add(missionClone.getMissionName() + "," + missionClone.getDetailMission() + "," + missionClone.getAge() + "," + missionClone.getNumberofMission()
                        + "," + missionClone.getQ1() + "," + missionClone.getQ2() + "," + missionClone.getQ3() + "," + missionClone.getQ4() + "," + missionClone.getQ5() + "," + missionClone.getQ6() + "," + missionClone.getQ7()
                        + "," + missionClone.getA1() + "," + missionClone.getA2() + "," + missionClone.getA3() + "," + missionClone.getA4() + "," + missionClone.getA5() + "," + missionClone.getA6() + "," + missionClone.getA7()
                        + "," + missionClone.getS1() + "," + missionClone.getS2() + "," + missionClone.getS3() + "," + missionClone.getS4() + "," + missionClone.getS5() + "," + missionClone.getS6() + "," + missionClone.getS7()
                        + "," + missionClone.getH1() + "," + missionClone.getH2() + "," + missionClone.getH3() + "," + missionClone.getH4() + "," + missionClone.getH5() + "," + missionClone.getH6() + "," + missionClone.getH7()
                        + "," + missionClone.getTime() + "," + missionClone.getTimeDeduction() + "-");


                createFileCsv();

                break;
            case 8:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Time,TimeDeduction-");

                dataList.add(missionClone.getMissionName() + "," + missionClone.getDetailMission() + "," + missionClone.getAge() + "," + missionClone.getNumberofMission()
                        + "," + missionClone.getQ1() + "," + missionClone.getQ2() + "," + missionClone.getQ3() + "," + missionClone.getQ4() + "," + missionClone.getQ5() + "," + missionClone.getQ6() + "," + missionClone.getQ7() + "," + missionClone.getQ8()
                        + "," + missionClone.getA1() + "," + missionClone.getA2() + "," + missionClone.getA3() + "," + missionClone.getA4() + "," + missionClone.getA5() + "," + missionClone.getA6() + "," + missionClone.getA7() + "," + missionClone.getA8()
                        + "," + missionClone.getS1() + "," + missionClone.getS2() + "," + missionClone.getS3() + "," + missionClone.getS4() + "," + missionClone.getS5() + "," + missionClone.getS6() + "," + missionClone.getS7() + "," + missionClone.getS8()
                        + "," + missionClone.getH1() + "," + missionClone.getH2() + "," + missionClone.getH3() + "," + missionClone.getH4() + "," + missionClone.getH5() + "," + missionClone.getH6() + "," + missionClone.getH7() + "," + missionClone.getH8()
                        + "," + missionClone.getTime() + "," + missionClone.getTimeDeduction() + "-");


                createFileCsv();

                break;

            case 9:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8,Question9" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8,Answer9" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8,Score9" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Hint9,Time,TimeDeduction-");

                dataList.add(missionClone.getMissionName() + "," + missionClone.getDetailMission() + "," + missionClone.getAge() + "," + missionClone.getNumberofMission()
                        + "," + missionClone.getQ1() + "," + missionClone.getQ2() + "," + missionClone.getQ3() + "," + missionClone.getQ4() + "," + missionClone.getQ5() + "," + missionClone.getQ6() + "," + missionClone.getQ7() + "," + missionClone.getQ8() + "," + missionClone.getQ9()
                        + "," + missionClone.getA1() + "," + missionClone.getA2() + "," + missionClone.getA3() + "," + missionClone.getA4() + "," + missionClone.getA5() + "," + missionClone.getA6() + "," + missionClone.getA7() + "," + missionClone.getA8() + "," + missionClone.getA9()
                        + "," + missionClone.getS1() + "," + missionClone.getS2() + "," + missionClone.getS3() + "," + missionClone.getS4() + "," + missionClone.getS5() + "," + missionClone.getS6() + "," + missionClone.getS7() + "," + missionClone.getS8() + "," + missionClone.getS9()
                        + "," + missionClone.getH1() + "," + missionClone.getH2() + "," + missionClone.getH3() + "," + missionClone.getH4() + "," + missionClone.getH5() + "," + missionClone.getH6() + "," + missionClone.getH7() + "," + missionClone.getH8() + "," + missionClone.getH9()
                        + "," + missionClone.getTime() + "," + missionClone.getTimeDeduction() + "-");


                createFileCsv();

                break;

            case 10:

                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8,Question9,Question10" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8,Answer9,Answer10" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8,Score9,Score10" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Hint9,Hint10,Time,TimeDeduction-");

                dataList.add(missionClone.getMissionName() + "," + missionClone.getDetailMission() + "," + missionClone.getAge() + "," + missionClone.getNumberofMission()
                        + "," + missionClone.getQ1() + "," + missionClone.getQ2() + "," + missionClone.getQ3() + "," + missionClone.getQ4() + "," + missionClone.getQ5() + "," + missionClone.getQ6() + "," + missionClone.getQ7() + "," + missionClone.getQ8() + "," + missionClone.getQ9() + "," + missionClone.getQ10()
                        + "," + missionClone.getA1() + "," + missionClone.getA2() + "," + missionClone.getA3() + "," + missionClone.getA4() + "," + missionClone.getA5() + "," + missionClone.getA6() + "," + missionClone.getA7() + "," + missionClone.getA8() + "," + missionClone.getA9() + "," + missionClone.getA10()
                        + "," + missionClone.getS1() + "," + missionClone.getS2() + "," + missionClone.getS3() + "," + missionClone.getS4() + "," + missionClone.getS5() + "," + missionClone.getS6() + "," + missionClone.getS7() + "," + missionClone.getS8() + "," + missionClone.getS9() + "," + missionClone.getS10()
                        + "," + missionClone.getH1() + "," + missionClone.getH2() + "," + missionClone.getH3() + "," + missionClone.getH4() + "," + missionClone.getH5() + "," + missionClone.getH6() + "," + missionClone.getH7() + "," + missionClone.getH8() + "," + missionClone.getH9() + "," + missionClone.getH10()
                        + "," + missionClone.getTime() + "," + missionClone.getTimeDeduction() + "-");

                createFileCsv();

                break;
        }


        MissionDATABASE.getInstance(ExportOnGoogleDrive.this).missionDAO().delete(missionClone);


    }

    private void createFileCsv() throws IOException {


        EasyCsv easyCsv = new EasyCsv(ExportOnGoogleDrive.this);

        easyCsv.setSeparatorColumn(",");
        easyCsv.setSeperatorLine("-");

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/MyMissionExport/" + missionClone.getMissionName();
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String fileName = "Data";

        easyCsv.createCsvFile(missionClone.getMissionName().trim(), missionClone.getMissionName() + fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {


            @Override
            public void onSuccess(File file) throws FileNotFoundException {

                stopThread = true;

            }

            @Override
            public void onFail(String err) {

                Log.v("fail", err);

            }
        });


    }


    public void startThread() {
        stopThread = false;
        stopThreadFirebase = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();

    }

    class ExampleRunnable implements Runnable {


        ExampleRunnable() {

        }

        @Override
        public void run() {

            // Generate a reference to a new location and add some data using push()
            DatabaseReference pushedPostRef = FirebaseDatabase.getInstance().getReference().push();

// Get the unique ID generated by a push()
            String postId = pushedPostRef.getKey();

            try {
                SwitchAddListString(missionClone.getNumberofMission());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (stopThread) {
                    for (int i = 0; i < missionClone.getNumberofMission(); i++) {

                        int finalI1 = i;
                        FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + postId + "/" + "picture" + (i + 1) +
                                ".jpg")
                                .putFile(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/"
                                        + missionClone.getMissionName(), "picture" + (i + 1) + ".jpg")))
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" +
                                                postId).child("picture" + (finalI1 + 1)).setValue("picture" + (finalI1 + 1) + ".jpg");


                                        if (finalI1 == (missionClone.getNumberofMission() - 1)) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {


                                                    FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" +
                                                            postId).child("MissionName").setValue(missionClone.getMissionName());


                                                    FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" +
                                                            postId).child("CSVfile").setValue(missionClone.getMissionName() + "Data.csv");


                                                    FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" +
                                                            postId).child("AGE").setValue(missionClone.getAge());

                                                    FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" +
                                                            postId).child("NumberQuiz").setValue(missionClone.getNumberofMission());


                                                    FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + postId + "/" + missionClone.getMissionName() + "Data" +
                                                            ".csv")
                                                            .putFile(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/MyMissionExport/"
                                                                    + missionClone.getMissionName() + "/" + missionClone.getMissionName() + "Data.csv")))
                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                    Toast.makeText(getApplicationContext(), "นำออกแบบทดสอบเสร็จเรียบร้อย", Toast.LENGTH_LONG).show();

                                                                    File dir = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionClone.getMissionName());
                                                                    File dirCSV = new File(Environment.getExternalStorageDirectory() + "/MyMissionExport/" + missionClone.getMissionName());

                                                                    if (dir.isDirectory()) {
                                                                        String[] children = dir.list();
                                                                        for (int i = 0; i < children.length; i++) {
                                                                            new File(dir, children[i]).delete();
                                                                        }

                                                                        dir.delete();
                                                                    }

                                                                    if (dirCSV.isDirectory()) {
                                                                        String[] children = dirCSV.list();
                                                                        for (int k = 0; k < children.length; k++) {
                                                                            new File(dirCSV, children[k]).delete();
                                                                        }

                                                                        dirCSV.delete();
                                                                    }

                                                                    progressDialog.dismiss();
                                                                    recreate();

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            });
                                        }


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                                progressDialog.setTitle("นำออกแบบทดสอบ " + "(" + finalI1 + "/" + missionClone.getNumberofMission() + ")");
                                progressDialog.setMessage("ความคืบหน้า : " + progress + "%");


                            }
                        });


                    }
                }
            }


        }


    }


    @Override
    public void onBackPressed() {
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
        new ButtonServiceEffect(ExportOnGoogleDrive.this).startEffect(); // Sound button effect
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
}
