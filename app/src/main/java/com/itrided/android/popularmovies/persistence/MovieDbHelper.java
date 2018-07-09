package com.itrided.android.popularmovies.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSIOn = 1;
    public static final String DATABASE_NAME = "PopularMovies.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                    MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                    MovieContract.MovieEntry.COLUMN_NAME_TITLE + " TEXT," +
                    MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW + " TEXT," +
                    MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE + " TEXT," +
                    MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE + " TEXT," +
                    MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH + " TEXT," +
                    MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH + " TEXT)";

    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME;

    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSIOn);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
