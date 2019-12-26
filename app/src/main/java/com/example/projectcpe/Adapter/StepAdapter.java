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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcpe.CreateMission.FinallyCreate;
import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Step;

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



//        stepViewHolder.imStep.setImageResource(step.getPhoto());
        stepViewHolder.Numstep.setText(String.valueOf(position+1));

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

                final LinearLayout linearDetail2 = dialog.findViewById(R.id.linearDetail);


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

                        } else {
                            childAt[2]++;
                            childCount = stepViewHolder.frameEdittextthis.getChildCount();
                        }
                    }

//
                    childCount = stepViewHolder.frameEdittextthis.getChildCount();

                    // For Create TextView
                    for (int i = 0; i < childCount - 1; i++) {

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
                        linearDetail2.addView(linearLayout);
                        linearLayout.addView(textView);
                        linearLayout.addView(spinner);
                    }

                    // For Set Text in TextView.
                    final int childDetail = linearDetail2.getChildCount();

                    for (int j = 0; j < childDetail; j++) {

                        EditText textGeted;
                        textGeted = (EditText) stepViewHolder.frameEdittextthis.getChildAt(childAt[0] + 2);

                        LinearLayout layoutSelect;
                        layoutSelect = (LinearLayout) linearDetail2.getChildAt(childAt[0] + 1);

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
                        final int childDetail = linearDetail2.getChildCount();
                        for(int j = 0; j < childDetail ; j++) {




                            EditText textGeted;
                            textGeted = (EditText) stepViewHolder.frameEdittextthis.getChildAt(childAt[1] + 2);

                            LinearLayout layoutSelect;
                            layoutSelect = (LinearLayout) linearDetail2.getChildAt(childAt[1] + 1);

                            TextView textGet;
                            textGet = (TextView) layoutSelect.getChildAt(0);

                            Spinner spinItem;
                            spinItem = (Spinner) layoutSelect.getChildAt(1);
                            String spinText =  spinItem.getSelectedItem().toString();


                            textGeted.setText(textGet.getText());

                            allWord.add(textGet.getText().toString() + " ");



                            childAt[1]++;
                        }
                        dialog.cancel();


                        Log.w("Mylist", String.valueOf(allWord));
                    }
                });



            }
        });

        stepViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                final EditText textView = new EditText(mCtx);

                layoutParams.setMargins(0,5,0,0);
                layoutParams.weight = 1;

                textView.setTypeface(ResourcesCompat.getFont(mCtx, R.font.thin));
                textView.setLayoutParams(layoutParams);
                textView.setTextSize(16);

                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(R.drawable.bgtext);
                textView.setHint("Put your answer");
                textView.setMaxEms(8);



                stepViewHolder.frameEdittextthis.addView(textView);


            }
        });


    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imStep,add,score;
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


            imStep.setOnClickListener(this);

            frameEdittext = this.frameEdittextthis;











            answerStep.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    stepList.get(getAdapterPosition()).setAnswer(answerStep.getText().toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

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

