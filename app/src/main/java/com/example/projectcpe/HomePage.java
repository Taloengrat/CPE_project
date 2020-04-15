package com.example.projectcpe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.PlayingMode.DetailMission;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity implements  MissionAdapter.OnCustomerItemClick{

  private DrawerLayout drawer;
    private String m_Text = "";
    TextView txName, txAge;
    ImageView imProfile;
    ImageView imdelete;
    RecyclerView recyclerView;
    MissionAdapter missionAdapter;
    List<Mission> missionsList;
    int id;
    private byte memberData;
    List<Member> member;

    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        createInstance();

        missionsList = new ArrayList<>();

        recyclerView =findViewById(R.id.recyclerView);

//        missionsList.add(new Mission("TestSystem", 0));


       loadData();






        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("ID");
        String NameUser = bundle.getString("NAME");
        String Age = bundle.getString("AGE");

        member = getData(id);

        Bitmap bitmap = BitmapFactory.decodeByteArray(member.get(0).getProfile(), 0, member.get(0).getProfile().length);


                txName.setText(NameUser);
                txAge.setText(Age);
                imProfile.setImageBitmap(bitmap);



   }



    private List<Member> getData(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMember(id);
    }

    private List<Mission> getMissionList() {
        return MissionDATABASE.getInstance(this).missionDAO().getAllMission();
    }


    public void createInstance(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txAge = findViewById(R.id.txAge);
        txName = findViewById(R.id.name);
        imProfile = findViewById(R.id.imProfile);
        drawer = findViewById(R.id.drawLayout);
        imdelete = findViewById(R.id.delete);


    }

//    public boolean CheckPassword(int putpassword){
//
//
//        SharedPreferences getPassword = getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
//        int gettingPassword = 0;
//        int gettedPassword  = getPassword.getInt("Pass", gettingPassword);
//        boolean resault;
//        if (putpassword == gettedPassword){
//            Toast.makeText(getApplicationContext(), "Password Verified",Toast.LENGTH_SHORT).show();
//            resault = true;
//        }else{
//            resault = false;
//        }
//        return resault;
//
//    }


    public void loadData(){
        RecyclerView.Adapter adapter = new MissionAdapter(getMissionList(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onCustomerClick(int id,int result, Mission mission) {

        new ButtonServiceEffect(HomePage.this).startEffect(); // Sound button effect

    Intent i = new Intent(HomePage.this, DetailMission.class);
    i.putExtra("MissionId", id);
    i.putExtra("MemberId", this.id);
    startActivity(i);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }



    @Override
    protected void onDestroy() {
        MissionDATABASE.desInstance();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout Accout");
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to logout?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(HomePage.this, BeginMember.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        new ButtonServiceEffect(HomePage.this).startEffect(); // Sound button effect
        dialog.show();
        // your code.

    }

    @Override
    public boolean onSupportNavigateUp() {

     onBackPressed();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(HomePage.this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(HomePage.this, MusicService.class));
        startService(new Intent(HomePage.this, MusicService.class));
    }


}
