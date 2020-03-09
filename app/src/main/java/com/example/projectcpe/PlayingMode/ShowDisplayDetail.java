package com.example.projectcpe.PlayingMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.projectcpe.Adapter.SlidePageAdapter;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class ShowDisplayDetail extends AppCompatActivity {

    private int id,stepnum,memberId;
    private SlidePageAdapter adapter;
    ViewPager viewPager;
    public List<Mission> missionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_display_detail);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("missionId");
        stepnum = bundle.getInt("step");
        memberId = bundle.getInt("memberId");

        viewPager  = findViewById(R.id.pagerDisplay);

        this.missionData = getData(id);

        adapter = new SlidePageAdapter(getSupportFragmentManager(), id, missionData.get(0).getNumberofMission(), getData(id));
        viewPager.setAdapter(adapter);
    }


    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(this).missionDAO().getAllinfoOfMission(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
