package com.example.projectcpe.Database;

import com.example.projectcpe.ViewModel.Admin;
import com.example.projectcpe.ViewModel.Member;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class REPOSITORY implements IDATABASEsource {

    private IDATABASEsource mLocalDataSource;
    private static REPOSITORY mInstance;

    public REPOSITORY(IDATABASEsource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static REPOSITORY getInstance(IDATABASEsource mLocalDataSource){
        if (mInstance == null){
            mInstance = new REPOSITORY(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<Member> getMemberById(int memberId) {
        return  mLocalDataSource.getMemberById(memberId);
    }

    @Override
    public Flowable<List<Member>> getAllMember() {
        return mLocalDataSource.getAllMember();
    }

    @Override
    public Single<Admin> getPassword() {
        return getPassword();
    }

    @Override
    public void insertPassword(Admin password) {
        mLocalDataSource.insertPassword(password);
    }

    @Override
    public void insertMember(Member... members) {
        mLocalDataSource.insertMember(members);
    }

    @Override
    public void updateMember(Member... members) {
        mLocalDataSource.updateMember(members);
    }

    @Override
    public void updatePassword(Admin... password) {
        mLocalDataSource.updatePassword(password);
    }

    @Override
    public void deleteMember(Member member) {
        mLocalDataSource.deleteMember(member);
    }
}
