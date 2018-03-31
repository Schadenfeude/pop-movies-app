package com.itrided.android.popularmovies.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Daniel on 5.03.18.
 */
//TODO WIP probably for the next version
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM Movie")
    public LiveData<List<Movie>> getAllMovies();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Query("DELETE FROM Movie")
    void deleteAll();
}
