package com.example.projectcpe.CreateMission.Export;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExportOnGoogleDrive extends AppCompatActivity implements MissionAdapter.OnCustomerItemClick {

    RecyclerView recyclerView;
    List<Mission> missionList;
    ProgressDialog progressDialog;

    Drive drive;
    List<String> headerList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;
    Drive googleDriveService;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_on_google_drive);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        requestSignIn();

        recyclerView = findViewById(R.id.recyclerView);

        loadData();

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


                missionList = MissionDATABASE.getInstance(ExportOnGoogleDrive.this).missionDAO().getAllinfoOfMission(id);


                try {
                    createFileCsv();
                } catch (IOException e) {
                    e.printStackTrace();
                }


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
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5" +
                        ".Score1.Score2.Score3.Score4.Score5" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5-");

                dataList.add(missionList.get(0).getMissionName() + "." + missionList.get(0).getDetailMission() + "." + missionList.get(0).getAge() + "." + missionList.get(0).getNumberofMission()

                        + "." + missionList.get(0).getQ1() + "." + missionList.get(0).getQ2() + "." + missionList.get(0).getQ3() + "." + missionList.get(0).getQ4() + "." + missionList.get(0).getQ5()
                        + "." + missionList.get(0).getA1() + "." + missionList.get(0).getA2() + "." + missionList.get(0).getA3() + "." + missionList.get(0).getA4() + "." + missionList.get(0).getA5()
                        + "." + missionList.get(0).getS1() + "." + missionList.get(0).getS2() + "." + missionList.get(0).getS3() + "." + missionList.get(0).getS4() + "." + missionList.get(0).getS5()
                        + "." + missionList.get(0).getH1() + "." + missionList.get(0).getH2() + "." + missionList.get(0).getH3() + "." + missionList.get(0).getH4() + "." + missionList.get(0).getH5());

                createFileCsv();


//                try {
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.png"))), "picture1");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.png"))), "picture2");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.png"))), "picture3");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.png"))), "picture4");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.png"))), "picture5");
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;
            case 6:
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6-");

                dataList.add(missionList.get(0).getMissionName() + "." + missionList.get(0).getDetailMission() + "." + missionList.get(0).getAge() + "." + missionList.get(0).getNumberofMission()
                        + "." + missionList.get(0).getQ1() + "." + missionList.get(0).getQ2() + "." + missionList.get(0).getQ3() + "." + missionList.get(0).getQ4() + "." + missionList.get(0).getQ5() + "." + missionList.get(0).getQ6()
                        + "." + missionList.get(0).getA1() + "." + missionList.get(0).getA2() + "." + missionList.get(0).getA3() + "." + missionList.get(0).getA4() + "." + missionList.get(0).getA5() + "." + missionList.get(0).getA6()
                        + "." + missionList.get(0).getS1() + "." + missionList.get(0).getS2() + "." + missionList.get(0).getS3() + "." + missionList.get(0).getS4() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS5()
                        + "." + missionList.get(0).getH1() + "." + missionList.get(0).getH2() + "." + missionList.get(0).getH3() + "." + missionList.get(0).getH4() + "." + missionList.get(0).getH5() + "." + missionList.get(0).getH6() + "-");

                createFileCsv();

//                try {
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.png"))), "picture1");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.png"))), "picture2");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.png"))), "picture3");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.png"))), "picture4");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.png"))), "picture5");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.png"))), "picture6");
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;
            case 7:
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7-");

                dataList.add(missionList.get(0).getMissionName() + "." + missionList.get(0).getDetailMission() + "." + missionList.get(0).getAge() + "." + missionList.get(0).getNumberofMission()
                        + "." + missionList.get(0).getQ1() + "." + missionList.get(0).getQ2() + "." + missionList.get(0).getQ3() + "." + missionList.get(0).getQ4() + "." + missionList.get(0).getQ5() + "." + missionList.get(0).getQ6() + "." + missionList.get(0).getQ7()
                        + "." + missionList.get(0).getA1() + "." + missionList.get(0).getA2() + "." + missionList.get(0).getA3() + "." + missionList.get(0).getA4() + "." + missionList.get(0).getA5() + "." + missionList.get(0).getA6() + "." + missionList.get(0).getA7()
                        + "." + missionList.get(0).getS1() + "." + missionList.get(0).getS2() + "." + missionList.get(0).getS3() + "." + missionList.get(0).getS4() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS7()
                        + "." + missionList.get(0).getH1() + "." + missionList.get(0).getH2() + "." + missionList.get(0).getH3() + "." + missionList.get(0).getH4() + "." + missionList.get(0).getH5() + "." + missionList.get(0).getH6() + "." + missionList.get(0).getH7() + "-");

                createFileCsv();

