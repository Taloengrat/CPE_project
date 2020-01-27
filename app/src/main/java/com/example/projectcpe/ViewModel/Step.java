package com.example.projectcpe.ViewModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "step")
public class Step{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idstep")
    private int idStep;

    private byte[] Photo;

    private String Answer,Question,Score,Hint;



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

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
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



}
