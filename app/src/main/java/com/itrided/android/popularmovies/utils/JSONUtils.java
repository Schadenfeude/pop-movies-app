package com.itrided.android.popularmovies.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.model.Review;
import com.itrided.android.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Daniel on 4.03.18.
 */

public final class JSONUtils {

    // General Properties
    private static final String RESULTS_KEY = "results";
    private static final String ID_KEY = "id";

    // Movie Properties
    private static final String TITLE_KEY = "title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String BACKDROP_PATH_KEY = "backdrop_path";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";

    // Trailer Properties
    private static final String LANGUAGE_CODE_KEY = "iso_639_1";
    private static final String COUNTRY_CODE_KEY = "iso_3166_1";
    private static final String YOUTUBE_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String SITE_KEY = "site";
    private static final String SIZE_KEY = "size";
    private static final String TYPE_KEY = "type";

    // Review Properties
    private static final String AUTHOR_KEY = "author";
    private static final String CONTENT_KEY = "content";
    private static final String URL_KEY = "url";

    private static final String FALLBACK = "Information not available";

    private JSONUtils() {
    }

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
    public static ArrayList<Movie> parseMovieList(@NonNull String moviesJsonString) {
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

    @Nullable
    public static ArrayList<Review> parseReviews(@NonNull String movieReviewsJsonString) {
        try {
            final JSONObject reviewsJson = new JSONObject(movieReviewsJsonString);
            final JSONArray results = reviewsJson.optJSONArray(RESULTS_KEY);
            final ArrayList<Review> reviews = new ArrayList<>(results.length());

            Review review;
            for (int i = 0; i < results.length(); i++) {
                review = parseReview(results.getJSONObject(i).toString());
                reviews.add(review);
            }

            return reviews;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private static Review parseReview(@NonNull String movieReviewJsonString) {
        Review review = null;

        try {
            final JSONObject reviewJson = new JSONObject(movieReviewJsonString);

            final String id = reviewJson.optString(ID_KEY, FALLBACK);
            final String author = reviewJson.optString(AUTHOR_KEY, FALLBACK);
            final String content = reviewJson.optString(CONTENT_KEY, FALLBACK);
            final String url = reviewJson.optString(URL_KEY, FALLBACK);

            review = new Review(id, author, content, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return review;
    }

    @Nullable
    public static ArrayList<Trailer> parseTrailers(@NonNull String movieTrailersJsonString) {
        try {
            final JSONObject trailersJson = new JSONObject(movieTrailersJsonString);
            final JSONArray results = trailersJson.optJSONArray(RESULTS_KEY);
            final ArrayList<Trailer> trailers = new ArrayList<>(results.length());

            Trailer trailer;
            for (int i = 0; i < results.length(); i++) {
                trailer = parseTrailer(results.getJSONObject(i).toString());
                trailers.add(trailer);
            }

            return trailers;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Trailer parseTrailer(@NonNull String movieTrailerJsonString) {
        Trailer trailer = null;

        try {
            final JSONObject trailerJson = new JSONObject(movieTrailerJsonString);

            final String id = trailerJson.optString(ID_KEY, FALLBACK);
            final String language = trailerJson.optString(LANGUAGE_CODE_KEY, FALLBACK);
            final String country = trailerJson.optString(COUNTRY_CODE_KEY, FALLBACK);
            final String youTubeKey = trailerJson.optString(YOUTUBE_KEY, FALLBACK);
            final String name = trailerJson.optString(NAME_KEY, FALLBACK);
            final String site = trailerJson.optString(SITE_KEY, FALLBACK);
            final int size = trailerJson.optInt(SIZE_KEY, 0);
            final String type = trailerJson.optString(TYPE_KEY, FALLBACK);

            trailer = new Trailer(id, language, country, youTubeKey, name, site, size, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailer;
    }
}
