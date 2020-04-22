package com.boophq.snipetal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Snip.class}, version = 1)
public abstract class SnipDatabase extends RoomDatabase {

    private static SnipDatabase instance;

    public abstract SnipDao snipDao();

    public static synchronized SnipDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SnipDatabase.class,
                    "snip_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
