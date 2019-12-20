package com.example.projectcpe.PlayingMode.FragmentViewPlay;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    public List<Mission> missionData;
    Bitmap bmp;

    public OneFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.one_frag, container, false);
        TextView textView = view.findViewById(R.id.text);
        ImageView imQuestion = view.findViewById(R.id.imQuestion);

//        String message = getArguments().getString("message");
        String id = getArguments().getString("message");


        Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();

        this.missionData = getData(Integer.valueOf(id));



               bmp  = BitmapFactory.decodeByteArray(missionData.get(0).getP1(), 0, missionData.get(0).getP1().length);

        imQuestion.setImageBitmap(bmp);

        return view;
    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(getActivity()).missionDAO().getAllinfoOfMission(id);
    }
}
