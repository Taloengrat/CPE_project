package com.example.projectcpe;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.projectcpe.Adapter.MemberAdapterBegin;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {
    ImageView _logoApp;
    EditText _etPass;
    ImageView imMedium;
    Button _btBegin;

    ImageView MediumP1, MediumP2, MediumP3, MediumP4, MediumP5, MediumP6, MediumP7, MediumP8, MediumP9, MediumP10;


    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _logoApp = (ImageView) findViewById(R.id.logoApp);
        _btBegin = (Button) findViewById(R.id._etBegin);

        _etPass = (EditText) findViewById(R.id.etPass);


        _btBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CreateMissionDefault();

            _etPass.setVisibility(View.VISIBLE);
                _btBegin.setText("LET's GO !");

                _btBegin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(_etPass.getText().toString().isEmpty()){
                            Snackbar.make(_etPass, "Plese enter your password for Admin !", Snackbar.LENGTH_SHORT).show();
                        }else {
                            SharedPreferences.Editor password = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE).edit();
                            password.putInt("Pass", Integer.parseInt(_etPass.getText().toString().trim()));
                            password.commit();
                            Snackbar.make(_etPass, "Created Password For Admin", Snackbar.LENGTH_SHORT).show();

                            CreateMember();



                        }
                        }
                });
            }


        });

    }

    private void CreateMissionDefault() {

        Bitmap bitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.red)).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(R.drawable.green)).getBitmap();
        Bitmap bitmap3 = ((BitmapDrawable) getResources().getDrawable(R.drawable.blue)).getBitmap();
        Bitmap bitmap4 = ((BitmapDrawable) getResources().getDrawable(R.drawable.yellow)).getBitmap();
        Bitmap bitmap5 = ((BitmapDrawable) getResources().getDrawable(R.drawable.black)).getBitmap();
        Bitmap bitmap6 = ((BitmapDrawable) getResources().getDrawable(R.drawable.white)).getBitmap();
        Bitmap bitmap7 = ((BitmapDrawable) getResources().getDrawable(R.drawable.brown)).getBitmap();
        Bitmap bitmap8 = ((BitmapDrawable) getResources().getDrawable(R.drawable.purple)).getBitmap();
        Bitmap bitmap9 = ((BitmapDrawable) getResources().getDrawable(R.drawable.gray)).getBitmap();
        Bitmap bitmap10 = ((BitmapDrawable) getResources().getDrawable(R.drawable.orange)).getBitmap();

        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos4 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos5 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos6 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos7 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos8 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos9 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos10 = new ByteArrayOutputStream();

        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos3);
        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos4);
        bitmap5.compress(Bitmap.CompressFormat.JPEG, 100, baos5);
        bitmap6.compress(Bitmap.CompressFormat.JPEG, 100, baos6);
        bitmap7.compress(Bitmap.CompressFormat.JPEG, 100, baos7);
        bitmap8.compress(Bitmap.CompressFormat.JPEG, 100, baos8);
        bitmap9.compress(Bitmap.CompressFormat.JPEG, 100, baos9);
        bitmap10.compress(Bitmap.CompressFormat.JPEG, 100, baos10);

        byte[] imageInByte1 = baos1.toByteArray();
        byte[] imageInByte2 = baos2.toByteArray();
        byte[] imageInByte3 = baos3.toByteArray();
        byte[] imageInByte4 = baos4.toByteArray();
        byte[] imageInByte5 = baos5.toByteArray();
        byte[] imageInByte6 = baos6.toByteArray();
        byte[] imageInByte7 = baos7.toByteArray();
        byte[] imageInByte8 = baos8.toByteArray();
        byte[] imageInByte9 = baos9.toByteArray();
        byte[] imageInByte10 = baos10.toByteArray();


                Mission mission = new Mission("Color", "Various colors", 5,10
        ,imageInByte1, imageInByte2, imageInByte3, imageInByte4, imageInByte5, imageInByte6, imageInByte7,imageInByte8,imageInByte9,imageInByte10
        ,getResources().getString(R.string.q1_1),getResources().getString(R.string.q1_2), getResources().getString(R.string.q1_3),getResources().getString(R.string.q1_4),getResources().getString(R.string.q1_5)
                        ,getResources().getString(R.string.q1_6),getResources().getString(R.string.q1_7),getResources().getString(R.string.q1_8)
                        ,getResources().getString(R.string.q1_9),getResources().getString(R.string.q1_10)
                   ,getResources().getString(R.string.a1_1).trim(), getResources().getString(R.string.a1_2).trim(),getResources().getString(R.string.a1_3).trim()
                        , getResources().getString(R.string.a1_4).trim(),getResources().getString(R.string.a1_5).trim()
                        ,getResources().getString(R.string.a1_6).trim(),getResources().getString(R.string.a1_7).trim(),getResources().getString(R.string.a1_8).trim()
                        ,getResources().getString(R.string.a1_9).trim(),getResources().getString(R.string.a1_10).trim());



        MissionDATABASE.getInstance(MainActivity.this).missionDAO().create(mission);

    }



    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
        int gettingPassword = 0;
        int gettedPassword  = getPassword.getInt("Pass", gettingPassword);
        if (gettedPassword == 0){

        }else{
            startActivity(new Intent(MainActivity.this, LogoIntro.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    private void CreateMember(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addmember_dialog);
        dialog.setCancelable(false);
        //

        final EditText _etName = dialog.findViewById(R.id.etname);
        final EditText _etAge = dialog.findViewById(R.id.etage);
        final ImageView profile = dialog.findViewById(R.id.head);
        final EditText _etPassword = dialog.findViewById(R.id.etpassword);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_profile);
                dialog.setCancelable(true);

                View.OnClickListener Clickkk = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        imMedium = (ImageView) view;

                        Toast.makeText(getApplicationContext(), String.valueOf(imMedium.getDrawable()), Toast.LENGTH_SHORT).show();
                        profile.setImageDrawable(imMedium.getDrawable());
                        dialog.cancel();
                    }
                };

                ImageView pro1, pro2, pro3, pro4, pro5, pro6, pro7, pro8, pro9, pro10,pro11, pro12, pro13, pro14, pro15;

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
                if (_etName.getText().toString().isEmpty()) {
                    _etName.setError("Name is required");
                    _etName.requestFocus();
                    return;
                } else {

                    Bitmap bitmap = ((BitmapDrawable) imMedium.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();

                    Member newMember = new Member(_etName.getText().toString(), _etAge.getText().toString(), imageInByte, Integer.valueOf(_etPassword.getText().toString()));

                    MissionDATABASE.getInstance(MainActivity.this).missionDAO().createMember(newMember);
                    dialog.cancel();
                    startActivity(new Intent(MainActivity.this, LogoIntro.class));
                    finish();

                }
            }
        });
        dialog.show();
    }


}


