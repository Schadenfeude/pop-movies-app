package com.itrided.android.popularmovies.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.itrided.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4.03.18.
 */

public class JSONUtils {

    private static final String RESULTS_KEY = "results";
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String BACKDROP_PATH_KEY = "backdrop_path";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";

    private static final String FALLBACK = "Information not available";

    @Nullable
    public static Movie parseMovieDetails(@NonNull String movieJsonString) {
        Movie movie = null;

        try {
            final JSONObject movieJson = new JSONObject(movieJsonString);

            final int id = movieJson.optInt(ID_KEY);
            final String title = movieJson.optString(TITLE_KEY, FALLBACK);
            final String overview = movieJson.optString(OVERVIEW_KEY, FALLBACK);
            final String posterPath = movieJson.optString(POSTER_PATH_KEY, FALLBACK);
            final String backdropPath = movieJson.optString(BACKDROP_PATH_KEY, FALLBACK);
            final String voteAvg = movieJson.optString(VOTE_AVERAGE_KEY, FALLBACK);
            final String releaseDate = movieJson.optString(RELEASE_DATE_KEY, FALLBACK);

            movie = new Movie(id, title, releaseDate, posterPath, backdropPath, voteAvg, overview);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    @Nullable
    public static List<Movie> parseMovieList(@NonNull String moviesJsonString) {
        try {
            final JSONObject moviesJson = new JSONObject(moviesJsonString);
            final JSONArray results = moviesJson.optJSONArray(RESULTS_KEY);
            final ArrayList<Movie> movies = new ArrayList<>(results.length());

            Movie movie;
            for (int i = 0; i < results.length(); i++) {
                movie = parseMovieDetails(results.getJSONObject(i).toString());
                movies.add(movie);
            }

            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
