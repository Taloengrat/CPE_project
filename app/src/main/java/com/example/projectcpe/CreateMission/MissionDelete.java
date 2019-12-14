package com.example.projectcpe.CreateMission;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.ArrayList;
import java.util.List;

public class MissionDelete extends AppCompatActivity implements MissionAdapter.OnCustomerItemClick{
    RecyclerView recyclerView;
    List<Mission> missionsList;
    private MissionAdapter.OnCustomerItemClick onCustomerItemClick;
    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_delete);
        missionsList = new ArrayList<>();
        recyclerView =findViewById(R.id.recyclerViewdelete);

        loadData();



    }



    private void loadData() {
        RecyclerView.Adapter adapter = new MissionAdapter( getMissionList(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }


    private List<Mission> getMissionList() {
        return MissionDATABASE.getInstance(getApplicationContext()).missionDAO().getAllMission();
    }



    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }


    @Override
    public void onCustomerClick(final int pos, int result, final Mission missionlist) {
switch (result){

    case 0 : new AlertDialog.Builder(MissionDelete.this)
            .setTitle("คุณต้องการทำอะไร?")
            .setItems(new String[]{"ลบข้อมูลลูกค้า", "แก้ไขข้อมูลลูกค้า"},
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:
                                    MissionDATABASE.getInstance(MissionDelete.this).missionDAO().delete(missionlist);
                                    missionsList.remove(missionlist);
                                    recreate();
                                    break;
                                case 1:

                                    break;
                            }
                        }
                    }).show();

}
    }



//    @Override
//    public void onCustomerClick(int adapterPosition) {
//
//    }
}
