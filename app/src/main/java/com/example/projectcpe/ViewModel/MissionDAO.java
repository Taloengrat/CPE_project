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

    @Query("select * from member")
    Member CheckMember();

    @Query("select * from static where idsub=:idsub order by score DESC")
    List<Static> getAllStatic(int idsub);

    @Query("select * from MemberStatic where idsub=:idsub order by score DESC")
    List<MemberStatic> getAllMemberStatic(int idsub);

    ////////////////////////////////////////////

    @Query("select * from mission where id=:id")
    int getDesMission(int id);

    @Query("SELECT * FROM mission WHERE id > 5")
    List<Mission> getMissionAdmin();

    @Query("select * from member where id=:id")
    int getDesMember(int id);

    @Query("select * from static where id=:id")
    int getDesStatic(int id);



    ////////////////////////////////////////////

    @Query("select * from mission where id=:id")
    List<Mission> getAllinfoOfMission(int id);

    @Query("select * from member where id=:id")
    List<Member> getAllinfoOfMember(int id);

    @Query("select * from static where memberId=:id and idsub=:missionId")
    Static CheckForUpdateStatistic(int id,int missionId);

    @Query("select * from mission where name LIKE :missionName")
    Mission CheckNameMission(String missionName);


    ///// Delete by idMember
    @Query("DELETE FROM static WHERE memberId = :memberId")
    void deleteStatisticByMemberId(int memberId);

    @Query("DELETE FROM memberstatic WHERE idsub = :memberId")
    void deleteByMemberStaticId(int memberId);


/////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Mission mission);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createMember(Member mission);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createStatistic(Static statistic);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createMemberStatic(MemberStatic memberStatic);

    ///////////////////////////////////////////////

    @Update
    void update(Mission mission);

    @Update
    void updateMember(Member mission);

    @Update
    void updateStatistic(Static statistic);

    @Query("UPDATE static SET score=:price,numstar=:numstar WHERE id = :id")
    void updateStatistic2(Float price, int id,int numstar);

    ////////////////////////////////////////////////

    @Delete
    void delete(Mission mission);

    @Delete
    void deleteMember(Member mission);

    @Delete
    void deleteMissionStatistic(Static mission);

}
