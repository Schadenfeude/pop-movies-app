package com.itrided.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.itrided.android.popularmovies.models.Movie;
import com.itrided.android.popularmovies.utils.JSONUtils;
import com.itrided.android.popularmovies.utils.MovieDbUtils;
import com.itrided.android.popularmovies.views.MovieDetailsView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Nullable
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    }
    //endregion Overridden Methods

    //todo remove this when local persistence is done
    private void loadMovieDetails() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        //todo this will be taken from persistence
        final Request movieRequest = MovieDbUtils.buildMovieDetailsRequest(Integer.toString(movieId));
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        final Movie[] movie = new Movie[1];
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
                                movie[0] = JSONUtils.parseMovieDetails(movieDetailsJson);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
    }
}
