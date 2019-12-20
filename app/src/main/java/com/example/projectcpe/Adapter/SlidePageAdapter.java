package com.example.projectcpe.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.projectcpe.PlayingMode.FragmentViewPlay.OneFragment;

import java.util.List;

public class SlidePageAdapter extends FragmentStatePagerAdapter {

int entity;
    public SlidePageAdapter(FragmentManager fm,int id){
        super(fm);
        entity = id;
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

        OneFragment fragment = new OneFragment();
        Bundle bundle = new Bundle();
        position = position+1;
        bundle.putString("message", String.valueOf(entity));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
