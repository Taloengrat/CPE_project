package com.example.projectcpe.Database;

import com.example.projectcpe.ViewModel.Admin;
import com.example.projectcpe.ViewModel.Member;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IDATABASEsource {


    Flowable<Member> getMemberById(int memberId);
    Flowable<List<Member>> getAllMember();
    Single<Admin> getPassword();
    void insertPassword(Admin password);
    void insertMember(Member... members);
    void updateMember(Member... members);
    void updatePassword(Admin... password);
    void deleteMember(Member member);

}
