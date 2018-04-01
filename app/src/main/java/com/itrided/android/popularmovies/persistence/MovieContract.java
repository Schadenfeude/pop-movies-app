package com.itrided.android.popularmovies.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    private MovieContract() {
    }

    public static final String AUTHORITY = "com.itrided.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String MOVIES_TABLE_PATH = "movies";
    public static final String FAVOURITES_PATH = "favorites";
//    public static final String POPULAR_PATH = "popular";
//    public static final String TOP_RATED_PATH = "top_rated";


    public static class MovieEntry implements BaseColumns {
        public static final Uri FAVOURITE_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(FAVOURITES_PATH).build();

//        public static final Uri MOVIES_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
//                .appendPath(MOVIES_TABLE_PATH).build();
//        public static final Uri POPULAR_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
//                .appendPath(POPULAR_PATH).build();
//        public static final Uri TOP_RATED_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
//                .appendPath(TOP_RATED_PATH).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_NAME_VOTE_COUNT = "voteCount";
        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_NAME_POSTER_PATH = "posterPath";
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdropPath";
        public static final String COLUMN_NAME_FLAGS_PATH = "flagsPath";
    }
}

