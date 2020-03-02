package com.example.projectcpe.CreateMission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
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

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectcpe.Adapter.StepAdapter;
import com.example.projectcpe.AdminPage;
import com.example.projectcpe.CSV.Utility.EasyCsv;
import com.example.projectcpe.CSV.Utility.FileCallback;
import com.example.projectcpe.CreateMission.Export.ExportOnDevice;
import com.example.projectcpe.CreateMission.Export.ExportOnGoogleDrive;
import com.example.projectcpe.Main2Activity;
import com.example.projectcpe.MainActivity;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.example.projectcpe.ViewModel.Step;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;


public class FinallyCreate extends AppCompatActivity implements StepAdapter.OnCustomrPictureClick {

    String currentPhotoPath;
    Uri photoURI;
    File photoFile;
    String imgDecodableString;
    public Bitmap b;

    RecyclerView recyclerViewStep;

    List<Step> steplist;
    String getName, getDetail;
    int getNumOfStep, getAge;
    Button btSubmit;
    LinearLayout frameEdittext;
    String getTime,getTimeDeduction;
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;

    Bundle bundle ;

    public ImageView mediumImage;
    private volatile boolean stopThread = false;
    ProgressDialog loadingDialog;
    Mission mission = new Mission();
    ImageView test;
    public Bitmap b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
    List<String> headerList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();


    private final int CAMERA_RESULT_CODE = 100;
    private static int RESULT_LOAD_IMG = 101;
    public static int position;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_create);





bundle = getIntent().getExtras();
        getNumOfStep = bundle.getInt("NumOfStep");
        getName = bundle.getString("name");
        getDetail = bundle.getString("detail");
        getAge = bundle.getInt("age");
        getTime = bundle.getString("time");
        getTimeDeduction = bundle.getString("timededuction");
        Initia();



        for (int i = 0; i < StepAdapter.stepList.size(); i++) {
            steplist.get(i).seticonHint(getResources().getDrawable(R.drawable.hint).getConstantState());
            steplist.get(i).seticonScore(getResources().getDrawable(R.drawable.unscore).getConstantState());
        }

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String checker = "default";

                for (int i = 0; i < StepAdapter.stepList.size(); i++){

//                    if (StepAdapter.stepList.get(i).geticonHint() == getResources().getDrawable(R.drawable.hint).getConstantState()){
//                        checker = "checkHint";
//                        Drawable.ConstantState constantState1 = StepAdapter.stepList.get(i).geticonHint();
//                        Drawable.ConstantState constantState2 = getResources().getDrawable(R.drawable.hint).getConstantState();

                    if (StepAdapter.stepList.get(i).getPicture() == null){
                        checker = "checkPicture";
                        break;
                    }else if (StepAdapter.stepList.get(i).getQuestion() == null){
                        checker = "checkQuestion";
                        break;
                    }else if (StepAdapter.stepList.get(i).getAnswer() == null){
                        checker = "checkAnswer";
                        break;
                    }else if (StepAdapter.stepList.get(i).getScore() == null){
                        checker = "checkScore";
                        break;
                    }else if (StepAdapter.stepList.get(i).geticonScore() == getResources().getDrawable(R.drawable.unscore).getConstantState()){
                        checker = "checkScore";
                        break;
                    }else if (StepAdapter.stepList.get(i).getHint() == null) {
                        checker = "checkHint";
                        break;
                    }else if (StepAdapter.stepList.get(i).geticonHint() == getResources().getDrawable(R.drawable.hint).getConstantState()){
                        checker = "checkHint";
                        break;
                    }else {
                        checker = "statusOK";
                    }

                    recyclerViewStep.scrollToPosition(i);
//                    recyclerViewStep.getChildAt(i).setBackgroundColor(R.color.blue50);
//                    recyclerViewStep.getChildAt(i).setBackgroundColor(R.color.blue100);

//                    if (StepAdapter.stepList.get(i).getAnswer() != null && StepAdapter.stepList.get(i).getQuestion() != null && StepAdapter.stepList.get(i).getScore() != null && StepAdapter.stepList.get(i).getHint() != null) {
//                        checker = 0;
//                    } else if (StepAdapter.stepList.get(i).getPicture() == null){
//                        checker = 1;
//                    } else {
//                        checker = 3;
//                    }

                }

                switch (checker) {
                    case "statusOK" :
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
//                                test.setImageBitmap(b5);
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
                        break;

                    case "checkPicture":
                        Toast.makeText(FinallyCreate.this,"Please Insert Picture.",Toast.LENGTH_SHORT).show();
                        break;

                    case "checkQuestion":
                        Toast.makeText(FinallyCreate.this,"Please Insert Question.",Toast.LENGTH_SHORT).show();
                        break;

                    case "checkAnswer":
                        Toast.makeText(FinallyCreate.this,"Please Insert Answer.",Toast.LENGTH_SHORT).show();
                        break;

                    case "checkScore":
                        Toast.makeText(FinallyCreate.this,"Please Insert Score.",Toast.LENGTH_SHORT).show();
                        break;

                    case "checkHint":
                        Toast.makeText(FinallyCreate.this,"Please Insert Hint.",Toast.LENGTH_SHORT).show();
                        break;

                }

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

    private List<Step> getStepList() {
        for (int i = 0; i < getNumOfStep; i++) {
            steplist.add(new Step());
        }
        return steplist;
    }

    private void Initia() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        steplist = new ArrayList<Step>();
        btSubmit = findViewById(R.id.btnsubmit);
        frameEdittext = findViewById(R.id.frameEdittext);

        RecyclerView.Adapter adapter = new StepAdapter(getStepList(),this);
        recyclerViewStep = findViewById(R.id.recyclerViewStepCreate);
        recyclerViewStep.setHasFixedSize(true);
        recyclerViewStep.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStep.setAdapter(adapter);





    }


    @Override
    public void oncustompictureclick(final int pos, final ImageView imageView) {

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
//                    if (it.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(it, CAMERA_RESULT_CODE);
//                        dialog.cancel();
//                    }

                    if (it.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            photoURI = FileProvider.getUriForFile(FinallyCreate.this,
                                    "com.example.android.fileprovider",
                                    photoFile);
                            it.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                            startActivityForResult(it, CAMERA_RESULT_CODE);

                        }
                    }

                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        Toast.makeText(FinallyCreate.this, "ไม่สามารถใช้งานกล้องได้", Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT_CODE);
                }dialog.cancel();
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

                options.inSampleSize = calculateInSampleSize(options, 700, 1000);
                options.inJustDecodeBounds = false;
                Bitmap bmp1 = BitmapFactory.decodeByteArray(imageInByte1, 0, imageInByte1.length, options);


                b = Bitmap.createBitmap(bmp1);
                mediumImage.setImageBitmap(b);
