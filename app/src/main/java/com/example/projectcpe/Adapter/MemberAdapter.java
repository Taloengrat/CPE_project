package com.example.projectcpe.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.BeginMember;
import com.example.projectcpe.FunctionEditProfile;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MissionDATABASE;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    Context mCtx;
    public static List<Member> memberListb;
    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";

    ///////New  22fesdwehnrt67tlkf


    public MemberAdapter(List<Member> c, Context ctx) {
        this.memberListb = c;
        this.mCtx = ctx;
    }

    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_member, parent,false);
        return new MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MemberAdapter.MemberViewHolder holder, int position) {


        final Member member = memberListb.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(member.getProfile(), 0, member.getProfile().length);

        holder.name.setText(member.getName());
        holder.age.setText(String.valueOf(member.getAge()));
        holder.imUser.setImageBitmap(bitmap);

        holder.imUser.setBackgroundResource(R.drawable.elevetorcircle);

//        Toast.makeText(mCtx, String.valueOf(member.getProfile()), Toast.LENGTH_SHORT).show();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mCtx);
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
                            Snackbar.make(holder.layout, "Please enter your pass word", Snackbar.LENGTH_SHORT).show();
                        }else if (CheckPassword(Integer.parseInt(pass))) {
                            Snackbar.make(holder.layout, "Password Correct :)", Snackbar.LENGTH_SHORT).show();
                            ChooseWay();
                        } else {
                            Snackbar.make(holder.layout, "Password Invalid !!!", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    private boolean CheckPassword(int parseInt) {

                        SharedPreferences getPassword = mCtx.getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
                        int gettingPassword = 0;
                        int gettedPassword  = getPassword.getInt("Pass", gettingPassword);
                        boolean resault;
                        if (parseInt == gettedPassword){
                            Toast.makeText(mCtx, "Password Verified",Toast.LENGTH_SHORT).show();
                            resault = true;
                        }else{
                            resault = false;
                        }
                        return resault;
                    }

                    private void ChooseWay() {
                        MissionDATABASE.getInstance(mCtx).missionDAO().deleteMember(member);

                        MissionDATABASE.getInstance(mCtx).missionDAO().deleteStatisticByMemberId(member.getId());

                        MissionDATABASE.getInstance(mCtx).missionDAO().deleteByMemberStaticId(member.getId());



                        ((FunctionEditProfile)mCtx).recreate();

                        dialog.cancel();
                    }
                });



            }
        });



    }

    @Override
    public int getItemCount() {
        return memberListb.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{

          TextView name, age;
          ImageView imUser, delete;
          ViewGroup layout;

        public MemberViewHolder(@NonNull final View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            delete = itemView.findViewById(R.id.delete);
            layout = itemView.findViewById(R.id.layout);
            imUser =itemView.findViewById(R.id.imUser);


        }


    }

}
