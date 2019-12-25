package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.projectcpe.Adapter.MemberAdapter;
import com.example.projectcpe.Adapter.MemberAdapterBegin;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_member);

        Initia();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BeginMember.this, AdminPage.class));
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


}
