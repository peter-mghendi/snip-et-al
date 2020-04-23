package com.boophq.snipetal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SnipViewModel extends AndroidViewModel {
    private SnipRepository repository;
    private LiveData<List<Snip>> allSnips;

    public SnipViewModel(@NonNull Application application) {
        super(application);
        repository = new SnipRepository(application);
        allSnips = repository.getAllSnips();
    }

    public void insert(Snip snip) {
        repository.insert(snip);
    }

    public void update(Snip snip) {
        repository.update(snip);
    }

    public void delete(Snip snip) {
        repository.delete(snip);
    }

    public void deleteAllSnips() {
        repository.deleteAllSnips();
    }

    public LiveData<List<Snip>> getAllSnips() {
        return allSnips;
    }
}