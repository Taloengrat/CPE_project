package com.example.projectcpe;


import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    ImageView _logoApp;
    EditText _etPass;
    Button _btBegin;


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

                            startActivity(new Intent(MainActivity.this, CreateProfile.class));
                        }
                        }
                });
            }


        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
        int gettingPassword = 0;
        int gettedPassword  = getPassword.getInt("Pass", gettingPassword);
        if (gettedPassword == 0){

        }else{
            startActivity(new Intent(MainActivity.this, FunctionEditProfile.class));
            finish();
        }
    }
}


