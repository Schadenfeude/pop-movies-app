package com.itrided.android.popularmovies.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.itrided.android.popularmovies.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Daniel on 2.03.18.
 */

public class MovieDbUtils {

    //region Private Fields
    private static final String IMAGES_RESOLUTION = "w185/";
    private static final String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/" + IMAGES_RESOLUTION;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR_URL = "popular";
    private static final String TOP_RATED_URL = "top_rated";
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VAL = "en-US";
    private static final String API_KEY = "api_key";
    //endregion Private Fields

    //region Public Fields
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            TOP_RATED,
            POPULAR,
            FAVOURITE
    })
    public @interface MovieCategory {
    }

    public static final String TOP_RATED = "Top Rated";
    public static final String POPULAR = "Popular";
    public static final String FAVOURITE = "Favourite";
    //endregion Public Fields

    //region API Methods
    public static Request getPopularMoviesRequest() {
        return buildMovieRequest(POPULAR_URL);
    }

    public static Request getTopRatedMoviesRequest() {
        return buildMovieRequest(TOP_RATED_URL);
    }

    public static Request getMovieInfoRequest(@NonNull String movieId) {
        return buildMovieRequest(movieId);
    }

    public static Request getMovieImage(@NonNull String imageUrl) {
        return buildImageRequest(imageUrl);
    }
    //endregion API Methods

    //region Private Methods
    private static Request buildMovieRequest(@NonNull String type) {
        final HttpUrl httpUrl = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment(type)
                .addQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
                .addQueryParameter(LANGUAGE_KEY, LANGUAGE_VAL)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }

    private static Request buildImageRequest(@NonNull String imageUrl) {
        final String removedSlashes = imageUrl.replace("/", "");
        final HttpUrl httpUrl = HttpUrl.parse(IMAGES_BASE_URL).newBuilder()
                .addPathSegment(removedSlashes)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }
    //endregion Private Methods
}
