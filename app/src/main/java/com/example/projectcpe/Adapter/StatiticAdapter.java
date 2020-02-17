package com.example.projectcpe.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.Static;
import com.example.projectcpe.ViewModel.Step;


import java.util.List;

public class StatiticAdapter extends RecyclerView.Adapter<StatiticAdapter.StatisticViewHolder>{

    public static List<Static> staticList;
    public Activity activitySta;

    public StatiticAdapter(List<Static> list, Activity activity) {
        staticList = list;
        this.activitySta = activity;
//        this.onCustomerItemClick = (MissionAdapter.OnCustomerItemClick) ctx;
    }

    @NonNull
    @Override
    public StatiticAdapter.StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_statistic, parent,false);
        return new StatisticViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull StatiticAdapter.StatisticViewHolder holder, int position) {

        Static statistic = staticList.get(position);

        Bitmap bitmap = BitmapFactory.decodeByteArray(statistic.getProfile(), 0, statistic.getProfile().length);


        switch (position){
            case 0 :
                holder.range.setVisibility(View.VISIBLE);
                holder.range.setImageResource(R.drawable.first);

                break;
            case 1 :
                holder.range.setVisibility(View.VISIBLE);
                holder.range.setImageResource(R.drawable.second);

                break;
            case 2 :
                holder.range.setVisibility(View.VISIBLE);
                holder.range.setImageResource(R.drawable.third);
                break;

                default:
                    holder.cardView.setVisibility(View.VISIBLE);
                    holder.textRange.setText(String.valueOf(position+1));
        }

        holder.improfile.setImageBitmap(bitmap);
        holder.txName.setText(statistic.getName());
        holder.txScore.setText("Score : "+statistic.getScore());
        holder.txAge.setText(String.valueOf(statistic.getAge()));

        switch (statistic.getNumStar()){

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
        return staticList.size();
    }

    public class StatisticViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txName, txAge, txScore, textRange;
        CardView cardView,home;
        ImageView improfile, star1, star2, star3, star4, star5,range,halfstar1, halfstar2, halfstar3, halfstar4, halfstar5;
        public StatisticViewHolder(@NonNull View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            txName = itemView.findViewById(R.id.txName);
            txAge = itemView.findViewById(R.id.txAge);
            txScore = itemView.findViewById(R.id.txScore);
            improfile = itemView.findViewById(R.id.imPro);
            cardView = itemView.findViewById(R.id.bgtext);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
            home = itemView.findViewById(R.id.homebg);

            halfstar1 = itemView.findViewById(R.id.halfstar1);
            halfstar2 = itemView.findViewById(R.id.halfstar2);
            halfstar3 = itemView.findViewById(R.id.halfstar3);
            halfstar4 = itemView.findViewById(R.id.halfstar4);
            halfstar5 = itemView.findViewById(R.id.halfstar5);
            range = itemView.findViewById(R.id.imrange);
            textRange = itemView.findViewById(R.id.textRange);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(activitySta, String.valueOf(staticList.get(0).getSubid()),Toast.LENGTH_SHORT).show();
        }
    }
}
