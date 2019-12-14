package com.example.projectcpe.Local;

import com.example.projectcpe.Database.IDATABASEsource;
import com.example.projectcpe.ViewModel.Admin;
import com.example.projectcpe.ViewModel.Member;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DATABASEsource implements IDATABASEsource {

    private DAO userDAO;
    private static  DATABASEsource mInstance;

    public DATABASEsource (DAO dao){
        this.userDAO = dao;
    }

    public static DATABASEsource getInstance(DAO dao){
        if (mInstance == null)
        {
            mInstance = new DATABASEsource(dao);
        }
        return mInstance;
    }

    @Override
    public Flowable<Member> getMemberById(int memberId) {
        return userDAO.getMemberById(memberId);
    }

    @Override
    public Flowable<List<Member>> getAllMember() {
        return userDAO.getAllMember();
    }

    @Override
    public Single<Admin> getPassword() {
        return userDAO.getPassword();
    }

    @Override
    public void insertPassword(Admin password) {
        userDAO.insertPassword(password);
    }

    @Override
    public void insertMember(Member... members) {
        userDAO.insertUser(members);
    }

    @Override
    public void updateMember(Member... members) {
        userDAO.updateUser(members);
    }

    @Override
    public void updatePassword(Admin... password) {
        userDAO.updatePassword(password);

    }

    @Override
    public void deleteMember(Member member) {
        userDAO.deleteUsers(member);
    }
}
