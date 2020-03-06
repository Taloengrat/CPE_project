package com.example.projectcpe.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;


public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.MissionViewHolder> {



    //this context we will use to inflate the layout
    private Context mCtx;
    //we are storing all the products in a list
    private List<Mission> missionList;
    private OnCustomerItemClick onCustomerItemClick;
    private OnCustomerItemLongClick onCustomerItemLongClick;



    public MissionAdapter(List<Mission> c, Context ctx) {
        this.missionList = c;
        this.mCtx = ctx;
        this.onCustomerItemClick = (OnCustomerItemClick) ctx;
//        this.onCustomerItemLongClick = (OnCustomerItemLongClick) ctx;
    }



    public MissionAdapter(List<Mission> missionList) {
        this.missionList = missionList;
    }


    @NonNull
    @Override
    public MissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mission, parent,false);
        return new MissionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MissionViewHolder missionViewHolder, int position) {
//getting the product of the specified position
        Mission mission = missionList.get(position);

        //binding the data with the viewholder views
        missionViewHolder.tvMisName.setText(mission.getMissionName());
        missionViewHolder.tvMisAge.setText(String.valueOf(mission.getAge()));
        missionViewHolder.number.setText("mission \n"+ ++position);

    }

    @Override
    public int getItemCount() {
        return missionList.size();
    }





    public class MissionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        TextView tvMisName, tvMisAge, number;
        ImageView delete;
        ViewGroup layout;

        public MissionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvMisName = itemView.findViewById(R.id.textMisssionName);
            tvMisAge = itemView.findViewById(R.id.textAge);
            delete = itemView.findViewById(R.id.delete);
            layout = itemView.findViewById(R.id.layoutcard);
            number = itemView.findViewById(R.id.number);

        }

        @Override
        public void onClick(View v) {


          int iddd = MissionDATABASE.getInstance(mCtx).missionDAO().getDesMission(missionList.get(getAdapterPosition()).getIdMission());

            onCustomerItemClick.onCustomerClick(iddd, 0, missionList.get(getAdapterPosition()));


//          Toast.makeText(mCtx, "Mission ID : "+String.valueOf(iddd), Toast.LENGTH_LONG).show();


        }
///

        @Override
        public boolean onLongClick(View v) {
            int iddd = MissionDATABASE.getInstance(mCtx).missionDAO().getDesMission(missionList.get(getAdapterPosition()).getIdMission());
           onCustomerItemLongClick.onCustomerLongClick(iddd, missionList.get(getAdapterPosition()));
            return false;
        }
    }

    public interface OnCustomerItemClick{
        void onCustomerClick(int pos, int result, Mission missionList);

    }


    public interface OnCustomerItemLongClick{
        void onCustomerLongClick(int pos, Mission missionList);

    }

}
