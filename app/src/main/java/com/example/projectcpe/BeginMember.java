package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectcpe.Adapter.MemberAdapter;
import com.example.projectcpe.Adapter.MemberAdapterBegin;
import com.example.projectcpe.CreateMission.MissionCreate;
import com.example.projectcpe.CreateMission.MissionDelete;
import com.example.projectcpe.CreateMission.MissionExport;
import com.example.projectcpe.CreateMission.MissionImport;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class BeginMember extends AppCompatActivity {

    RecyclerView MemberRecyclerview;
    List<Member> missionsList;
    ImageView addMember;
    int numPic;
    ImageView menu;

    BitmapDrawable imBitmap;

    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_member);

        Initia();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyAdmin();

            }
        });
    }

    private List<Member> lodeMember () {

        return MissionDATABASE.getInstance(this).missionDAO().getAllMember();

    }

    private void loadData () {

        RecyclerView.Adapter adapter = new MemberAdapterBegin(lodeMember(), this);
        MemberRecyclerview.setHasFixedSize(true);
        MemberRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        MemberRecyclerview.setAdapter(adapter);
    }

    private void Initia () {
        MemberRecyclerview = findViewById(R.id.MemberRecyclerView);
        addMember = findViewById(R.id.addmember);
        menu = findViewById(R.id.menu_begin);
    }

    @Override
    protected void onStart () {
        super.onStart();
        loadData();
    }


    public void VerifyAdmin(){

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
                final String pass= (input.getText().toString());

                if (input.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your pass word", Toast.LENGTH_SHORT).show();
                }else if (CheckPassword(Integer.parseInt(pass))) {
                    Toast.makeText(getApplicationContext(), "Password Correct :)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BeginMember.this, AdminPage.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Password Invalid !!!", Toast.LENGTH_SHORT).show();
                }
            }





        });

    }
    public boolean CheckPassword(int putpassword){


        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
        int gettingPassword = 0;
        int gettedPassword  = getPassword.getInt("Pass", gettingPassword);
        boolean resault;
        if (putpassword == gettedPassword){
            Toast.makeText(getApplicationContext(), "Password Verified",Toast.LENGTH_SHORT).show();
            resault = true;
        }else{
            resault = false;
        }
        return resault;

    }


}
