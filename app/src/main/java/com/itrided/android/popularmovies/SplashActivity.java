package com.itrided.android.popularmovies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.utils.ConnectivityUtils;
import com.itrided.android.popularmovies.utils.JSONUtils;
import com.itrided.android.popularmovies.utils.MovieDbUtils;
import com.itrided.android.popularmovies.utils.MovieLoader;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setTheme(R.style.SplashThemeOreo);
            //todo add support for android 8+
        }
        super.onCreate(savedInstanceState);

        if (ConnectivityUtils.isNetworkConnected(this)) {
            loadMoviesAndStartLibrary();
        } else {
            final Intent intent = new Intent(this, LibraryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void loadMoviesAndStartLibrary() {
        final Intent intent = new Intent(this, LibraryActivity.class);
        MovieLoader.loadMovies(MovieDbUtils.TOP_RATED, getObserver(intent));
    }

    private DisposableSingleObserver<Response> getObserver(Intent intent) {
        return new DisposableSingleObserver<Response>() {
            @Override
            public void onSuccess(Response response) {
                try {
                    final String topRatedMoviesJson = response.body().string();
                    final ArrayList<Movie> movies = JSONUtils.parseMovieList(topRatedMoviesJson);
                    intent.putParcelableArrayListExtra(LibraryActivity.MOVIES, movies);

                    startActivity(intent);
                    finish();
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }
    //endregion Overridden Methods
}
