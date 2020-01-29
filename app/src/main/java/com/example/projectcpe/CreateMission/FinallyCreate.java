package com.example.projectcpe.CreateMission;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectcpe.Adapter.StepAdapter;
import com.example.projectcpe.AdminPage;
import com.example.projectcpe.Main2Activity;
import com.example.projectcpe.MainActivity;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Step;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FinallyCreate extends AppCompatActivity implements StepAdapter.OnCustomrPictureClick {


    String imgDecodableString;
    public Bitmap b;

    RecyclerView recyclerViewStep;
    StepAdapter adapter;
    List<Step> steplist;
    String getName, getDetail;
    int getNumOfStep, getAge;
    Button btSubmit;
    LinearLayout frameEdittext;
    List<Step> stepList;

    public ImageView mediumImage;
    EditText addEdittext;
    private volatile boolean stopThread = false;
    ProgressDialog loadingDialog;
    Mission mission = new Mission();
    ImageView test;
    public Bitmap b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;


    private final int CAMERA_RESULT_CODE = 100;
    private static int RESULT_LOAD_IMG = 101;
    public static int position;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_create);

        test = findViewById(R.id.test);

        Initia();

        Bundle bundle = getIntent().getExtras();
        getNumOfStep = bundle.getInt("NumOfStep");
        getName = bundle.getString("name");
        getDetail = bundle.getString("detail");
        getAge = bundle.getInt("age");

        NumStepListener(this.getNumOfStep);

        btSubmit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(FinallyCreate.this, Main2Activity.class);
                startActivity(intent);
                return true;
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(FinallyCreate.this);
                dialog.setTitle("create mission");
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to create mission?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/" + getName;
                        File file = new File(directory_path);
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        test.setImageBitmap(b5);
                        loadingDialog = ProgressDialog.show(FinallyCreate.this, "Create Mission", "Creating...", true, false);


                        startThread();

                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();