//                try {
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.png"))), "picture1");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.png"))), "picture2");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.png"))), "picture3");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.png"))), "picture4");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.png"))), "picture5");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.png"))), "picture6");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.png"))), "picture7");
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;
            case 8:
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7.Question8" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7.Answer8" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7.Score8" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7.Hint8-");

                dataList.add(missionList.get(0).getMissionName() + "." + missionList.get(0).getDetailMission() + "." + missionList.get(0).getAge() + "." + missionList.get(0).getNumberofMission()
                        + "." + missionList.get(0).getQ1() + "." + missionList.get(0).getQ2() + "." + missionList.get(0).getQ3() + "." + missionList.get(0).getQ4() + "." + missionList.get(0).getQ5() + "." + missionList.get(0).getQ6() + "." + missionList.get(0).getQ7() + "." + missionList.get(0).getQ8()
                        + "." + missionList.get(0).getA1() + "." + missionList.get(0).getA2() + "." + missionList.get(0).getA3() + "." + missionList.get(0).getA4() + "." + missionList.get(0).getA5() + "." + missionList.get(0).getA6() + "." + missionList.get(0).getA7() + "." + missionList.get(0).getA8()
                        + "." + missionList.get(0).getS1() + "." + missionList.get(0).getS2() + "." + missionList.get(0).getS3() + "." + missionList.get(0).getS4() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS7() + "." + missionList.get(0).getS8()
                        + "." + missionList.get(0).getH1() + "." + missionList.get(0).getH2() + "." + missionList.get(0).getH3() + "." + missionList.get(0).getH4() + "." + missionList.get(0).getH5() + "." + missionList.get(0).getH6() + "." + missionList.get(0).getH7() + "." + missionList.get(0).getH8() + "-");

                createFileCsv();

//                try {
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.png"))), "picture1");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.png"))), "picture2");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.png"))), "picture3");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.png"))), "picture4");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.png"))), "picture5");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.png"))), "picture6");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.png"))), "picture7");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture8.png"))), "picture8");
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;

            case 9:
                headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                        ".Question1.Question2.Question3.Question4.Question5.Question6.Question7.Question8.Question9" +
                        ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7.Answer8.Answer9" +
                        ".Score1.Score2.Score3.Score4.Score5.Score6.Score7.Score8.Score9" +
                        ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7.Hint8.Hint9-");

                dataList.add(missionList.get(0).getMissionName() + "." + missionList.get(0).getDetailMission() + "." + missionList.get(0).getAge() + "." + missionList.get(0).getNumberofMission()
                        + "." + missionList.get(0).getQ1() + "." + missionList.get(0).getQ2() + "." + missionList.get(0).getQ3() + "." + missionList.get(0).getQ4() + "." + missionList.get(0).getQ5() + "." + missionList.get(0).getQ6() + "." + missionList.get(0).getQ7() + "." + missionList.get(0).getQ8() + "." + missionList.get(0).getQ9()
                        + "." + missionList.get(0).getA1() + "." + missionList.get(0).getA2() + "." + missionList.get(0).getA3() + "." + missionList.get(0).getA4() + "." + missionList.get(0).getA5() + "." + missionList.get(0).getA6() + "." + missionList.get(0).getA7() + "." + missionList.get(0).getA8() + "." + missionList.get(0).getA9()
                        + "." + missionList.get(0).getS1() + "." + missionList.get(0).getS2() + "." + missionList.get(0).getS3() + "." + missionList.get(0).getS4() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS7() + "." + missionList.get(0).getS8() + "." + missionList.get(0).getS9()
                        + "." + missionList.get(0).getH1() + "." + missionList.get(0).getH2() + "." + missionList.get(0).getH3() + "." + missionList.get(0).getH4() + "." + missionList.get(0).getH5() + "." + missionList.get(0).getH6() + "." + missionList.get(0).getH7() + "." + missionList.get(0).getH8() + "." + missionList.get(0).getH9() + "-");

                createFileCsv();

