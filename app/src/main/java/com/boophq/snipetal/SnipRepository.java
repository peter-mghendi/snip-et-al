package com.boophq.snipetal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SnipRepository {

    private SnipDao snipDao;

    private LiveData<List<Snip>> allSnips;

    public SnipRepository(Application application) {
         AppDatabase database = AppDatabase.getInstance(application);
         snipDao = database.snipDao();
         allSnips = snipDao.getAllSnips();
    }

    public void insert(Snip snip) {
        new InsertSnipAsyncTask(snipDao).execute(snip);
    }

    public void update(Snip snip) {
        new UpdateSnipAsyncTask(snipDao).execute(snip);
    }

    public void delete(Snip snip) {
        new DeleteSnipAsyncTask(snipDao).execute(snip);
    }

    public void deleteAllSnips() {
        new DeleteAllSnipsAsyncTask(snipDao).execute();
    }

    public LiveData<List<Snip>> getAllSnips() {
        return allSnips;
    }

    private static class InsertSnipAsyncTask extends AsyncTask<Snip, Void, Void> {
        private SnipDao snipDao;

        private InsertSnipAsyncTask(SnipDao snipDao) {
            this.snipDao = snipDao;
        }

        @Override
        protected Void doInBackground(Snip... snips) {
            snipDao.insert(snips[0]);
            return null;
        }
    }

    private static class UpdateSnipAsyncTask extends AsyncTask<Snip, Void, Void> {
        private SnipDao snipDao;

        private UpdateSnipAsyncTask(SnipDao snipDao) {
            this.snipDao = snipDao;
        }

        @Override
        protected Void doInBackground(Snip... snips) {
            snipDao.update(snips[0]);
            return null;
        }
    }

    private static class DeleteSnipAsyncTask extends AsyncTask<Snip, Void, Void> {
        private SnipDao snipDao;

        private DeleteSnipAsyncTask(SnipDao snipDao) {
            this.snipDao = snipDao;
        }

        @Override
        protected Void doInBackground(Snip... snips) {
            snipDao.delete(snips[0]);
            return null;
        }
    }

    private static class DeleteAllSnipsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SnipDao snipDao;

        private DeleteAllSnipsAsyncTask(SnipDao snipDao) {
            this.snipDao = snipDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            snipDao.deleteAllSnips();
            return null;
        }
    }
}