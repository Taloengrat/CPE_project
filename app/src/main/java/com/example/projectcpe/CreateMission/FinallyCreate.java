package com.example.projectcpe.CreateMission;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Step;

import java.io.ByteArrayOutputStream;
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

    public ImageView mediumImage;
    EditText addEdittext;


    private  final int CAMERA_RESULT_CODE = 100;
    private static int RESULT_LOAD_IMG = 101;
    public static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_create);

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

                for (int i = 0; i < StepAdapter.stepList.size(); i++) {
                    int position = 0;
                    Toast.makeText(getApplicationContext(),  StepAdapter.stepList.get(1).getScore(), Toast.LENGTH_SHORT).show();
                    position++;

                    if (StepAdapter.stepList.get(i).getAnswer() != null && StepAdapter.stepList.get(i).getQuestion() != null && StepAdapter.stepList.get(i).getScore() != null && StepAdapter.stepList.get(i).getHint() != null) {
                        Log.e("Question" + (i+1),StepAdapter.stepList.get(i).getQuestion());
                        Log.e("Answer" + (i+1), StepAdapter.stepList.get(i).getAnswer());
                        Log.e("Score" + (i+1), StepAdapter.stepList.get(i).getScore());
                        Log.e("Hint" + (i+1), StepAdapter.stepList.get(i).getHint());

                    }
                }

//                Toast.makeText(getApplicationContext(), getName, Toast.LENGTH_SHORT).show();
            }
        });

//        loadData();
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

        for (int i = 1 ; i <= mediumNum ;i++) {
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

        Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_SHORT ).show();

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
                if(ContextCompat.checkSelfPermission(FinallyCreate.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(it.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(it, CAMERA_RESULT_CODE);
                        dialog.cancel();
                    }
                }
                else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
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
                Intent getImageIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getImageIntent.setType("image/*");
                startActivityForResult(getImageIntent , RESULT_LOAD_IMG );
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
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Bitmap b1 = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                b1.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                byte[] imageInByte1 = baos1.toByteArray();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(imageInByte1,0,imageInByte1.length,options);

                options.inSampleSize = calculateInSampleSize(options, 500, 500);
                options.inJustDecodeBounds = false;
                Bitmap bmp1 = BitmapFactory.decodeByteArray(imageInByte1,0,imageInByte1.length,options);


                b = Bitmap.createBitmap(bmp1);
                setPic(b);
            }


            else if (resultCode == RESULT_OK && requestCode == CAMERA_RESULT_CODE) {
                Bundle bd = data.getExtras();
                Bitmap bmp = (Bitmap) bd.get("data");
                if (bmp != null) {
                    setPic(bmp);
                }
            }
            else {
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

        int stretch_width = Math.round((float)width / (float)reqWidth);
        int stretch_height = Math.round((float)height / (float)reqHeight);

        if (stretch_width <= stretch_height)
            return stretch_height;
        else
            return stretch_width;
    }



    private void setPic(Bitmap bitmap) {

        this.mediumImage.setImageBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_RESULT_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(it.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(it, CAMERA_RESULT_CODE);
                }
            }
            else{
                Toast.makeText(FinallyCreate.this, "ไม่สามารถใช้งานกล้องได้", Toast.LENGTH_LONG).show();
            }
        }
        else {
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

}



