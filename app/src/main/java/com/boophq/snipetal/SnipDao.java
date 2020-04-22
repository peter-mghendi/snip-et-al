package com.boophq.snipetal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SnipDao {

    @Insert
    void insert(Snip snip);

    @Update
    void update(Snip snip);

    @Delete
    void delete(Snip snip);

    @Query("DELETE FROM snip_table")
    void deleteAllSnips();

    @Query("SELECT * FROM snip_table ORDER BY priority DESC")
    LiveData<List<Snip>> getAllSnips();
}
