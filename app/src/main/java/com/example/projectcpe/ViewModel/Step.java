package com.example.projectcpe.ViewModel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projectcpe.Adapter.StepAdapter;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "step")
public class Step{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idstep")
    private int idStep;
    public Bitmap Picture;
    private String Answer,Question,Score,Hint;
    private Drawable.ConstantState iconHint,iconScore;


    public Step(){}



    public Step(int idStep){
        this.idStep = idStep;
    }


    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    ///GETTER AND SETTER
    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    public Bitmap getPicture() {
        return Picture;
    }

    public void setPicture(Bitmap Pic) {
        Picture = Pic;
    }

    public Drawable.ConstantState geticonHint() {
        return iconHint;
    }

    public void seticonHint(Drawable.ConstantState iconHint1) {
        iconHint = iconHint1;
    }

    public Drawable.ConstantState geticonScore() {
        return iconScore;
    }

    public void seticonScore(Drawable.ConstantState iconScore1) {
        iconScore = iconScore1;
    }
}
