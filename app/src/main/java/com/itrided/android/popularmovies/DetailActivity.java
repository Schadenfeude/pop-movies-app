package com.itrided.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.utils.JSONUtils;
import com.itrided.android.popularmovies.utils.MovieDbUtils;
import com.itrided.android.popularmovies.details.MovieDetailsView;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private MovieDetailsView detailsView;

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsView = new MovieDetailsView(this);
        setSupportActionBar(detailsView.getToolbar());

        loadMovieDetails();
    }
    //endregion Overridden Methods

    //todo remove this when local persistence is done
    private void loadMovieDetails() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        //todo this will be taken from persistence
        final Request movieRequest = MovieDbUtils.buildMovieDetailsRequest(Integer.toString(movieId));
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create((SingleEmitter<Response> emitter) -> {
                            final Response response =
                                    okHttpClient.newCall(movieRequest).execute();

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(response);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                String movieDetailsJson = "";
                                try {
                                    movieDetailsJson = response.body().string();
                                } catch (NullPointerException | IOException e) {
                                    e.printStackTrace();
                                }
                                final Movie movie = JSONUtils.parseMovieDetails(movieDetailsJson);
                                detailsView.setMovieDetails(movie);
                                setContentView(detailsView);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
    }
}
