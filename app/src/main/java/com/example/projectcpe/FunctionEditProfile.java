package com.example.projectcpe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.Adapter.MemberAdapter;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class FunctionEditProfile extends AppCompatActivity {


    RecyclerView MemberRecyclerview;
    List<Member> missionsList;
    ImageView addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_edit_profile);

        Initia();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(FunctionEditProfile.this);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.frameline);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.addmember_dialog);
                dialog.setCancelable(true);
                //

                final EditText _etName = dialog.findViewById(R.id.etname);
                final EditText _etAge = dialog.findViewById(R.id.etage);

                Button btSubmit = (Button) dialog.findViewById(R.id.ok);
                btSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (_etName.getText().toString().isEmpty()) {
                            _etName.setError("Name is required");
                            _etName.requestFocus();
                            return;
                        } else {

                            Member newMember = new Member(_etName.getText().toString(), _etAge.getText().toString());

                            MissionDATABASE.getInstance(FunctionEditProfile.this).missionDAO().createMember(newMember);
dialog.cancel();
                            recreate();

                        }
                    }
                });
                dialog.show();
            }
        });

    }

        private void loadData () {

            RecyclerView.Adapter adapter = new MemberAdapter(lodeMember(), this);
            MemberRecyclerview.setHasFixedSize(true);
            MemberRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            MemberRecyclerview.setAdapter(adapter);
        }

        private List<Member> lodeMember () {

            return MissionDATABASE.getInstance(this).missionDAO().getAllMember();

        }

        private void Initia () {
            MemberRecyclerview = findViewById(R.id.MemberRecyclerView);
            addMember = findViewById(R.id.addmember);
        }

        @Override
        protected void onStart () {
            super.onStart();
            loadData();
        }



}