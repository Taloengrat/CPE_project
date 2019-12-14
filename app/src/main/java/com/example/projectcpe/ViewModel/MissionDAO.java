package com.example.projectcpe.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MissionDAO {
    @Query("select * from mission")
    List<Mission> getAllMission();

    @Query("select * from member")
    List<Member> getAllMember();

    ////////////////////////////////////////////

    @Query("select * from mission where id=:id")
    int getDesMission(int id);

    @Query("select * from member where id=:id")
    int getDesMember(int id);

    ////////////////////////////////////////////

    @Query("select * from mission where id=:id")
    List<Mission> getAllinfoOfMission(int id);

    @Query("select * from member where id=:id")
    List<Member> getAllinfoOfMember(int id);

    @Query("select * from member where id=:id")
    LiveData<Member> getProfile(int id);

/////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Mission mission);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createMember(Member mission);

    ///////////////////////////////////////////////

    @Update
    void update(Mission mission);

    @Update
    void updateMember(Member mission);

    ////////////////////////////////////////////////

    @Delete
    void delete(Mission mission);

    @Delete
    void deleteMember(Member mission);

}
