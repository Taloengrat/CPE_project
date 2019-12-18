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

    @ColumnInfo(name = "P1")
    private byte[] P1;

    @ColumnInfo(name = "P2")
    private byte[] P2;

    @ColumnInfo(name = "P3")
    private byte[] P3;

    @ColumnInfo(name = "P4")
    private byte[] P4;

    @ColumnInfo(name = "P5")
    private byte[] P5;

    @ColumnInfo(name = "P6")
    private byte[] P6;

    @ColumnInfo(name = "P7")
    private byte[] P7;

    @ColumnInfo(name = "P8")
    private byte[] P8;

    @ColumnInfo(name = "P9")
    private byte[] P9;

    @ColumnInfo(name = "P10")
    private byte[] P10;

    @ColumnInfo(name = "Q1")
    private String Q1;

    @ColumnInfo(name = "Q2")
    private String Q2;

    @ColumnInfo(name = "Q3")
    private String Q3;

    @ColumnInfo(name = "Q4")
    private String Q4;

    @ColumnInfo(name = "Q5")
    private String Q5;

    @ColumnInfo(name = "Q6")
    private String Q6;

    @ColumnInfo(name = "Q7")
    private String Q7;

    @ColumnInfo(name = "Q8")
    private String Q8;

    @ColumnInfo(name = "Q9")
    private String Q9;

    @ColumnInfo(name = "Q10")
    private String Q10;

    @ColumnInfo(name = "A1")
    private String A1;

    @ColumnInfo(name = "A2")
    private String A2;

    @ColumnInfo(name = "A3")
    private String A3;

    @ColumnInfo(name = "A4")
    private String A4;

    @ColumnInfo(name = "A5")
    private String A5;

    @ColumnInfo(name = "A6")
    private String A6;

    @ColumnInfo(name = "A7")
    private String A7;

    @ColumnInfo(name = "A8")
    private String A8;

    @ColumnInfo(name = "A9")
    private String A9;

    @ColumnInfo(name = "A10")
    private String A10;




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


    public Mission( String missionName, String detailMission, int age, int numberofMission, byte[] p1, byte[] p2, byte[] p3, byte[] p4, byte[] p5, String q1, String q2, String q3, String q4, String q5, String a1, String a2, String a3, String a4, String a5) {
        this.idMission = idMission;
        this.missionName = missionName;
        this.detailMission = detailMission;
        this.numberofMission = numberofMission;
        this.age = age;
        P1 = p1;
        P2 = p2;
        P3 = p3;
        P4 = p4;
        P5 = p5;
        Q1 = q1;
        Q2 = q2;
        Q3 = q3;
        Q4 = q4;
        Q5 = q5;
        A1 = a1;
        A2 = a2;
        A3 = a3;
        A4 = a4;
        A5 = a5;
    }

    public Mission(int idMission, String missionName, String detailMission, int age, int numberofMission
            , byte[] p1, byte[] p2, byte[] p3, byte[] p4, byte[] p5, byte[] p6, byte[] p7, byte[] p8, byte[] p9, byte[] p10
            , String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10
            , String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10) {

        this.idMission = idMission;
        this.missionName = missionName;
        this.detailMission = detailMission;
        this.numberofMission = numberofMission;
        this.age = age;

        P1 = p1;
        P2 = p2;
        P3 = p3;
        P4 = p4;
        P5 = p5;
        P6 = p6;
        P7 = p7;
        P8 = p8;
        P9 = p9;
        P10 = p10;

        Q1 = q1;
        Q2 = q2;
        Q3 = q3;
        Q4 = q4;
        Q5 = q5;
        Q6 = q6;
        Q7 = q7;
        Q8 = q8;
        Q9 = q9;
        Q10 = q10;

        A1 = a1;
        A2 = a2;
        A3 = a3;
        A4 = a4;
        A5 = a5;
        A6 = a6;
        A7 = a7;
        A8 = a8;
        A9 = a9;
        A10 = a10;
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

    public byte[] getP1() { return P1; }

    public void setP1(byte[] p1) { P1 = p1; }

    public byte[] getP2() { return P2; }

    public void setP2(byte[] p2) { P2 = p2; }

    public byte[] getP3() { return P3; }

    public void setP3(byte[] p3) { P3 = p3; }

    public byte[] getP4() { return P4; }

    public void setP4(byte[] p4) { P4 = p4; }

    public byte[] getP5() { return P5; }

    public void setP5(byte[] p5) { P5 = p5; }

    public byte[] getP6() { return P6; }

    public void setP6(byte[] p6) { P6 = p6; }

    public byte[] getP7() { return P7; }

    public void setP7(byte[] p7) { P7 = p7; }

    public byte[] getP8() { return P8; }

    public void setP8(byte[] p8) { P8 = p8; }

    public byte[] getP9() { return P9; }

    public void setP9(byte[] p9) { P9 = p9; }

    public byte[] getP10() { return P10; }

    public void setP10(byte[] p10) { P10 = p10; }

    public String getQ1() { return Q1; }

    public void setQ1(String q1) { Q1 = q1; }

    public String getQ2() { return Q2; }

    public void setQ2(String q2) { Q2 = q2; }

    public String getQ3() { return Q3; }

    public void setQ3(String q3) { Q3 = q3; }

    public String getQ4() { return Q4; }

    public void setQ4(String q4) { Q4 = q4; }

    public String getQ5() { return Q5; }

    public void setQ5(String q5) { Q5 = q5; }

    public String getQ6() { return Q6; }

    public void setQ6(String q6) { Q6 = q6; }

    public String getQ7() { return Q7; }

    public void setQ7(String q7) { Q7 = q7; }

    public String getQ8() { return Q8; }

    public void setQ8(String q8) { Q8 = q8; }

    public String getQ9() { return Q9; }

    public void setQ9(String q9) { Q9 = q9; }

    public String getQ10() { return Q10; }

    public void setQ10(String q10) { Q10 = q10; }

    public String getA1() { return A1; }

    public void setA1(String a1) { A1 = a1; }

    public String getA2() { return A2; }

    public void setA2(String a2) { A2 = a2; }

    public String getA3() { return A3; }

    public void setA3(String a3) { A3 = a3; }

    public String getA4() { return A4; }

    public void setA4(String a4) { A4 = a4; }

    public String getA5() { return A5; }

    public void setA5(String a5) { A5 = a5; }

    public String getA6() { return A6; }

    public void setA6(String a6) { A6 = a6; }

    public String getA7() { return A7; }

    public void setA7(String a7) { A7 = a7; }

    public String getA8() { return A8; }

    public void setA8(String a8) { A8 = a8; }

    public String getA9() { return A9; }

    public void setA9(String a9) { A9 = a9; }

    public String getA10() { return A10; }

    public void setA10(String a10) { A10 = a10; }

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