//                Toast.makeText(getApplicationContext(), getName, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String saveToInternalStorage(Bitmap bitmapImage, String picturename, String missionName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = new File(Environment.getExternalStorageDirectory() + "/EnglishPractice/" + missionName + "/");
        // Create imageDir
        File mypath = new File(directory, picturename + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 30, fos);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == 1) {
//            if(resultCode == Activity.RESULT_OK){
//
//
//                data.getIntExtra("NumOfStep",this.mediumNum);
//
//
//                NumStepListener(this.mediumNum);
//            }
//            if (resultCode == Activity.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
//        }
//    }

    private void NumStepListener(int mediumNum) {

        for (int i = 0; i < mediumNum; i++) {
            steplist.add(new Step());
        }

        adapter = new StepAdapter(steplist, this);
        recyclerViewStep.setAdapter(adapter);
    }

    private void Initia() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        steplist = new ArrayList<Step>();
        btSubmit = findViewById(R.id.btnsubmit);
        recyclerViewStep = findViewById(R.id.recyclerViewStepCreate);
        recyclerViewStep.setHasFixedSize(true);
        recyclerViewStep.setLayoutManager(new LinearLayoutManager(this));
        frameEdittext = findViewById(R.id.frameEdittext);

//        for (int i = 0 ; i<)
//
//        adapter = new StepAdapter(, this);

        recyclerViewStep.setAdapter(adapter);

    }


    @Override
    public void oncustompictureclick(final int pos, final ImageView imageView) {

        Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_SHORT).show();

        position = pos;

        this.mediumImage = imageView;


        final Dialog dialog;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.picture_dialog);
        dialog.setCancelable(true);

        dialog.show();

        Button btCamera = dialog.findViewById(R.id.ChooseCamera);
        btCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "this is Choose Camera", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(FinallyCreate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (it.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(it, CAMERA_RESULT_CODE);
                        dialog.cancel();
                    }
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        Toast.makeText(FinallyCreate.this, "ไม่สามารถใช้งานกล้องได้", Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT_CODE);
                }
            }
        });


        Button btGallary = dialog.findViewById(R.id.ok);

        btGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getImageIntent.setType("image/*");
                startActivityForResult(getImageIntent, RESULT_LOAD_IMG);
                Toast.makeText(getApplicationContext(), "this is Choose Gallary", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                byte[] imageInByte1 = baos1.toByteArray();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(imageInByte1, 0, imageInByte1.length, options);

                options.inSampleSize = calculateInSampleSize(options, 500, 500);
                options.inJustDecodeBounds = false;
                Bitmap bmp1 = BitmapFactory.decodeByteArray(imageInByte1, 0, imageInByte1.length, options);


//                b = Bitmap.createBitmap(bmp1);
                mediumImage.setImageBitmap(bmp1);

                switch (position + 1) {

                    case 1:
                        b1 = b;
                        break;
                    case 2:
                        b2 = b;
                        break;
                    case 3:
                        b3 = b;
                        break;
                    case 4:
                        b4 = b;
                        break;
                    case 5:
                        b5 = b;
                        break;
                    case 6:
                        b6 = b;
                        break;
                    case 7:
                        b7 = b;
                        break;
                    case 8:
                        b8 = b;
                        break;
                    case 9:
                        b9 = b;
                        break;
                    case 10:
                        b10 = b;
                        break;
                }

            } else if (resultCode == RESULT_OK && requestCode == CAMERA_RESULT_CODE) {
                Bundle bd = data.getExtras();
                Bitmap bmp = (Bitmap) bd.get("data");
                if (bmp != null) {
                    mediumImage.setImageBitmap(bmp);

                    switch (position + 1) {

                        case 1:
                            b1 = bmp;
                            break;
                        case 2:
                            b2 = bmp;
                            break;
                        case 3:
                            b3 = bmp;
                            break;
                        case 4:
                            b4 = bmp;
                            break;
                        case 5:
                            b5 = bmp;
                            break;
                        case 6:
                            b6 = bmp;
                            break;
                        case 7:
                            b7 = bmp;
                            break;
                        case 8:
                            b8 = bmp;
                            break;
                        case 9:
                            b9 = bmp;
                            break;
                        case 10:
                            b10 = bmp;
                            break;
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int stretch_width = Math.round((float) width / (float) reqWidth);
        int stretch_height = Math.round((float) height / (float) reqHeight);

        if (stretch_width <= stretch_height)
            return stretch_height;
        else
            return stretch_width;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_RESULT_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (it.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(it, CAMERA_RESULT_CODE);
                }
            } else {
                Toast.makeText(FinallyCreate.this, "ไม่สามารถใช้งานกล้องได้", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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


            creatingMission();


            if (stopThread) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        loadingDialog.dismiss();


                    }
                });
            }


        }


    }

    private void creatingMission() {

        mission.setMissionName(getName);
        mission.setDetailMission(getDetail);
        mission.setAge(getAge);
        mission.setNumberofMission(getNumOfStep);


        for (int i = 0; i < StepAdapter.stepList.size(); i++) {

            if (StepAdapter.stepList.get(i).getAnswer() != null && StepAdapter.stepList.get(i).getQuestion() != null && StepAdapter.stepList.get(i).getScore() != null && StepAdapter.stepList.get(i).getHint() != null) {



                switch (i) {

                    case 0:
                        saveToInternalStorage(b1, "picture1", getName);
                        mission.setQ1(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA1(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS1(StepAdapter.stepList.get(i).getScore());
                        mission.setH1(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 1:
                        saveToInternalStorage(b2, "picture2", getName);
                        mission.setQ2(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA2(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS2(StepAdapter.stepList.get(i).getScore());
                        mission.setH2(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 2:
                        saveToInternalStorage(b3, "picture3", getName);
                        mission.setQ3(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA3(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS3(StepAdapter.stepList.get(i).getScore());
                        mission.setH3(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 3:
                        saveToInternalStorage(b4, "picture4", getName);
                        mission.setQ4(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA4(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS4(StepAdapter.stepList.get(i).getScore());
                        mission.setH4(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 4:
                        saveToInternalStorage(b5, "picture5", getName);
                        mission.setQ5(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA5(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS5(StepAdapter.stepList.get(i).getScore());
                        mission.setH5(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 5:
                        saveToInternalStorage(b6, "picture6", getName);
                        mission.setQ6(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA6(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS6(StepAdapter.stepList.get(i).getScore());
                        mission.setH6(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 6:
                        saveToInternalStorage(b7, "picture7", getName);
                        mission.setQ7(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA7(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS7(StepAdapter.stepList.get(i).getScore());
                        mission.setH7(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 7:
                        saveToInternalStorage(b8, "picture8", getName);
                        mission.setQ8(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA8(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS8(StepAdapter.stepList.get(i).getScore());
                        mission.setH8(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 8:
                        saveToInternalStorage(b9, "picture9", getName);
                        mission.setQ9(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA9(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS9(StepAdapter.stepList.get(i).getScore());
                        mission.setH9(StepAdapter.stepList.get(i).getScore());
                        break;
                    case 9:
                        saveToInternalStorage(b10, "picture10", getName);
                        mission.setQ10(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA10(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS10(StepAdapter.stepList.get(i).getScore());
                        mission.setH10(StepAdapter.stepList.get(i).getScore());
                        break;
                }

            }

            stopThread = true;

            MissionDATABASE.getInstance(FinallyCreate.this).missionDAO().create(mission);
        }
    }
}



