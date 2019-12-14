package com.example.projectcpe.PlayingMode.Stepmission;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projectcpe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class detailStep extends Fragment {

    ImageView Sone, Stwo, Sthree, beginClick;
    public detailStep() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_detail_step, container, false);


         Sone = view.findViewById(R.id.Sone);
         Stwo = view.findViewById(R.id.Stwo);
         Sthree = view.findViewById(R.id.Stree);

         beginClick = view.findViewById(R.id.imageView);

         beginClick.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 ObjectAnimator three = ObjectAnimator.ofFloat(Sthree, View.ALPHA, 0f);
                 ObjectAnimator two = ObjectAnimator.ofFloat(Stwo, View.ALPHA, 0f);
                 ObjectAnimator one = ObjectAnimator.ofFloat(Stwo, View.ALPHA, 0f);

                 three.setDuration(1000);
                 two.setDuration(1000);
                 one.setDuration(1000);

                 beginClick.setVisibility(View.GONE);

                 Sthree.setVisibility(View.VISIBLE);
                 three.start();
                 Sthree.setVisibility(View.GONE);

                 if (Sthree.getVisibility() == View.GONE){
                 Stwo.setVisibility(View.VISIBLE);
                 two.start();
                 Stwo.setVisibility(View.GONE);}

                 if (Stwo.getVisibility() == View.GONE ) {
                     Sone.setVisibility(View.VISIBLE);
                     one.start();
                     Sone.setVisibility(View.GONE);
                 }
             }
         });


        return view;
    }




}
