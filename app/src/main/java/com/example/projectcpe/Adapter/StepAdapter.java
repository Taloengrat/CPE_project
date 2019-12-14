package com.example.projectcpe.Adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectcpe.R;
import com.example.projectcpe.ViewModel.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {


    //this context we will use to inflate the layout

    protected static LinearLayout frameEdittext;
    private Context mCtx;
    private Step[] steps;
    //we are storing all the products in a list
    public static List<Step> stepList;
    private OnCustomrPictureClick onCustomrPictureClick;



    public StepAdapter(Step[] steps){
        this.steps = steps;
    }


    public StepAdapter(List<Step> c, Context ctx) {
        this.stepList = c;
        this.mCtx = ctx;
        this.onCustomrPictureClick = (StepAdapter.OnCustomrPictureClick) ctx;
//        this.onCustomerItemClick = (MissionAdapter.OnCustomerItemClick) ctx;
    }

    @NonNull
    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list, parent,false);
        return new StepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepAdapter.StepViewHolder stepViewHolder, final int position) {
        Step step = (Step) stepList.get(position);

//        stepViewHolder.imStep.setImageResource(step.getPhoto());
        stepViewHolder.Numstep.setText(String.valueOf(position+1));

        stepViewHolder.score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mCtx);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.framedetailstep);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.put_detail_step);
                dialog.setCancelable(true);

                TextView textHead = dialog.findViewById(R.id.head);
                textHead.setText("Set a score of step "+String.valueOf(position+1));
                dialog.show();

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
        EditText answerStep;
        TextView Numstep;
        public LinearLayout frameEdittextthis;



        private final int CAMERA_RESULT_CODE = 100;

        public StepViewHolder(@NonNull final View itemView) {
            super(itemView);

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
                    stepList.get(getAdapterPosition()).setAnswer(answerStep.getText().toString());

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

