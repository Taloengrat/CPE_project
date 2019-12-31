package com.example.projectcpe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.projectcpe.PlayingMode.FragmentViewPlay.OneFragment;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.util.List;

public class SlidePageAdapter extends FragmentStatePagerAdapter {

int entity, step;
Context c;
    public List<Mission> Data;
    protected byte[] tranfer;
    Bitmap bmp;

    public SlidePageAdapter(FragmentManager fm,int id,int step,List<Mission> c){

        super(fm);
        entity = id;
        this.step = step;
        Data = c;

    }

    public SlidePageAdapter(@NonNull FragmentManager fm,String sendingEntity) {
        super(fm);


    }

    public SlidePageAdapter(@NonNull FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Data = getData(entity);

        OneFragment fragment = new OneFragment();

        Bundle bundle = new Bundle();


        position = position+1;
        bundle.putString("step", position +" / " +step);
        bundle.putInt("position", position);
        bundle.putString("message", String.valueOf(entity));



        switch (position) {

            case 1:
                bundle.putByteArray("code", Data.get(0).getP1());
                bundle.putString("question", Data.get(0).getQ1());
                break;
            case 2:
                bundle.putByteArray("code", Data.get(0).getP2());
                bundle.putString("question", Data.get(0).getQ2());
                break;
            case 3:
                bundle.putByteArray("code", Data.get(0).getP3());
                bundle.putString("question", Data.get(0).getQ3());
                break;
            case 4:
                bundle.putByteArray("code", Data.get(0).getP4());
                bundle.putString("question", Data.get(0).getQ4());
                break;
            case 5:
                bundle.putByteArray("code", Data.get(0).getP5());
                bundle.putString("question", Data.get(0).getQ5());
                break;
            case 6:
                bundle.putByteArray("code", Data.get(0).getP6());
                bundle.putString("question", Data.get(0).getQ6());
                break;
            case 7:
                bundle.putByteArray("code", Data.get(0).getP7());
                bundle.putString("question", Data.get(0).getQ7());
                break;
            case 8:
                bundle.putByteArray("code", Data.get(0).getP8());
                bundle.putString("question", Data.get(0).getQ8());
                break;
            case 9:
                bundle.putByteArray("code", Data.get(0).getP9());
                bundle.putString("question", Data.get(0).getQ9());
                break;
            case 10:
                bundle.putByteArray("code", Data.get(0).getP10());
                bundle.putString("question", Data.get(0).getQ10());
                break;


        }



        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public int getCount() {
        return this.step;
    }



    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(c).missionDAO().getAllinfoOfMission(id);
    }

}



