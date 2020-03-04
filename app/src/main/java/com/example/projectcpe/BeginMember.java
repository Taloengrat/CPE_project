package com.example.projectcpe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectcpe.Adapter.MemberAdapterBegin;
import com.example.projectcpe.ViewModel.Member;
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
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

public class BeginMember extends AppCompatActivity {

    RecyclerView MemberRecyclerview;
    List<Member> missionsList;
    ImageView addMember;
    CardView menu;
    ImageView imMedium;


    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_member);

//        requestSignIn();

        Initia();


        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new ButtonServiceEffect(BeginMember.this).startEffect(); // Sound button effect
                VerifyAdmin();

            }
        });
    }

    private List<Member> lodeMember() {

        return MissionDATABASE.getInstance(this).missionDAO().getAllMember();

    }
    private void CreateMember() {
        final Dialog dialog = new Dialog(BeginMember.this);
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
                startService(new Intent(BeginMember.this, ButtonServiceEffect.class));
                final Dialog dialog = new Dialog(BeginMember.this);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_profile);
                dialog.setCancelable(true);

                View.OnClickListener Clickkk = new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        startService(new Intent(BeginMember.this, ButtonServiceEffect.class)); // Sound button effect
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
                startService(new Intent(BeginMember.this, ButtonServiceEffect.class)); // Sound button effect
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

                    MissionDATABASE.getInstance(BeginMember.this).missionDAO().createMember(newMember);
                    dialog.cancel();
                    loadData();


                } else {
                    Toast.makeText(getApplicationContext(), "กรุณาเลือกรูปโปรไฟล์", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void loadData() {
      if (lodeMember() == null){

          CreateMember();

      }else{
          RecyclerView.Adapter adapter = new MemberAdapterBegin(lodeMember(), this);
          MemberRecyclerview.setHasFixedSize(true);
          MemberRecyclerview.setLayoutManager(new LinearLayoutManager(this));
          MemberRecyclerview.setAdapter(adapter);
      }

    }

    private void Initia() {
        MemberRecyclerview = findViewById(R.id.MemberRecyclerView);
        addMember = findViewById(R.id.addmember);
        menu = findViewById(R.id.menu_begin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }


    public void VerifyAdmin() {

        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_admin);
        dialog.setCancelable(true);

        dialog.show();
        final EditText input = dialog.findViewById(R.id.etpassss);

        Button ok = dialog.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ButtonServiceEffect(BeginMember.this).startEffect(); // Sound button effect

                final String pass = (input.getText().toString());

                if (input.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (CheckPassword(Integer.parseInt(pass))) {
                    Toast.makeText(getApplicationContext(), "Password Correct :)", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    startActivity(new Intent(BeginMember.this, AdminPage.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(getApplicationContext(), "Password Invalid !!!", Toast.LENGTH_SHORT).show();
                }

                dialog.cancel();
            }


        });

    }

    public boolean CheckPassword(int putpassword) {


        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
        int gettingPassword = 0;
        int gettedPassword = getPassword.getInt("Pass", gettingPassword);
        boolean resault;
        if (putpassword == gettedPassword) {
            Toast.makeText(getApplicationContext(), "Password Verified", Toast.LENGTH_SHORT).show();
            resault = true;
        } else {
            resault = false;
        }
        return resault;

    }

    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                new ButtonServiceEffect(BeginMember.this).startEffect(); // Sound button effect
                 finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        new ButtonServiceEffect(BeginMember.this).startEffect(); // Sound button effect
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(BeginMember.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(BeginMember.this, MusicService.class));
        startService(new Intent(BeginMember.this, MusicService.class));
    }
}
