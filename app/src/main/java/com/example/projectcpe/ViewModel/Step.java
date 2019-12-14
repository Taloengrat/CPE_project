package com.example.projectcpe.ViewModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "step")
public class Step extends Mission {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idstep")
    private int idStep;

    private int Photo;

    private String Answer;

    public Step(){}

    public Step(int idStep){
        this.idStep = idStep;
    }



    public Step(String missionName, String detailMission, int age, int numberofMission) {
        super(missionName, detailMission, age, numberofMission);
    }

    public Step(String missionName, String detailMission, int age, int numberofMission, int idStep, int photo, String answer) {
        super(missionName, detailMission, age, numberofMission);
        this.idStep = idStep;
        Photo = photo;
        Answer = answer;
    }





    ///GETTER AND SETTER
    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
