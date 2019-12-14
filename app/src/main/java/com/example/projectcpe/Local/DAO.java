package com.example.projectcpe.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectcpe.ViewModel.Admin;
import com.example.projectcpe.ViewModel.Member;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface DAO {

    @Query("select * from member where id=:memberId")
    Flowable<Member> getMemberById(int memberId);

    @Query("select * from member ")
    Flowable<List<Member>> getAllMember();

    @Query("select * from admin ")
    Single<Admin> getPassword();

    @Insert
    void insertPassword(Admin... password);

    @Insert
    void insertUser(Member... members);

    @Update
    void updateUser(Member... members);

    @Update
    void updatePassword(Admin... password);

    @Delete
    void deleteUsers(Member member);




}
