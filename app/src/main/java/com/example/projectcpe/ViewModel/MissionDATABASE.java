package com.example.projectcpe.ViewModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = { Mission.class, Member.class, Static.class}, version = 1)
public abstract class MissionDATABASE extends RoomDatabase {
    private static final String DATABASE_NAME = "Mission";
    private static MissionDATABASE db;

    public abstract MissionDAO missionDAO();

    public static  MissionDATABASE getInstance(Context ctx){
        if (db == null){
            db = Room.databaseBuilder(ctx.getApplicationContext(), MissionDATABASE.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }

    public static void desInstance(){
        db = null;
    }
}
