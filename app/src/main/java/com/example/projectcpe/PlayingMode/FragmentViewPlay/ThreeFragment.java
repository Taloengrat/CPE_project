package com.example.projectcpe.PlayingMode.FragmentViewPlay;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcpe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {


    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.three_fragment, container, false);
        TextView textView = view.findViewById(R.id.text);
        ImageView imQuestion = view.findViewById(R.id.imQuestion);

        String step = getArguments().getString("step");
        String id = getArguments().getString("message");
        int pic = getArguments().getInt("position");

        textView.setText(String.valueOf(step));
        return view;
    }

}
