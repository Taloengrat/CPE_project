package com.example.projectcpe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.HomePage;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Member;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class MemberAdapterBegin extends RecyclerView.Adapter<MemberAdapterBegin.MemberViewHolder>{

    Context mCtx;
    public static List<Member> memberListb;

    public MemberAdapterBegin(List<Member> c, Context ctx) {
        this.memberListb = c;
        this.mCtx = ctx;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list_begin, parent,false);
        return new MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        final Member member = memberListb.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(member.getProfile(), 0, member.getProfile().length);

        holder.name.setText(member.getName());
        holder.age.setText(String.valueOf(member.getAge()));
        holder.imUser.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return memberListb.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, age;
        ImageView imUser;
        ViewGroup layout;

        public MemberViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            layout = itemView.findViewById(R.id.layout);
            imUser =itemView.findViewById(R.id.imUser);


        }

        @Override
        public void onClick(View view) {

            int iddd = MissionDATABASE.getInstance(mCtx).missionDAO().getDesMember(memberListb.get(getAdapterPosition()).getId());
            Intent i = new Intent(mCtx, HomePage.class);
            i.putExtra("NAME", name.getText().toString());
            i.putExtra("AGE", age.getText().toString());
            i.putExtra("ID", iddd);
            mCtx.startActivity(i);
        }
    }
}
