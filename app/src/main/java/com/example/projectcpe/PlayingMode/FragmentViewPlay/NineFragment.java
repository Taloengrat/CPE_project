package com.example.projectcpe.PlayingMode.FragmentViewPlay;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectcpe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NineFragment extends Fragment {


    public NineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.nine_fragment, container, false);
    }

}