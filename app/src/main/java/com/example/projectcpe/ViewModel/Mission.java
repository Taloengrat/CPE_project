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


    ///////////// attribute Picture
    @ColumnInfo(name = "P1")
    private String P1;

    @ColumnInfo(name = "P2")
    private String P2;

    @ColumnInfo(name = "P3")
    private String P3;

    @ColumnInfo(name = "P4")
    private String P4;

    @ColumnInfo(name = "P5")
    private String P5;

    @ColumnInfo(name = "P6")
    private String P6;

    @ColumnInfo(name = "P7")
    private String P7;

    @ColumnInfo(name = "P8")
    private String P8;

    @ColumnInfo(name = "P9")
    private String P9;

    @ColumnInfo(name = "P10")
    private String P10;


    ///////////// attribute Question
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


    ///////////// attribute Answer
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

    ///////////// attribute Score
    @ColumnInfo(name = "S1")
    private String S1;

    @ColumnInfo(name = "S2")
    private String S2;

    @ColumnInfo(name = "S3")
    private String S3;

    @ColumnInfo(name = "S4")
    private String S4;

    @ColumnInfo(name = "S5")
    private String S5;

    @ColumnInfo(name = "S6")
    private String S6;

    @ColumnInfo(name = "S7")
    private String S7;

    @ColumnInfo(name = "S8")
    private String S8;

    @ColumnInfo(name = "S9")
    private String S9;

    @ColumnInfo(name = "S10")
    private String S10;
//
/////////////// attribute Hint
    @ColumnInfo(name = "H1")
    private String H1;

    @ColumnInfo(name = "H2")
    private String H2;

    @ColumnInfo(name = "H3")
    private String H3;

    @ColumnInfo(name = "H4")
    private String H4;

    @ColumnInfo(name = "H5")
    private String H5;

    @ColumnInfo(name = "H6")
    private String H6;

    @ColumnInfo(name = "H7")
    private String H7;

    @ColumnInfo(name = "H8")
    private String H8;

    @ColumnInfo(name = "H9")
    private String H9;

    @ColumnInfo(name = "H10")
    private String H10;






     @Ignore


    public Mission(String missionName, int age) {
        this.missionName = missionName;
        this.age = age;
    }




    public Mission(String missionName, String detailMission, int age, int numberofMission) {
//        this.idMission = idMission;
        this.missionName = missionName;
        this.detailMission = detailMission;
        this.numberofMission = numberofMission;
        this.age = age;
//        this.answer = answer;
//        this.question = question;
    }


    public Mission( String missionName, String detailMission, int age, int numberofMission
            , String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9, String p10
            , String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10
            , String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10
            , String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10
            , String h1, String h2, String h3, String h4, String h5, String h6, String h7, String h8, String h9, String h10) {
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
        S1 = s1;
        S2 = s2;
        S3 = s3;
        S4 = s4;
        S5 = s5;
        S6 = s6;
        S7 = s7;
        S8 = s8;
        S9 = s9;
        S10 = s10;
        H1 = h1;
        H2 = h2;
        H3 = h3;
        H4 = h4;
        H5 = h5;
        H6 = h6;
        H7 = h7;
        H8 = h8;
        H9 = h9;
        H10 = h10;
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

    public String getP1() { return P1; }

    public void setP1(String p1) { P1 = p1; }

    public String getP2() { return P2; }

    public void setP2(String p2) { P2 = p2; }

    public String getP3() { return P3; }

    public void setP3(String p3) { P3 = p3; }

    public String getP4() { return P4; }

    public void setP4(String p4) { P4 = p4; }

    public String getP5() { return P5; }

    public void setP5(String p5) { P5 = p5; }

    public String getP6() { return P6; }

    public void setP6(String p6) { P6 = p6; }

    public String getP7() { return P7; }

    public void setP7(String p7) { P7 = p7; }

    public String getP8() { return P8; }

    public void setP8(String p8) { P8 = p8; }

    public String getP9() { return P9; }

    public void setP9(String p9) { P9 = p9; }

    public String getP10() { return P10; }

    public void setP10(String p10) { P10 = p10; }

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

    public String getS1() { return S1; }

    public void setS1(String s1) { S1 = s1; }

    public String getS2() { return S2; }

    public void setS2(String s2) { S2 = s2; }

    public String getS3() { return S3; }

    public void setS3(String s3) { S3 = s3; }

    public String getS4() { return S4; }

    public void setS4(String s4) { S4 = s4; }

    public String getS5() { return S5; }

    public void setS5(String s5) { S5 = s5; }

    public String getS6() { return S6; }

    public void setS6(String s6) { S6 = s6; }

    public String getS7() { return S7; }

    public void setS7(String s7) { S7 = s7; }

    public String getS8() { return S8; }

    public void setS8(String s8) { S8 = s8; }

    public String getS9() { return S9; }

    public void setS9(String s9) { S9 = s9; }

    public String getS10() { return S10; }

    public void setS10(String s10) { S10 = s10; }

    public String getH1() { return H1; }

    public void setH1(String h1) { H1 = h1; }

    public String getH2() { return H2; }

    public void setH2(String h2) { H2 = h2; }

    public String getH3() { return H3; }

    public void setH3(String h3) { H3 = h3; }

    public String getH4() { return H4; }

    public void setH4(String h4) { H4 = h4; }

    public String getH5() { return H5; }

    public void setH5(String h5) { H5 = h5; }

    public String getH6() { return H6; }

    public void setH6(String h6) { H6 = h6; }

    public String getH7() { return H7; }

    public void setH7(String h7) { H7 = h7; }

    public String getH8() { return H8; }

    public void setH8(String h8) { H8 = h8; }

    public String getH9() { return H9; }

    public void setH9(String h9) { H9 = h9; }

    public String getH10() { return H10; }

    public void setH10(String h10) { H10 = h10; }

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
