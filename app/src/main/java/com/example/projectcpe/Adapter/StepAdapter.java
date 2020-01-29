package com.example.projectcpe.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.CreateMission.FinallyCreate;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Step;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {


    //this context we will use to inflate the layout

    public static LinearLayout frameEdittext;
    private Context mCtx;
    private Step[] steps;
    //we are storing all the products in a list
    public static List<Step> stepList;
    private OnCustomrPictureClick onCustomrPictureClick;

    public String scoreOfStep ;

    public ArrayList<String> allWord = new ArrayList<String>();








    public StepAdapter(Step[] steps){
        this.steps = steps;
    }

    public StepAdapter(List<Step> c, Context ctx) {
        this.stepList = c;
        this.mCtx = ctx;
        this.onCustomrPictureClick = (StepAdapter.OnCustomrPictureClick) ctx;
//        this.onCustomerItemClick = (MissionAdapter.OnCustomerItemClick) ctx;
    }

    public StepAdapter(List<Step> steps) {
        this.stepList = steps;
    }


    @NonNull
    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list, parent,false);
        return new StepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepAdapter.StepViewHolder stepViewHolder, final int position) {
        final Step step = (Step) stepList.get(position);

        final int[] n = {1};



//        stepViewHolder.imStep.setImageResource(step.getPhoto());
        stepViewHolder.Numstep.setText("step "+String.valueOf(position+1));

        stepViewHolder.frameEdittextthis.getChildCount();


        stepViewHolder.score.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mCtx);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.framedetailstep);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.put_detail_step);
                dialog.setCancelable(true);

                TextView textHead = dialog.findViewById(R.id.head);
                textHead.setText("Set a score of step "+String.valueOf(position+1));

                Button bSave = dialog.findViewById(R.id.ok);

                final LinearLayout linearDetail = dialog.findViewById(R.id.linearDetail);


                int childCount = stepViewHolder.frameEdittextthis.getChildCount();
                final int[] childAt = {-1,-1,-1};

                TextView textView1 = (TextView) stepViewHolder.frameEdittextthis.getChildAt(childAt[2]+2);

                if (textView1.getText().toString().matches("")){
                    Toast.makeText(mCtx, "กรุณาใส่คำตอบที่ถูกตัองที่สุด", Toast.LENGTH_LONG).show();
                } else {

                    for (int k = 0; k < childCount - 1; k++) {
                        TextView textView = (TextView) stepViewHolder.frameEdittextthis.getChildAt(childAt[2] + 2);

                        Log.e("Text", textView.getText().toString());
                        Log.e("Hint", textView.getHint().toString());

                        if (textView.getText().toString().matches("")) {
                            stepViewHolder.frameEdittextthis.removeViewAt(childAt[2] + 2);
                            Log.e("DO it", "Delete");
                            n[0]--;

                        } else {
                            childAt[2]++;
                            childCount = stepViewHolder.frameEdittextthis.getChildCount();
                        }
                    }

//
                    childCount = stepViewHolder.frameEdittextthis.getChildCount();

                    // For Create TextView
                    for (int i = 0; i < childCount - 2; i++) {

                        // LayoutParams Properties.
                        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 5, 0, 0);
                        layoutParams.weight = 1;

                        // LinearLayout Properties.
                        final LinearLayout linearLayout = new LinearLayout(mCtx);

                        // TextView Properties.
                        final TextView textView = new TextView(mCtx);
                        textView.setTypeface(ResourcesCompat.getFont(mCtx, R.font.thin));
                        textView.setLayoutParams(layoutParams);
                        textView.setTextSize(16);
                        textView.setGravity(Gravity.CENTER);
                        textView.setBackgroundResource(R.drawable.bgtext);
                        textView.setHint("Put your answer");
                        textView.setMaxEms(8);

                        // Spinner Properties.
                        final Spinner spinner = new Spinner(mCtx);
                        String[] items = new String[]{"5", "6", "7", "8", "9", "10"};
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(mCtx, android.R.layout.simple_spinner_dropdown_item, items);
                        spinner.setAdapter(adapter);

                        // Create Detail.
                        linearDetail.addView(linearLayout);
                        linearLayout.addView(textView);
                        linearLayout.addView(spinner);
                    }

                    // For Set Text in TextView.
                    final int childDetail = linearDetail.getChildCount();

                    for (int j = 0; j < childDetail; j++) {

                        EditText textGeted;
                        textGeted = (EditText) stepViewHolder.frameEdittextthis.getChildAt(childAt[0] + 3);

                        LinearLayout layoutSelect;
                        layoutSelect = (LinearLayout) linearDetail.getChildAt(childAt[0] + 1);

                        TextView textSet;
                        textSet = (TextView) layoutSelect.getChildAt(0);

                        textSet.setText(textGeted.getText());
                        childAt[0]++;
                    }
                    dialog.show();
                }




                bSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int childDetail = linearDetail.getChildCount();

                        String sumScore = "10/";
                        for(int j = 0; j < childDetail ; j++) {




                            EditText textGeted;
                            textGeted = (EditText) stepViewHolder.frameEdittextthis.getChildAt(childAt[1] + 3);

                            LinearLayout layoutSelect;
                            layoutSelect = (LinearLayout) linearDetail.getChildAt(childAt[1] + 1);

                            TextView textGet;
                            textGet = (TextView) layoutSelect.getChildAt(0);

                            Spinner spinItem = (Spinner) layoutSelect.getChildAt(1);
                            String score =  spinItem.getSelectedItem().toString();

                            textGeted.setText(textGet.getText());

                            Log.e("Score",score);



                            sumScore += score+"/";




                            scoreOfStep = sumScore;

                            Log.e("SumScore",sumScore);

                            childAt[1]++;
                        }
                        stepList.get(position).setScore(sumScore);
                        dialog.cancel();






                    }
                });



            }
        });


        stepViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (n[0] < 5) {
                    final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final EditText textView = new EditText(mCtx);
                    layoutParams.setMargins(0, 5, 0, 0);
                    layoutParams.weight = 1;
                    textView.setTypeface(ResourcesCompat.getFont(mCtx, R.font.thin));
                    textView.setLayoutParams(layoutParams);
                    textView.setTextSize(16);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundResource(R.drawable.bgtext);
                    textView.setHint("Put your answer");
                    textView.setMaxEms(8);
                    textView.setText("");
                    onClickViewChange(textView, stepViewHolder.frameEdittextthis, position);
                    textView.requestFocus();
                    stepViewHolder.frameEdittextthis.addView(textView);
                    n[0]++;
                }

            }
        });


        stepViewHolder.hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(mCtx);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.framedetailstep);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.put_hint);
                dialog.setCancelable(true);

                TextView textHead = dialog.findViewById(R.id.head2);
                textHead.setText("Set a hint of step "+String.valueOf(position+1));

                Button bSave = dialog.findViewById(R.id.ok2);
                final ImageView add2 = dialog.findViewById(R.id.add2);
                final ImageView del2 = dialog.findViewById(R.id.del2);

                int childAt = -1;

                TextView textView1 = (TextView) stepViewHolder.frameEdittextthis.getChildAt(childAt+2);

                final LinearLayout linearDetail = dialog.findViewById(R.id.linearDetail2);


                if (textView1.getText().toString().matches("")){
                    Toast.makeText(mCtx, "กรุณาใส่คำตอบที่ถูกตัองที่สุด", Toast.LENGTH_LONG).show();
                } else {

                    final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 5, 0, 0);
