package com.example.projectcpe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projectcpe.Adapter.StepAdapter;

public class Main2Activity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = (TextView) findViewById(R.id.tv);

        for (int i = 0; i < StepAdapter.stepList.size(); i++){

            tv.setText(tv.getText() + " " + StepAdapter.stepList.get(i).getAnswer() +System.getProperty("line.separator"));

        }

    }
}
