package com.example.projectcpe;

import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FunctionEditPassword extends AppCompatActivity {

    Button btSubmit;
    EditText etCurrent, etNewpass, etConfirm;

    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_edit_password);

        Init();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChange();
            }
        });
    }

    private void Init() {
        btSubmit = findViewById(R.id.ok);
        etCurrent = findViewById(R.id.etCurrent);
        etConfirm = findViewById(R.id.etConfirm);
        etNewpass = findViewById(R.id.etNewpass);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void checkChange(){
        String confirm = etConfirm.getText().toString().trim();
        String current = etCurrent.getText().toString().trim();
        String newpass = etNewpass.getText().toString().trim();

        if (current.isEmpty()) {
            etCurrent.setError("Current Password is required");
            etCurrent.requestFocus();
            return;
        }
        if (newpass.isEmpty()) {
            etNewpass.setError("New Password is required");
            etNewpass.requestFocus();
            return;
        }
        if (confirm.isEmpty()) {
            etConfirm.setError("Confirm Password is required");
            etConfirm.requestFocus();
            return;
        }

        if (checkPassword(Integer.parseInt(current))){

        }else{
                etCurrent.setError("Current Password Not The Same");
                etCurrent.requestFocus();
                return;

        }
        if (confirm.equals(newpass)) {
            SharedPreferences.Editor newPass = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE).edit();
            newPass.putInt("Pass", Integer.parseInt(etNewpass.getText().toString().trim()));
            newPass.commit();


            startActivity(new Intent(FunctionEditPassword.this, AdminPage.class));
            Toast.makeText(getApplicationContext(), "Your Password is Changed", Toast.LENGTH_SHORT).show();
        }else{
            etNewpass.setError("Password not the same");
            etNewpass.requestFocus();
            return;
        }
    }

    private boolean checkPassword(int putpassword){

        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
        int gettingPassword = 0;
        int gettedPassword  = getPassword.getInt("Pass", gettingPassword);
        boolean resault;
        if (putpassword == gettedPassword){
            resault = true;
        }else{
            resault = false;
        }
        return resault;

    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(FunctionEditPassword.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(FunctionEditPassword.this, MusicService.class));
        startService(new Intent(FunctionEditPassword.this, MusicService.class));
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
