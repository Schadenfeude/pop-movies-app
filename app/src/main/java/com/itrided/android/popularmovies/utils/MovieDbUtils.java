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

public final class MovieDbUtils {

    //region Private Fields
    private static final String IMAGES_RESOLUTION = "w500/";
    private static final String YOUTUBE_IMAGE = "0.jpg";

    private static final String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/" + IMAGES_RESOLUTION;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String YOUTUBE_IMAGES_BASE_URL = "https://img.youtube.com/vi/";

    private static final String VIDEOS_SEGMENT = "videos";
    private static final String REVIEWS_SEGMENT = "reviews";
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

    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR = "popular";
    public static final String FAVOURITE = "favourite";
    //endregion Public Fields

    private MovieDbUtils() {
    }

    //region API Methods
    public static Request buildMovieCategoryRequest(@NonNull @MovieCategory String category) {
        return buildMovieRequest(category);
    }

    public static Request buildMovieDetailsRequest(@NonNull String movieId) {
        return buildMovieRequest(movieId);
    }

    public static Request buildImageRequest(@NonNull String imageUrl) {
        final String removedSlashesImageUrl = imageUrl.replace("/", "");
        final HttpUrl httpUrl = HttpUrl.parse(IMAGES_BASE_URL).newBuilder()
                .addPathSegment(removedSlashesImageUrl)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }

    public static Request buildTrailerThumbnailRequest(@NonNull String youTubeKey) {
        final HttpUrl httpUrl = HttpUrl.parse(YOUTUBE_IMAGES_BASE_URL).newBuilder()
                .addPathSegment(youTubeKey)
                .addPathSegment(YOUTUBE_IMAGE)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }

    public static Request buildTrailersRequest(@NonNull String movieId) {
        final HttpUrl httpUrl = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment(movieId)
                .addPathSegment(VIDEOS_SEGMENT)
                .addQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
                .addQueryParameter(LANGUAGE_KEY, LANGUAGE_VAL)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }

    public static Request buildReviewsRequest(@NonNull String movieId) {
        final HttpUrl httpUrl = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment(movieId)
                .addPathSegment(REVIEWS_SEGMENT)
                .addQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
                .addQueryParameter(LANGUAGE_KEY, LANGUAGE_VAL)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }
    //endregion API Methods

    //region Private Methods
    private static Request buildMovieRequest(@NonNull String movieId) {
        final HttpUrl httpUrl = HttpUrl.parse(BASE_URL).newBuilder()
                .addPathSegment(movieId)
                .addQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
                .addQueryParameter(LANGUAGE_KEY, LANGUAGE_VAL)
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .build();
    }
    //endregion Private Methods
}
