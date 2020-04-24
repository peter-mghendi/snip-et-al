package com.boophq.snipetal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Snip.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract SnipDao snipDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "app_db")
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

        private PopulateDbAsyncTask(AppDatabase db) {
            snipDao = db.snipDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            snipDao.insert(new Snip("Hello", "Welcome to Snip et al.", 1));
            snipDao.insert(new Snip("Add a Snip", "Press the button at the bottom right to add a Snip.", 1));
            snipDao.insert(new Snip("Edit a Snip", "Click on any Snip to edit it.", 1));
            snipDao.insert(new Snip("Delete a Snip", "Swipe a Snip out in any direction to delete it", 1));
            return null;
        }
    }
}