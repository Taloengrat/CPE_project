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
        bundle.putInt("message", Data.get(0).getIdMission());



        switch (position) {

            case 1:

                bundle.putString("code", Data.get(0).getP1());
                bundle.putString("question", Data.get(0).getQ1());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 2:
                bundle.putString("code", Data.get(0).getP2());
                bundle.putString("question", Data.get(0).getQ2());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 3:
                bundle.putString("code", Data.get(0).getP3());
                bundle.putString("question", Data.get(0).getQ3());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 4:
                bundle.putString("code", Data.get(0).getP4());
                bundle.putString("question", Data.get(0).getQ4());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 5:
                bundle.putString("code", Data.get(0).getP5());
                bundle.putString("question", Data.get(0).getQ5());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 6:
                bundle.putString("code", Data.get(0).getP6());
                bundle.putString("question", Data.get(0).getQ6());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 7:
                bundle.putString("code", Data.get(0).getP7());
                bundle.putString("question", Data.get(0).getQ7());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 8:
                bundle.putString("code", Data.get(0).getP8());
                bundle.putString("question", Data.get(0).getQ8());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 9:
                bundle.putString("code", Data.get(0).getP9());
                bundle.putString("question", Data.get(0).getQ9());
                bundle.putString("numberpicture",String.valueOf(position));
                break;
            case 10:
                bundle.putString("code", Data.get(0).getP10());
                bundle.putString("question", Data.get(0).getQ10());
                bundle.putString("numberpicture",String.valueOf(position));
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



