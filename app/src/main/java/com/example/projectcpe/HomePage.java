package com.example.projectcpe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.CreateMission.MissionCreate;
import com.example.projectcpe.CreateMission.MissionDelete;
import com.example.projectcpe.CreateMission.MissionExport;
import com.example.projectcpe.CreateMission.MissionImport;
import com.example.projectcpe.PlayingMode.DetailMission;
import com.example.projectcpe.TestSystem.TestSystem;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MissionAdapter.OnCustomerItemClick{

  private DrawerLayout drawer;
    private String m_Text = "";
    TextView txName, txAge;
    ImageView imProfile;
    ImageView imdelete;
    RecyclerView recyclerView;
    MissionAdapter missionAdapter;
    List<Mission> missionsList;
    Button testSystem;
    int count;
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


        testSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), TestSystem.class));
            }
        });
       loadData();


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("ID");
        String NameUser = bundle.getString("NAME");
        String Age = bundle.getString("AGE");

        member = getData(id);

        Toast.makeText(getApplicationContext(), String.valueOf(member.get(0).getProfile()), Toast.LENGTH_SHORT).show();
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

    public void detailMission(View view){

        Intent i = new Intent(HomePage.this, DetailMission.class);
//        i.putExtra("id",view.getId().)
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.member : VerifyAdmin("profile");
                break;
            case R.id.admin :
                startActivity(new Intent(this, FunctionEditPassword.class));
                break;
            case R.id.Create : VerifyAdmin("create");
                break;
            case R.id.Delete : VerifyAdmin("delete");
                break;
            case R.id.Import : VerifyAdmin("import");
                break;
            case R.id.Export : VerifyAdmin("export");
                break;

        }
        return true;
    }

    public void VerifyAdmin(final String Way){

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
                    ChooseWay();
                } else {
                    Toast.makeText(getApplicationContext(), "Password Invalid !!!", Toast.LENGTH_SHORT).show();
                }
            }


            private void ChooseWay() {
                switch (Way) {
                    case "create" : startActivity(new Intent(HomePage.this, MissionCreate.class)); break;
                    case "delete" : startActivity(new Intent(HomePage.this, MissionDelete.class)); break;
                    case "import" : startActivity(new Intent(HomePage.this, MissionImport.class)); break;
                    case "export" : startActivity(new Intent(HomePage.this, MissionExport.class)); break;
                    case "profile" : startActivity(new Intent(HomePage.this, FunctionEditProfile.class));break;
                }
            }


        });



    }


    public void createInstance(){
        testSystem = findViewById(R.id.textsystem);
        txAge = findViewById(R.id.txAge);
        txName = findViewById(R.id.txName);
        imProfile = findViewById(R.id.imProfile);
        drawer = findViewById(R.id.drawLayout);
        imdelete = findViewById(R.id.delete);


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
    public void onCustomerClick(int id, int result, Mission missionlist) {
//        switch (result){
//            case 0 : startActivity(new Intent(getApplicationContext(), DetailMission.class));
//
//            case 1 : loadData();
//                break;
//            case 2 : startActivity(new Intent(getApplicationContext(), DetailMission.class));
//
//                break;
//        }

    Intent i = new Intent(HomePage.this, DetailMission.class);
    i.putExtra("MissionId", id);
    startActivity(i);

    }



    @Override
    protected void onDestroy() {
        MissionDATABASE.desInstance();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // your code.
        startActivity(new Intent(HomePage.this, BeginMember.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
