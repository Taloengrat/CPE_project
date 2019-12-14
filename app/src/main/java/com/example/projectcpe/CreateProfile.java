package com.example.projectcpe;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.projectcpe.CreateMission.MissionCreate;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.projectcpe.Database.REPOSITORY;

import com.example.projectcpe.Local.DATABASE;
import com.example.projectcpe.Local.DATABASEsource;

import java.io.ByteArrayOutputStream;

import io.reactivex.disposables.CompositeDisposable;

public class CreateProfile extends AppCompatActivity {
Button _btSave;
EditText _etName1,_etName2,_etName4,_etName3,   _etAge1, _etAge2, _etAge3, _etAge4;

    ImageView imMedium;


    CompositeDisposable compositeDisposable;
    private REPOSITORY repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        imMedium = findViewById(R.id.pro6);
        _btSave =  findViewById(R.id.btSave);
        _etName1 = findViewById(R.id.member1);
        _etName2 = findViewById(R.id.member2);
        _etName3 = findViewById(R.id.member3);
        _etName4 = findViewById(R.id.member4);

        _etAge1 = findViewById(R.id.etAge1);
        _etAge2 = findViewById(R.id.etAge2);
        _etAge3 = findViewById(R.id.etAge3);
        _etAge4 = findViewById(R.id.etAge4);

        ///// Initialize
        compositeDisposable = new CompositeDisposable();

        /// Database
        DATABASE adminDatabase = DATABASE.getInstance(this); //// Create Database
        repository = REPOSITORY.getInstance(DATABASEsource.getInstance(adminDatabase.dao()));

        _btSave.setOnClickListener(new View.OnClickListener() {

            String name1 = _etName1.getText().toString();
            String name2 = _etName2.getText().toString();
            String name3 = _etName3.getText().toString();
            String name4 = _etName4.getText().toString();

            String age1 = _etAge1.getText().toString();
            String age2 = _etAge2.getText().toString();
            String age3 = _etAge3.getText().toString();
            String age4 = _etAge4.getText().toString();




            @Override
            public void onClick(View v) {
                //// Add Member

                if (name1.isEmpty()){
                    Snackbar.make(_btSave, "Enter your SomeMember!", Snackbar.LENGTH_SHORT)
                            .show();
                }


                if(_etName1.getText().toString().isEmpty() || _etAge1.getText().toString().isEmpty()){
                    Snackbar.make(_etName1, "Plese enter your password for Admin !", Snackbar.LENGTH_SHORT).show();
                }else {
                    Bitmap bitmap = ((BitmapDrawable) imMedium.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();

                    Member create = new Member(_etName1.getText().toString(), _etAge1.getText().toString(),null);

                    MissionDATABASE.getInstance(CreateProfile.this).missionDAO().createMember(create);


                    startActivity(new Intent(CreateProfile.this, FunctionEditProfile.class));
                }


            }
        });


    }



}
