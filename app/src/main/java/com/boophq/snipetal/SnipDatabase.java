package com.boophq.snipetal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Snip.class}, version = 1)
public abstract class SnipDatabase extends RoomDatabase {

    private static SnipDatabase instance;

    public abstract SnipDao snipDao();

    public static synchronized SnipDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SnipDatabase.class,
                    "snip_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SnipDao snipDao;

        private PopulateDbAsyncTask(SnipDatabase db) {
            snipDao = db.snipDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            snipDao.insert(new Snip("Subject 1", "Content 1", 1));
            snipDao.insert(new Snip("Subject 2", "Content 2", 2));
            snipDao.insert(new Snip("Subject 3", "Content 3", 3));
            return null;
        }
    }
}