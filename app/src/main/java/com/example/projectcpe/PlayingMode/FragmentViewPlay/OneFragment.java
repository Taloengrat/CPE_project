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

import com.example.projectcpe.PlayingMode.PlayPage;
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


    public OneFragment() {
        // Required empty public constructor
    }

   protected Bitmap  bmp ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.one_frag, container, false);
        TextView textView = view.findViewById(R.id.text);
        TextView Question = view.findViewById(R.id.text);
        ImageView imQuestion = view.findViewById(R.id.imQuestion);

        String step = getArguments().getString("step");
        int id = getArguments().getInt("message");
         byte[] pic = getArguments().getByteArray("code");

        textView.setText(String.valueOf(step));

//        this.missionData = getData(id);
        Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        bmp = BitmapFactory.decodeByteArray(pic, 0, pic.length);
//
//        assert id != null;
//        imQuestion.setImageBitmap(getPicture(pic,getData(Integer.valueOf(id))));
//
//        Toast.makeText(getActivity(), String.valueOf(pic),Toast.LENGTH_SHORT).show();


        imQuestion.setImageBitmap(bmp);


        return view;
    }



//    private Bitmap getPicture(int i,List<Mission> mission){
//
//        switch (i){
//            case 1 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP1(), 0, mission.get(0).getP1().length);
//                break;
//            case 2 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP2(), 0, mission.get(0).getP2().length);
//                break;
//            case 3 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP3(), 0, mission.get(0).getP3().length);
//                break;
//            case 4 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP4(), 0, mission.get(0).getP4().length);
//                break;
//            case 5 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP5(), 0, mission.get(0).getP5().length);
//                break;
//            case 6 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP6(), 0, mission.get(0).getP6().length);
//                break;
//            case 7 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP7(), 0, mission.get(0).getP7().length);
//                break;
//            case 8 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP8(), 0, mission.get(0).getP8().length);
//                break;
//            case 9 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP9(), 0, mission.get(0).getP9().length);
//                break;
//            case 10 : bmp = BitmapFactory.decodeByteArray(mission.get(0).getP10(), 0, mission.get(0).getP10().length);
//                break;
//        }
//
//        return bmp;
//    }
    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(getActivity()).missionDAO().getAllinfoOfMission(id);
    }
}
