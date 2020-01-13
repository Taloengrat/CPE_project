package com.example.projectcpe.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Csv;
import com.example.projectcpe.ViewModel.Mission;

import java.util.List;

public class CsvImportAdapter extends RecyclerView.Adapter<CsvImportAdapter.CsvViewHolder>{

    private List<Csv> csvList;
    private Activity activity;

    public CsvImportAdapter(List<Csv> list, Activity activity) {
        this.csvList = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CsvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_import, parent,false);
        return new CsvViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CsvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return csvList.size();
    }

    public class CsvViewHolder extends RecyclerView.ViewHolder {

        TextView txName, txDate;
        public CsvViewHolder(@NonNull View itemView) {
            super(itemView);

            txName = itemView.findViewById(R.id.csvName);
            txDate = itemView.findViewById(R.id.csvDate);
        }

    }
}
