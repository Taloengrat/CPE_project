package com.example.projectcpe.ViewModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "static")
public class Static {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "idsub")
    private int subid;

    @ColumnInfo(name = "memberId")
    private int memberId;

    @ColumnInfo(name = "profile")
    private
    byte[] profile;

    @ColumnInfo(name = "name")
    private
    String name;

    @ColumnInfo(name = "age")
    private
    int age;

    @ColumnInfo(name = "score")
    private
    float score;

    @ColumnInfo(name = "numstar")
    private
    int numStar;

    @Ignore

    public Static() {
    }


    public Static(int subid,int memberId, byte[] profile, String name, int age, float score, int numStar) {
        this.id = id;
        this.subid = subid;
        this.memberId = memberId;
        this.profile = profile;
        this.name = name;
        this.age = age;
        this.score = score;
        this.numStar = numStar;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() { return memberId; }

    public void setMemberId(int memberId) { this.memberId = memberId; }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getNumStar() {
        return numStar;
    }

    public void setNumStar(int numStar) {
        this.numStar = numStar;
    }
}
