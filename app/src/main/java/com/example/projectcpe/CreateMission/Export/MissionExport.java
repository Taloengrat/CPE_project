package com.example.projectcpe.CreateMission.Export;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.projectcpe.CreateMission.Import.ImportOnDevice;
import com.example.projectcpe.CreateMission.Import.ImportOnGoogledrive;
import com.example.projectcpe.CreateMission.Import.MissionImport;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Collections;

public class MissionExport extends AppCompatActivity {


    private static final int RC_SIGN_IN = 48;
    public EditText _etUsername, _etPassword;
    ProgressDialog progressDialog;
     FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_export);


        BindVariable();

        mAuth = FirebaseAuth.getInstance();


        user = mAuth.getCurrentUser();





    }




    private void BindVariable(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardDevice :
                MediaPlayer.create(getApplicationContext(), R.raw.button1).start();
//                StoreCSV.storeCSV("test.csv");
                startActivity(new Intent(MissionExport.this, ExportOnDevice.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.cardGoogledrive :
                MediaPlayer.create(getApplicationContext(), R.raw.button1).start();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // [END config_signin]

                googleSignInClient = GoogleSignIn.getClient(this, gso);

                signIn();



            break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(MissionExport.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MissionExport.this, MusicService.class));
        startService(new Intent(MissionExport.this, MusicService.class));
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("accoutId", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        progressDialog = ProgressDialog.show(MissionExport.this, "เข้าสู่ระบบ", "กำลังเข้าสู่ระบบ...", true, false);
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("result", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("result", "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.exxx), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        progressDialog.dismiss();
        if (user != null) {

            Intent i = new Intent(MissionExport.this, ExportOnGoogleDrive.class);
            i.putExtra("username", user.getDisplayName());
            i.putExtra("Uid",user.getUid());

            startActivity(i);
            finish();



        } else {

            signIn();
            Toast.makeText(getApplicationContext(), "User result : null",Toast.LENGTH_LONG).show();

        }
    }
}
