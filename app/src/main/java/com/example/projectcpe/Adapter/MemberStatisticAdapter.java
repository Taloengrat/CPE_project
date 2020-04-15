package com.example.projectcpe.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.MemberStatic;

import java.util.List;

public class MemberStatisticAdapter extends RecyclerView.Adapter<MemberStatisticAdapter.MemberStaticViewHolder> {

    List<MemberStatic> memberStaticList;
    Activity activity;
    public MemberStatisticAdapter(List<MemberStatic> memberStaticList, Activity activity) {
        this.memberStaticList = memberStaticList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MemberStatisticAdapter.MemberStaticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_statistic, parent,false);
        return new MemberStaticViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberStatisticAdapter.MemberStaticViewHolder holder, int position) {

        MemberStatic memberStatic = memberStaticList.get(position);

        holder.name.setText("Mission name : "+memberStatic.getMissionName());
        holder.age.setText("For age : "+memberStatic.getForAge());
        holder.score.setText("Score : "+String.valueOf(memberStatic.getScore()));


        switch (memberStatic.getNumStar()){

            case 1 :
                holder.halfstar1.setVisibility(View.VISIBLE);
                break;
            case 2 :

                holder.star1.setVisibility(View.VISIBLE);

                break;
            case 3 :

                holder.star1.setVisibility(View.VISIBLE);
                holder.halfstar2.setVisibility(View.VISIBLE);
                break;
            case 4 :
                holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                break;
            case 5 : holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                holder.halfstar3.setVisibility(View.VISIBLE);
                break;
            case 6 :holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                holder.star3.setVisibility(View.VISIBLE);
                break;
            case 7 : holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                holder.star3.setVisibility(View.VISIBLE);
                holder.halfstar4.setVisibility(View.VISIBLE);
                break;
            case 8 : holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                holder.star3.setVisibility(View.VISIBLE);
                holder.star4.setVisibility(View.VISIBLE);
                break;
            case 9 : holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                holder.star3.setVisibility(View.VISIBLE);
                holder.star4.setVisibility(View.VISIBLE);
                holder.halfstar5.setVisibility(View.VISIBLE);
                break;
            case 10 :  holder.star1.setVisibility(View.VISIBLE);
                holder.star2.setVisibility(View.VISIBLE);
                holder.star3.setVisibility(View.VISIBLE);
                holder.star4.setVisibility(View.VISIBLE);
                holder.star5.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return memberStaticList.size();
    }


    public class MemberStaticViewHolder extends RecyclerView.ViewHolder{

        TextView name, age, score;
        ImageView star1, star2, star3, star4, star5, halfstar1, halfstar2, halfstar3, halfstar4, halfstar5 ;
        public MemberStaticViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.namem);
            age = itemView.findViewById(R.id.agem);
            score = itemView.findViewById(R.id.score);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
            halfstar1 = itemView.findViewById(R.id.star);
            halfstar2 = itemView.findViewById(R.id.star6);
            halfstar3 = itemView.findViewById(R.id.star7);
            halfstar4 = itemView.findViewById(R.id.star8);
            halfstar5 = itemView.findViewById(R.id.star9);


        }


    }

}
