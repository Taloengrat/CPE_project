package com.example.projectcpe.ViewModel;

public class Csv {
    private String csvName;
    private String csvDate;

    public Csv() {
    }

    public Csv(String csvName, String csvDate) {
        this.csvName = csvName;
        this.csvDate = csvDate;
    }

    public String getCsvName() {
        return csvName;
    }

    public void setCsvName(String csvName) {
        this.csvName = csvName;
    }

    public String getCsvDate() {
        return csvDate;
    }

    public void setCsvDate(String csvDate) {
        this.csvDate = csvDate;
    }
}
