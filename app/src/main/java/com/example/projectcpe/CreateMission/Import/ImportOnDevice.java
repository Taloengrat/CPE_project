package com.example.projectcpe.CreateMission.Import;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;

import com.example.projectcpe.Adapter.CsvImportAdapter;
import com.example.projectcpe.Adapter.MissionAdapter;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Csv;
import com.example.projectcpe.ViewModel.Mission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportOnDevice extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadData();


    }

    private void loadData(){
        RecyclerView.Adapter adapter = new CsvImportAdapter(getFilelist(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Csv> getFilelist() {

        ArrayList<Csv> csvArrayList = new ArrayList<>();

        String asdf = Environment.getExternalStorageDirectory().getPath()+"/MyMissionExport/";
        File downloadsFolder = new File(asdf);

        Csv csv;

        if (downloadsFolder.exists())
        {
            File[] files =downloadsFolder.listFiles();

            assert files != null;
            for (int i = 0; i<files.length; i++){

                File file = files[i];


                    csv = new Csv();
                    csv.setCsvName(file.getName());
                    csv.setCsvDate(file.getAbsolutePath());

                    csvArrayList.add(csv);

            }
        }
        return csvArrayList;
    }

    @Override
    public void onBackPressed() {
        // your code.

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }
}
