package com.example.projectcpe.Local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.projectcpe.ViewModel.Admin;
import com.example.projectcpe.ViewModel.Member;
import static com.example.projectcpe.Local.DATABASE.DATABASE_VERSION;

@Database(entities = {Admin.class, Member.class}, version = DATABASE_VERSION)
public abstract class DATABASE extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PROJECT-CPE:TEAM";

    public abstract DAO dao();
    private static DATABASE mInstance;

    public static  DATABASE getInstance(Context context)
    {
        if (mInstance == null)
        {

            mInstance = Room.databaseBuilder( context, DATABASE.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return mInstance;
    }
}
