package com.itrided.android.popularmovies.utils;

import android.support.annotation.NonNull;

import com.itrided.android.popularmovies.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Daniel on 2.03.18.
 */

public class MovieDbUtils {

    private static final String IMAGES_RESOLUTION = "w185/";
    private static final String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/" + IMAGES_RESOLUTION;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String LANGUAGE_KEY = "language";
    private static final String LANGUAGE_VAL = "en-US";
    private static final String API_KEY = "api_key";

    public static Request getPopularMoviesRequest() {
        return buildMovieRequest(POPULAR);
    }

    public static Request getTopRatedMoviesRequest() {
        return buildMovieRequest(TOP_RATED);
    }

    public static Request getMovieInfoRequest(@NonNull String movieId) {
        return buildMovieRequest(movieId);
    }

    public static Request getMovieImage(@NonNull String imageUrl) {
        return buildImageRequest(imageUrl);
    }

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
        final HttpUrl httpUrl = HttpUrl.parse(IMAGES_BASE_URL).newBuilder()
                .addPathSegment(imageUrl)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }
}
