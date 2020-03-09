package com.example.projectcpe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class MissionDeleteImport extends RecyclerView.Adapter<MissionDeleteImport.MissionDeleteImportViewHolder> {

    private Context mCtx;
    //we are storing all the products in a list
    private List<Mission> missionList;
     private OnCustomerItemClick onCustomerItemClick;
    private OnCustomerItemLongClick onCustomerItemLongClick;

    public MissionDeleteImport(List<Mission> c, Context ctx) {
        this.missionList = c;
        this.mCtx = ctx;
        this.onCustomerItemClick = (OnCustomerItemClick) ctx;
        this.onCustomerItemLongClick = (OnCustomerItemLongClick) ctx;
    }

    @NonNull
    @Override
    public MissionDeleteImportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mission, parent,false);
        return new MissionDeleteImportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionDeleteImportViewHolder holder, int position) {
        Mission mission = missionList.get(position);

        //binding the data with the viewholder views
        holder.tvMisName.setText(mission.getMissionName());
        holder.tvMisAge.setText(String.valueOf(mission.getAge()));
        holder.number.setText("mission \n"+ ++position);
    }

    @Override
    public int getItemCount() {
         return missionList.size();
    }

    public class MissionDeleteImportViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        TextView tvMisName, tvMisAge, number;
        ImageView delete;
        ViewGroup layout;

        public MissionDeleteImportViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

            tvMisName = itemView.findViewById(R.id.textMisssionName);
            tvMisAge = itemView.findViewById(R.id.textAge);
            delete = itemView.findViewById(R.id.delete);
            layout = itemView.findViewById(R.id.layoutcard);
            number = itemView.findViewById(R.id.number);

        }
        @Override
        public boolean onLongClick(View v) {
            int iddd = MissionDATABASE.getInstance(mCtx).missionDAO().getDesMission(missionList.get(getAdapterPosition()).getIdMission());
            onCustomerItemLongClick.onCustomerLongClick(iddd, missionList.get(getAdapterPosition()));
            return false;
        }

        @Override
        public void onClick(View v) {

            int iddd = MissionDATABASE.getInstance(mCtx).missionDAO().getDesMission(missionList.get(getAdapterPosition()).getIdMission());

            onCustomerItemClick.onCustomerClick(iddd, 0, missionList.get(getAdapterPosition()));

        }
    }

    public interface OnCustomerItemClick{
        void onCustomerClick(int pos, int result, Mission missionList);

    }

    public interface OnCustomerItemLongClick{
        void onCustomerLongClick(int pos, Mission missionList);

    }
}
