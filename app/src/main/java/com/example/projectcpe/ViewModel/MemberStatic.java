package com.example.projectcpe.ViewModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;


@Entity(tableName = "MemberStatic")
public class MemberStatic {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "idsub")
    private int idsub;


    @ColumnInfo(name = "name")
    private String missionName;

    @ColumnInfo(name = "age")
    private String forAge;

    @ColumnInfo(name = "star")
    private int numStar;

    @ColumnInfo(name = "score")
    private float score;


    public MemberStatic() {
    }

    @Ignore


    public MemberStatic(int idsub, String missionName, String forAge, int numStar, float score) {
        this.missionName = missionName;
       this.idsub = idsub;
        this.forAge = forAge;
        this.numStar = numStar;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsub() { return idsub; }

    public void setIdsub(int idsub) { this.idsub = idsub; }

    public String getMissionName() { return missionName; }

    public void setMissionName(String missionName) { this.missionName = missionName; }

    public String getForAge() { return forAge; }

    public void setForAge(String forAge) { this.forAge = forAge; }

    public int getNumStar() { return numStar; }

    public void setNumStar(int numStar) { this.numStar = numStar; }

    public float getScore() { return score; }

    public void setScore(float score) { this.score = score; }
}
