package com.itrided.android.popularmovies.utils;

import android.content.ContentResolver;
import android.database.Cursor;

import com.itrided.android.popularmovies.library.LibraryAdapter;
import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.persistence.MovieContract;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class MovieLoader {

    private MovieLoader() {
    }

    @Nullable
    public static void loadMovies(@NonNull @MovieDbUtils.MovieCategory String category,
                                  @NonNull DisposableSingleObserver<Response> responseObserver) {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create(getEmitter(category))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(responseObserver));
    }

    private static SingleOnSubscribe<Response> getEmitter(@Nullable String category) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request movieRequest = MovieDbUtils.buildMovieCategoryRequest(category);

        return emitter -> {
            final Response response = okHttpClient.newCall(movieRequest).execute();

            if (!emitter.isDisposed()) {
                emitter.onSuccess(response);
            }
        };
    }

    public static void loadFavourites(@NonNull ContentResolver contentResolver,
                                      @NonNull DisposableSingleObserver<Cursor> responseObserver) {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create((SingleEmitter<Cursor> emitter) -> {
                            final Cursor cursor = contentResolver
                                    .query(MovieContract.MovieEntry.FAVOURITE_CONTENT_URI,
                                            null,
                                            null,
                                            null,
                                            null);
                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(cursor);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(responseObserver));
    }

    public static DisposableSingleObserver<Cursor> getFavouritesObserver(@NonNull final LibraryAdapter libraryAdapter) {
        return new DisposableSingleObserver<Cursor>() {
            @Override
            public void onSuccess(Cursor cursor) {
                libraryAdapter.setMovies(readMovieFromCursor(cursor));
            }

            private ArrayList<Movie> readMovieFromCursor(Cursor cursor) {
                final ArrayList<Movie> retMovies = new ArrayList<>(cursor.getCount());
                for (int i = 0; i < cursor.getCount(); i++) {
                    int idIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry._ID);
                    int titleIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry.COLUMN_NAME_TITLE);
                    int overviewIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry.COLUMN_NAME_OVERVIEW);
                    int posterPathIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry.COLUMN_NAME_POSTER_PATH);
                    int backdropPathIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry.COLUMN_NAME_BACKDROP_PATH);
                    int voteAverageIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry.COLUMN_NAME_VOTE_AVERAGE);
                    int releaseDateIdx = cursor.getColumnIndex(MovieContract
                            .MovieEntry.COLUMN_NAME_RELEASE_DATE);

                    cursor.moveToPosition(i);

                    final Movie movie = new Movie();
                    movie.setId(cursor.getInt(idIdx));
                    movie.setTitle(cursor.getString(titleIdx));
                    movie.setPlotSynopsis(cursor.getString(overviewIdx));
                    movie.setPoster(cursor.getString(posterPathIdx));
                    movie.setBackdrop(cursor.getString(backdropPathIdx));
                    movie.setVoteAvg(cursor.getString(voteAverageIdx));
                    movie.setReleaseDate(cursor.getString(releaseDateIdx));
                    movie.setFavourite(true);
                    retMovies.add(movie);
                }
                return retMovies;
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    public static void loadTrailers(@NonNull String movieId,
                                    @NonNull DisposableSingleObserver<Response> responseObserver) {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create((SingleOnSubscribe<Response>) emitter -> {
                            final OkHttpClient okHttpClient = new OkHttpClient();
                            final Request trailerRequest = MovieDbUtils.buildTrailersRequest(movieId);
                            final Response response = okHttpClient.newCall(trailerRequest).execute();

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(response);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(responseObserver));
    }

    public static void loadReviews(@NonNull String movieId,
                                   @NonNull DisposableSingleObserver<Response> responseObserver) {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create((SingleOnSubscribe<Response>) emitter -> {
                            final OkHttpClient okHttpClient = new OkHttpClient();
                            final Request reviewsRequest = MovieDbUtils.buildReviewsRequest(movieId);
                            final Response response = okHttpClient.newCall(reviewsRequest).execute();

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(response);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(responseObserver));
    }
}
