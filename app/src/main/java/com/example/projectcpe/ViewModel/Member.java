package com.example.projectcpe.ViewModel;

import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "member")
public class Member  {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "age")
    private String age;

    @ColumnInfo(name = "profile")
    private byte[] profile;

    @ColumnInfo(name = "password")
    private int password;

    @Ignore
    // Constructor
    public Member(){

    }


    public Member(String name, String age, byte[] profile, int password) {
        this.name = name;
        this.age = age;
        this.profile = profile;
        this.password = password;
    }


    //Getter and Setter

    public int getId() {
              return this.id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public int getPassword() { return password; }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringBuilder(name).toString();
    }

}
