package com.example.projectcpe.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.projectcpe.ButtonServiceEffect;
import com.example.projectcpe.HomePage;
import com.example.projectcpe.MainActivity;
import com.example.projectcpe.R;
import com.example.projectcpe.StatisticMember;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MemberAdapterBegin extends RecyclerView.Adapter<MemberAdapterBegin.MemberViewHolder> {

    Activity activity;
    public static List<Member> memberListb, User;
    public static final String MY_PRE_PASSWORD_ADMIN = "com.example.projectcpe.passwordasmin";

    public MemberAdapterBegin(List<Member> c, Activity activity) {
        this.memberListb = c;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list_begin, parent, false);
        return new MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, final int position) {
        final Member member = memberListb.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(member.getProfile(), 0, member.getProfile().length);

        holder.name.setText("name : " + member.getName());
        holder.age.setText("age : " + member.getAge());
        holder.imUser.setImageBitmap(bitmap);


        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ButtonServiceEffect(activity).startEffect(); // Sound button effect
                Intent i = new Intent(activity, StatisticMember.class);
                i.putExtra("id", member.getId());
                activity.startActivity(i);
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


    }

    @Override
    public int getItemCount() {
        return memberListb.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, age;
        ImageView imUser, option;
        ViewGroup layout;

        public MemberViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            layout = itemView.findViewById(R.id.layout);
            imUser = itemView.findViewById(R.id.imUser);
            option = itemView.findViewById(R.id.option);


        }


        @Override
        public void onClick(View view) {


            new ButtonServiceEffect(activity).startEffect(); // Sound button effect

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_custom);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.verify_member);
            dialog.setCancelable(true);

            final EditText _etPassword = dialog.findViewById(R.id.etpassword);
            Button btSubmit = dialog.findViewById(R.id.okverify);

            final int iddd = MissionDATABASE.getInstance(activity).missionDAO().getDesMember(memberListb.get(getAdapterPosition()).getId());

            User = getData(iddd);

            final SharedPreferences getPassword = activity.getSharedPreferences(MY_PRE_PASSWORD_ADMIN, MODE_PRIVATE);
            int gettingPassword = 0;
            final int gettedPassword = getPassword.getInt("Pass", gettingPassword);

            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new ButtonServiceEffect(activity).startEffect(); // Sound button effect

                    if (_etPassword.getText().toString().equals(String.valueOf(User.get(0).getPassword()))
                            || _etPassword.getText().toString().equals(String.valueOf(gettedPassword))) {
                        Intent i = new Intent(activity, HomePage.class);
                        i.putExtra("NAME", name.getText().toString());
                        i.putExtra("AGE", age.getText().toString());
                        i.putExtra("ID", iddd);
                        activity.startActivity(i);
                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        activity.finish();
                        dialog.cancel();

                    } else {

                        Toast.makeText(activity, String.valueOf(User.get(0).getPassword()), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            dialog.show();


        }

        private List<Member> getData(int id) {
            return MissionDATABASE.getInstance(activity).missionDAO().getAllinfoOfMember(id);
        }
    }
}