//                try {
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.png"))), "picture1");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.png"))), "picture2");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.png"))), "picture3");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.png"))), "picture4");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.png"))), "picture5");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.png"))), "picture6");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.png"))), "picture7");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture8.png"))), "picture8");
//                    saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture9.png"))), "picture9");
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }


                break;

            case 10:

                try {
                    headerList.add("MissionName.DetailMission.ForAge.NumberOfMission" +
                            ".Question1.Question2.Question3.Question4.Question5.Question6.Question7.Question8.Question9.Question10" +
                            ".Answer1.Answer2.Answer3.Answer4.Answer5.Answer6.Answer7.Answer8.Answer9.Answer10" +
                            ".Score1.Score2.Score3.Score4.Score5.Score6.Score7.Score8.Score9.Score10" +
                            ".Hint1.Hint2.Hint3.Hint4.Hint5.Hint6.Hint7.Hint8.Hint9.Hint10-");

                    dataList.add(missionList.get(0).getMissionName() + "." + missionList.get(0).getDetailMission() + "." + missionList.get(0).getAge() + "." + missionList.get(0).getNumberofMission()
                            + "." + missionList.get(0).getQ1() + "." + missionList.get(0).getQ2() + "." + missionList.get(0).getQ3() + "." + missionList.get(0).getQ4() + "." + missionList.get(0).getQ5() + "." + missionList.get(0).getQ6() + "." + missionList.get(0).getQ7() + "." + missionList.get(0).getQ8() + "." + missionList.get(0).getQ9() + "." + missionList.get(0).getQ10()
                            + "." + missionList.get(0).getA1() + "." + missionList.get(0).getA2() + "." + missionList.get(0).getA3() + "." + missionList.get(0).getA4() + "." + missionList.get(0).getA5() + "." + missionList.get(0).getA6() + "." + missionList.get(0).getA7() + "." + missionList.get(0).getA8() + "." + missionList.get(0).getA9() + "." + missionList.get(0).getA10()
                            + "." + missionList.get(0).getS1() + "." + missionList.get(0).getS2() + "." + missionList.get(0).getS3() + "." + missionList.get(0).getS4() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS5() + "." + missionList.get(0).getS7() + "." + missionList.get(0).getS8() + "." + missionList.get(0).getS9() + "." + missionList.get(0).getS10()
                            + "." + missionList.get(0).getH1() + "." + missionList.get(0).getH2() + "." + missionList.get(0).getH3() + "." + missionList.get(0).getH4() + "." + missionList.get(0).getH5() + "." + missionList.get(0).getH6() + "." + missionList.get(0).getH7() + "." + missionList.get(0).getH8() + "." + missionList.get(0).getH9() + "." + missionList.get(0).getH10() + "-");

                } catch (Exception ex) {
                    Log.v("NIGHT", ex.getMessage());
                } finally {
                    createFileCsv();

//                    try {
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture1.png"))), "picture1");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture2.png"))), "picture2");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture3.png"))), "picture3");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture4.png"))), "picture4");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture5.png"))), "picture5");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture6.png"))), "picture6");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture7.png"))), "picture7");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture8.png"))), "picture8");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture9.png"))), "picture9");
//                        saveToInternalStorage(BitmapFactory.decodeStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionList.get(0).getMissionName(), "picture10.png"))), "picture10");
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
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


//        stopThread = true;

        MissionDATABASE.getInstance(ExportOnGoogleDrive.this).missionDAO().delete(missionList.get(0));


    }

    private void createFileCsv() throws IOException {


        EasyCsv easyCsv = new EasyCsv(ExportOnGoogleDrive.this);

        easyCsv.setSeparatorColumn(".");
        easyCsv.setSeperatorLine("-");

        String fileName = "Data";

        easyCsv.createCsvFile(missionList.get(0).getMissionName().trim(), missionList.get(0).getMissionName() + fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {


            @Override
            public void onSuccess(File file) throws IOException {

                Toast.makeText(getApplicationContext(), "csv file created", Toast.LENGTH_SHORT).show();

                com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
                fileMetadata.setName("textgoogledrive.csv");
                java.io.File filePath = file;
                FileContent mediaContent = new FileContent("csv", file);
                com.google.api.services.drive.model.File files = googleDriveService.files().create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute();
//                System.out.println("File ID: " + file.getId());


            }

            @Override
            public void onFail(String err) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
