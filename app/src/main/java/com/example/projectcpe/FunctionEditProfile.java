package com.example.projectcpe;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.Adapter.MemberAdapter;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class FunctionEditProfile extends AppCompatActivity {


    RecyclerView MemberRecyclerview;
    List<Member> missionsList;
    ImageView addMember;
    int numPic;
    ImageView imMedium;

    BitmapDrawable imBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_edit_profile);

        Initia();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(FunctionEditProfile.this);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.addmember_dialog);
                dialog.setCancelable(true);
                //

                final EditText _etName = dialog.findViewById(R.id.etname);
                final EditText _etAge = dialog.findViewById(R.id.checkbox2);
                final EditText _etPassword = dialog.findViewById(R.id.checkbox3);
                final ImageView profile = dialog.findViewById(R.id.head);

                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(FunctionEditProfile.this);
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
                        if (_etName.getText().toString().isEmpty()) {
                            _etName.setError("Name is required");
                            _etName.requestFocus();
                            return;
                        } else if ((_etName.length() > 13)) {

                            _etName.setError("Length name is 13");
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

                            MissionDATABASE.getInstance(FunctionEditProfile.this).missionDAO().createMember(newMember);
                            dialog.cancel();
                            recreate();

                        } else {
                            Toast.makeText(getApplicationContext(), "กรุณาเลือกรูปโปรไฟล์", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });


    }

    private void loadData() {

        RecyclerView.Adapter adapter = new MemberAdapter(lodeMember(), this);
        MemberRecyclerview.setHasFixedSize(true);
        MemberRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        MemberRecyclerview.setAdapter(adapter);
    }

    private List<Member> lodeMember() {

        return MissionDATABASE.getInstance(this).missionDAO().getAllMember();

    }

    private void Initia() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MemberRecyclerview = findViewById(R.id.MemberRecyclerView);
        addMember = findViewById(R.id.addmember);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(FunctionEditProfile.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(FunctionEditProfile.this, MusicService.class));
        startService(new Intent(FunctionEditProfile.this, MusicService.class));
    }

}