//                rotateImageView(mediumImage);

                steplist.get(position).setPicture(b);

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


//                Bundle bd = data.getExtras();
//                Bitmap bmp = (Bitmap) bd.get("data");

                Bitmap bmp = null;
                Bitmap bmp2 = null;


                String file = photoFile.getAbsolutePath();


                InputStream iStream = getContentResolver().openInputStream(photoURI);


                byte[] inputData = getBytes(iStream);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(inputData, 0, inputData.length, options);

                options.inSampleSize = calculateInSampleSize(options, 700, 1000);
                options.inJustDecodeBounds = false;
                Bitmap bmp1 = BitmapFactory.decodeByteArray(inputData, 0, inputData.length, options);

                bmp2 = Bitmap.createBitmap(modifyOrientation(bmp1,file));

                bmp = Bitmap.createBitmap(bmp2);


//                rotateBitmap(bmp);



                if (bmp != null) {

                    mediumImage.setImageBitmap(bmp);

                    steplist.get(position).setPicture(bmp);


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

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }









    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage) {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.decodeStream(imageStream, null, options);
        try {
            imageStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        try {
            imageStream = context.getContentResolver().openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage){

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(selectedImage.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }















    private ImageView rotateImageView(ImageView img){
        float imgX = img.getDrawable().getIntrinsicWidth();;
        float imgY = img.getDrawable().getIntrinsicHeight();;
        if (imgX > imgY){
            img.setRotation(90.0f);
        }

        return img;
    }

   private Bitmap rotateBitmap(Bitmap bitmapR){

        Matrix matrix = new Matrix();

       matrix.postRotate(90);
       int width = bitmapR.getWidth();
       int height = bitmapR.getHeight();

       return Bitmap.createBitmap(bitmapR, 0, 0, bitmapR.getWidth(), bitmapR.getHeight(), matrix, true);
   }







    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
        return bytesResult;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//
//        int stretch_width = Math.round((float) width / (float) reqWidth);
//        int stretch_height = Math.round((float) height / (float) reqHeight);
//
//        if (stretch_width <= stretch_height)
//            return stretch_height;
//        else
//            return stretch_width;
//    }

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
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

                        Intent i = new Intent(FinallyCreate.this,AdminPage.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();

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
        mission.setTime(getTime);
        mission.setTimeDeduction(getTimeDeduction);


        for (int i = 0; i < StepAdapter.stepList.size(); i++) {

//            if (StepAdapter.stepList.get(i).getAnswer() != null && StepAdapter.stepList.get(i).getQuestion() != null && StepAdapter.stepList.get(i).getScore() != null && StepAdapter.stepList.get(i).getHint() != null) {



                switch (i) {

                    case 0:
                        saveToInternalStorage(b1, "picture1", getName);
                        mission.setQ1(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA1(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS1(StepAdapter.stepList.get(i).getScore());
                        mission.setH1(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 1:
                        saveToInternalStorage(b2, "picture2", getName);
                        mission.setQ2(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA2(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS2(StepAdapter.stepList.get(i).getScore());
                        mission.setH2(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 2:
                        saveToInternalStorage(b3, "picture3", getName);
                        mission.setQ3(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA3(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS3(StepAdapter.stepList.get(i).getScore());
                        mission.setH3(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 3:
                        saveToInternalStorage(b4, "picture4", getName);
                        mission.setQ4(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA4(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS4(StepAdapter.stepList.get(i).getScore());
                        mission.setH4(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 4:
                        saveToInternalStorage(b5, "picture5", getName);
                        mission.setQ5(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA5(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS5(StepAdapter.stepList.get(i).getScore());
                        mission.setH5(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 5:
                        saveToInternalStorage(b6, "picture6", getName);
                        mission.setQ6(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA6(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS6(StepAdapter.stepList.get(i).getScore());
                        mission.setH6(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 6:
                        saveToInternalStorage(b7, "picture7", getName);
                        mission.setQ7(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA7(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS7(StepAdapter.stepList.get(i).getScore());
                        mission.setH7(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 7:
                        saveToInternalStorage(b8, "picture8", getName);
                        mission.setQ8(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA8(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS8(StepAdapter.stepList.get(i).getScore());
                        mission.setH8(StepAdapter.stepList.get(i).getHint());


                        break;
                    case 8:
                        saveToInternalStorage(b9, "picture9", getName);
                        mission.setQ9(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA9(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS9(StepAdapter.stepList.get(i).getScore());
                        mission.setH9(StepAdapter.stepList.get(i).getHint());
                        break;
                    case 9:
                        saveToInternalStorage(b10, "picture10", getName);
                        mission.setQ10(StepAdapter.stepList.get(i).getQuestion());
                        mission.setA10(StepAdapter.stepList.get(i).getAnswer());
                        mission.setS10(StepAdapter.stepList.get(i).getScore());
                        mission.setH10(StepAdapter.stepList.get(i).getHint());
                        break;
                }

//            }




        }

        Log.v("check : ", steplist.get(0).getAnswer());
        MissionDATABASE.getInstance(FinallyCreate.this).missionDAO().create(mission);

        stopThread = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(FinallyCreate.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(FinallyCreate.this, MusicService.class));
        startService(new Intent(FinallyCreate.this, MusicService.class));
    }

    private void createFileCsv() throws IOException {


        EasyCsv easyCsv = new EasyCsv(FinallyCreate.this);

        easyCsv.setSeparatorColumn(".");
        easyCsv.setSeperatorLine("-");

        /// create folder
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/EnglishPractice/" + getName;
        File file = new File(directory_path);
        if (!file.exists())
        {
            file.mkdirs();
        }


        String fileName="Data";

        easyCsv.createCsvFile(getName,getName+fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {


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



}



