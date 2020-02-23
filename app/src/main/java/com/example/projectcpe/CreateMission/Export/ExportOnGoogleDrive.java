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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    GoogleSignInClient googleSignInClient;
    List<String> headerList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;
    private String folderId;


    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    ImageView imageUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_on_google_drive);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

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


        if (CheckInternet()) {
            requestSignIn();
            Log.v("dddddddddddddddd", "true");
        } else {
            Log.v("dddddddddddddddd", "fail");
        }

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
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Time-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5()
                        + "," + missionList.get(0).getTime() + "-");
                createFileCsv();

                break;
            case 6:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Time-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6()
                        + "," + missionList.get(0).getTime() + "-");


                createFileCsv();


                break;
            case 7:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Time-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7()
                        + "," + missionList.get(0).getTime() + "-");


                createFileCsv();

                break;
            case 8:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Time-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7() + "," + missionList.get(0).getQ8()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7() + "," + missionList.get(0).getA8()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7() + "," + missionList.get(0).getS8()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7() + "," + missionList.get(0).getH8()
                        + "," + missionList.get(0).getTime() + "-");


                createFileCsv();

                break;

            case 9:
                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8,Question9" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8,Answer9" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8,Score9" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Hint9,Time-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7() + "," + missionList.get(0).getQ8() + "," + missionList.get(0).getQ9()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7() + "," + missionList.get(0).getA8() + "," + missionList.get(0).getA9()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7() + "," + missionList.get(0).getS8() + "," + missionList.get(0).getS9()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7() + "," + missionList.get(0).getH8() + "," + missionList.get(0).getH9()
                        + "," + missionList.get(0).getTime() + "-");


                createFileCsv();

                break;

            case 10:

                headerList.add("MissionName,DetailMission,ForAge,NumberOfMission" +
                        ",Question1,Question2,Question3,Question4,Question5,Question6,Question7,Question8,Question9,Question10" +
                        ",Answer1,Answer2,Answer3,Answer4,Answer5,Answer6,Answer7,Answer8,Answer9,Answer10" +
                        ",Score1,Score2,Score3,Score4,Score5,Score6,Score7,Score8,Score9,Score10" +
                        ",Hint1,Hint2,Hint3,Hint4,Hint5,Hint6,Hint7,Hint8,Hint9,Hint10,Time-");

                dataList.add(missionList.get(0).getMissionName() + "," + missionList.get(0).getDetailMission() + "," + missionList.get(0).getAge() + "," + missionList.get(0).getNumberofMission()
                        + "," + missionList.get(0).getQ1() + "," + missionList.get(0).getQ2() + "," + missionList.get(0).getQ3() + "," + missionList.get(0).getQ4() + "," + missionList.get(0).getQ5() + "," + missionList.get(0).getQ6() + "," + missionList.get(0).getQ7() + "," + missionList.get(0).getQ8() + "," + missionList.get(0).getQ9() + "," + missionList.get(0).getQ10()
                        + "," + missionList.get(0).getA1() + "," + missionList.get(0).getA2() + "," + missionList.get(0).getA3() + "," + missionList.get(0).getA4() + "," + missionList.get(0).getA5() + "," + missionList.get(0).getA6() + "," + missionList.get(0).getA7() + "," + missionList.get(0).getA8() + "," + missionList.get(0).getA9() + "," + missionList.get(0).getA10()
                        + "," + missionList.get(0).getS1() + "," + missionList.get(0).getS2() + "," + missionList.get(0).getS3() + "," + missionList.get(0).getS4() + "," + missionList.get(0).getS5() + "," + missionList.get(0).getS6() + "," + missionList.get(0).getS7() + "," + missionList.get(0).getS8() + "," + missionList.get(0).getS9() + "," + missionList.get(0).getS10()
                        + "," + missionList.get(0).getH1() + "," + missionList.get(0).getH2() + "," + missionList.get(0).getH3() + "," + missionList.get(0).getH4() + "," + missionList.get(0).getH5() + "," + missionList.get(0).getH6() + "," + missionList.get(0).getH7() + "," + missionList.get(0).getH8() + "," + missionList.get(0).getH9() + "," + missionList.get(0).getH10()
                        + "," + missionList.get(0).getTime() + "-");

                createFileCsv();

                break;
        }

        File dir = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName());

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }

            dir.delete();
        }


        MissionDATABASE.getInstance(ExportOnGoogleDrive.this).missionDAO().delete(missionList.get(0));


    }

    private void createFileCsv() throws IOException {


        EasyCsv easyCsv = new EasyCsv(ExportOnGoogleDrive.this);

        easyCsv.setSeparatorColumn(",");
        easyCsv.setSeperatorLine("-");

        String fileName = "Data";

        easyCsv.createCsvFile(missionList.get(0).getMissionName().trim(), missionList.get(0).getMissionName() + fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {


            @Override
            public void onSuccess(File file) {


            }

            @Override
            public void onFail(String err) {


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


//            try {
//                SwitchAddListString(missionList.get(0).getNumberofMission());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            for (int i = 0; i < missionClone.getNumberofMission(); i++) {





                int finalI1 = i;
                FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + missionClone.getMissionName() + "/" + "picture" + (i + 1) +
                        ".png")
                        .putFile(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/"
                                + missionClone.getMissionName(), "picture" + (i + 1) + ".png")))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                                if (finalI1 == 9){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


                                            progressDialog.dismiss();
                                            recreate();


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
                        progressDialog.setTitle("นำออกแบบทดสอบ "  +"("+ finalI1+"/"+missionClone.getNumberofMission()+")");
                       progressDialog.setMessage("ความคืบหน้า : "+progress + "%");




                    }
                });



            }





        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 48:
                if (resultCode == RESULT_OK) {
                    handleSignInIntent(data);
                }
                break;
        }
    }

    public void requestSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestScopes(new Scope(DriveScopes.DRIVE_FILE)).build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(client.getSignInIntent(), 48);
    }

    private void handleSignInIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
            @Override
            public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(getApplicationContext(),
                        Collections.singleton(DriveScopes.DRIVE_FILE));

                credential.setSelectedAccount(googleSignInAccount.getAccount());

                googleDriveService = new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
                        .setApplicationName("MMSE Drive").build();

//                driveServiceHelper = new DriveServiceHelper(googleDriveService);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public boolean CheckInternet() {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

//    public Task<String> create_folder() {
//        return Tasks.call(mExecutor,()-> {
//            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
//            fileMetadata.setName("English Practice"); //ตั่งชื่อ folder ใน google drive
//            fileMetadata.setMimeType("application/vnd.google-apps.folder");
//            com.google.api.services.drive.model.File file = null;
//            try {
//                file = googleDriveService.files().create(fileMetadata)
//                        .setFields("id")
//                        .execute();
//                System.out.println("Folder ID: " + file.getId());
//                folderId = file.getId();
//            } catch (Exception e){
//                throw  new IOException("Null result");
//            }
//            return file.getId();
//        });}

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