//                    layoutParams.weight = 1;



                    // LinearLayout Properties.
                    final FlowLayout flowLayout = new FlowLayout(mCtx);
                    flowLayout.setLayoutParams(layoutParams);
                    flowLayout.setBackgroundResource(R.drawable.bgtext);


                    // TextView Properties.
                    final TextView textView = new TextView(mCtx);
                    textView.setTypeface(ResourcesCompat.getFont(mCtx, R.font.thin));
                    textView.setLayoutParams(layoutParams);
                    textView.setTextSize(16);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundResource(R.drawable.bgtext);
                    textView.setHint("Put your answer");
                    textView.setMaxEms(8);
//                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                    String textToHint = textView1.getText().toString();
                    final String[] textSplit = textToHint.split("\\s+");

                    linearDetail.addView(flowLayout);

                    for (int i = 0; i < textSplit.length; i++){

                        NewText(dialog,textSplit[i],0);

                    }

                    del2.setVisibility(View.INVISIBLE);

                    final int[] n = {1};
                    add2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (n[0] < 4) {
                                NewFlowLayout(dialog);
                              for (int i = 0; i < textSplit.length; i++){
                                NewText(dialog,textSplit[i], n[0]);
                                del2.setVisibility(View.VISIBLE);
                                }
                              n[0]++;
                              if (n[0] == 4){add2.setVisibility(View.INVISIBLE);}
                            }
                        }
                    });

                    del2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (n[0] > 1) {
                                int childcount2 = linearDetail.getChildCount();
                                linearDetail.removeViewAt(childcount2 - 1);
                                add2.setVisibility(View.VISIBLE);
                                n[0]--;
                            }
                            if (n[0] == 1){
                                del2.setVisibility(View.INVISIBLE);
                            }

                        }
                    });






                    dialog.show();
                }

                bSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final LinearLayout linearDetail = dialog.findViewById(R.id.linearDetail2);

                        int childcount = linearDetail.getChildCount();

                        String sumHint="";
                        String textHint = "";
                        String sumAllHint = "";

                        for (int j = 0; j < childcount; j++){
                            FlowLayout flowLayout = (FlowLayout) linearDetail.getChildAt(j);

                            int fChildcount = flowLayout.getChildCount();

                            for (int k = 0;k < fChildcount; k++ ) {

                                TextView textView = (TextView) flowLayout.getChildAt(k);
                                textHint = textView.getText().toString();

                                sumHint += textHint+" ";


                            }

                            sumHint += "/";
                        }
                        stepList.get(position).setHint(sumHint);
                        Log.e("sumHint",sumHint);
                    dialog.cancel();
                    }
                });




            }
        });


    }

    public void NewText (final Dialog dialog, String s , final int index){

        View.OnClickListener textDelelt = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) view;

                if (tv.getText().toString().matches("____")){
                    tv.setText(tv.getHint().toString());

                }

                else {
//                    final LinearLayout linearDetail2 = dialog.findViewById(R.id.linearDetail2);
//                    FlowLayout flowLayout1 = (FlowLayout) linearDetail2.getChildAt(index);
                    tv.setHint(tv.getText().toString());

                    tv.setText("____");
                }
            }
        };
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 5, 0, 5);
        // LinearLayout Properties.
        final FlowLayout flowLayout = new FlowLayout(mCtx);
        // TextView Properties.
        final TextView textView = new TextView(mCtx);
        textView.setTypeface(ResourcesCompat.getFont(mCtx, R.font.thin));
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.bgtext);
        textView.setText(s);
        textView.setOnClickListener(textDelelt);
        final LinearLayout linearDetail = dialog.findViewById(R.id.linearDetail2);
        FlowLayout flowLayout1 = (FlowLayout) linearDetail.getChildAt(index);
        flowLayout1.addView(textView);

    }

    public void NewFlowLayout (Dialog dialog){

        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 5, 0, 5);

        final FlowLayout flowLayout = new FlowLayout(mCtx);
        flowLayout.setLayoutParams(layoutParams);
        flowLayout.setBackgroundResource(R.drawable.bgtext);
        final LinearLayout linearDetail = dialog.findViewById(R.id.linearDetail2);
        linearDetail.addView(flowLayout);
    }

    public void onClickViewChange (EditText view, final LinearLayout linearLayout, final int position){
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s ) {

                int childCount = linearLayout.getChildCount();
                int childAt = -1;

                String sumAnswer = "";

                for (int i = 0 ; i < childCount-1 ; i++){


                    TextView textView = (TextView) linearLayout.getChildAt(childAt+2);
                    String nextText = textView.getText().toString();
                    sumAnswer += nextText+"/";

                    childAt++;
                }


                stepList.get(position).setAnswer(sumAnswer);

                TextView questionText = (TextView) linearLayout.getChildAt(0);


                stepList.get(position).setQuestion(questionText.getText().toString());


            }
        });




    }








    @Override
    public int getItemCount() {
        return stepList.size();
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imStep,add,score,hint;
        EditText answerStep,questionStep;
        TextView Numstep;
        public LinearLayout frameEdittextthis;





        public StepViewHolder(@NonNull final View itemView) {
            super(itemView);

            questionStep = itemView.findViewById(R.id.questionStep);
            imStep = itemView.findViewById(R.id.head);
            answerStep = itemView.findViewById(R.id.answerStep);
            Numstep = itemView.findViewById(R.id.numstep);
            add = itemView.findViewById(R.id.add);
            frameEdittextthis = itemView.findViewById(R.id.frameEdittext);
            score = itemView.findViewById(R.id.score);
            hint = itemView.findViewById(R.id.hint);
            imStep.setOnClickListener(this);

            frameEdittext = this.frameEdittextthis;



            answerStep.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    int childCount = frameEdittextthis.getChildCount();
                    int childAt = -1;
                    String sumAnswer = "";
                    for (int i = 0 ; i < childCount-1 ; i++){

                        TextView textView = (TextView) frameEdittextthis.getChildAt(childAt+2);
                        String nextText = textView.getText().toString();
                        sumAnswer = sumAnswer + " / " + nextText;

                        childAt++;
                    }
                    stepList.get(getAdapterPosition()).setAnswer(sumAnswer);
                    stepList.get(getAdapterPosition()).setQuestion(questionStep.getText().toString());
                }
            });

            questionStep.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    int childCount = frameEdittextthis.getChildCount();
                    int childAt = -1;
                    String sumAnswer = "";
                    for (int i = 0 ; i < childCount-1 ; i++){

                        TextView textView = (TextView) frameEdittextthis.getChildAt(childAt+2);
                        String nextText = textView.getText().toString();
                        sumAnswer = sumAnswer + " / " + nextText;

                        childAt++;
                    }
                    stepList.get(getAdapterPosition()).setAnswer(sumAnswer);
                    stepList.get(getAdapterPosition()).setQuestion(questionStep.getText().toString());
                }
            });




        }

        @Override
        public void onClick(View v) {
            onCustomrPictureClick.oncustompictureclick(getAdapterPosition(), this.imStep);

        }





    }

    public interface OnCustomrPictureClick{

        void oncustompictureclick(int pos, ImageView imageView);
    }


}

