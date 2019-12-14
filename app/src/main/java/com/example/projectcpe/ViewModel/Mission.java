package com.example.projectcpe.ViewModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "mission")
public class Mission implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idMission;

    @ColumnInfo(name = "name")
    private String missionName;

    @ColumnInfo(name = "detail")
    private String detailMission;

    @ColumnInfo(name = "number")
    private int numberofMission;

    @ColumnInfo(name = "age")
    private int age;



     @Ignore

     public List<Mission> missionList;
    public Mission(String missionName, int age) {
        this.missionName = missionName;
        this.age = age;
    }

    public List<Mission> getDetailmissionList()
    {
        return missionList;
    }



    public Mission(String missionName, String detailMission, int age,int numberofMission) {
//        this.idMission = idMission;
        this.missionName = missionName;
        this.detailMission = detailMission;
        this.numberofMission = numberofMission;
        this.age = age;
//        this.answer = answer;
//        this.question = question;
    }

    public Mission() {
    }

    public int getIdMission() {
        return idMission;
    }

    public void setIdMission(int idMission) {
        this.idMission = idMission;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getDetailMission() {
        return detailMission;
    }

    public void setDetailMission(String detailMission) {
        this.detailMission = detailMission;
    }

    public int getNumberofMission() {
        return numberofMission;
    }

    public void setNumberofMission(int numberofMission) {
        this.numberofMission = numberofMission;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public int[] getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(int[] question) {
//        this.question = question;
//    }
//
//    public String[] getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(String[] answer) {
//        this.answer = answer;
//    }
}
