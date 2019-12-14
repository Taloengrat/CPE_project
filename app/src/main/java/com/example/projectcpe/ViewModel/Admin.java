package com.example.projectcpe.ViewModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "admin")
public  class Admin {

    @PrimaryKey
    @ColumnInfo(name = "password")
    private int password;

    public Admin(int password) {
        this.password = password;
    }

    public Admin() {
    }

    public int getPassword() {
        return this.password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

}
