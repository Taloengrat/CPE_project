package com.example.projectcpe.CreateMission.Import;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.projectcpe.CreateMission.Export.ExportOnGoogleDrive;
import com.example.projectcpe.CreateMission.Export.MissionExport;
import com.example.projectcpe.MusicService;
import com.example.projectcpe.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.File;

public class MissionImport extends AppCompatActivity {


    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 48;
    GoogleSignInClient googleSignInClient;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_import);

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
//                StoreCSV.storeCSV("test.csv");
                startActivity(new Intent(MissionImport.this, ImportOnDevice.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.cardGoogledrive :
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

        stopService(new Intent(MissionImport.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(MissionImport.this, MusicService.class));
        startService(new Intent(MissionImport.this, MusicService.class));
    }

    private void updateUI(FirebaseUser user) {

        progressDialog.dismiss();
        if (user != null) {

            Intent i = new Intent(MissionImport.this, ImportOnGoogledrive.class);
            i.putExtra("username", user.getDisplayName());
            i.putExtra("Uid",user.getUid());

            startActivity(i);
            finish();



        } else {

            Toast.makeText(getApplicationContext(), "User result : null",Toast.LENGTH_LONG).show();

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("accoutId", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        progressDialog = ProgressDialog.show(MissionImport.this, "เข้าสู่ระบบ", "กำลังเข้าสู่ระบบ...", true, false);
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

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
