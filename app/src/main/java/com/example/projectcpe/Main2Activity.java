package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.Adapter.StepAdapter;
import com.example.projectcpe.PlayingMode.PlayPage;
import com.example.projectcpe.ViewModel.Mission;
import com.example.projectcpe.ViewModel.MissionDATABASE;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private TextView tv;
    EditText editText;
    Button button;
    String date,value,line;
    List<Mission> missionList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = (TextView) findViewById(R.id.tv);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

      missionList = getData(2);

      line = missionList.get(0).getA10();



            final String[] RowData = line.split("/");

            final List<String> code = Arrays.asList(RowData);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (code.contains(editText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                }
            }
        });


            // do something with "data" and "value"


//        for (int i = 0; i < StepAdapter.stepList.size(); i++){
//
//            tv.setText(tv.getText() + " " + StepAdapter.stepList.get(i).getAnswer() +System.getProperty("line.separator"));
//
//        }

    }

    private List<Mission> getData(int id) {
        return MissionDATABASE.getInstance(Main2Activity.this).missionDAO().getAllinfoOfMission(id);
    }
}
