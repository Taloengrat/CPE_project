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
public class TenFragment extends Fragment {


    public TenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ten_fragment, container, false);
    }

}
