package com.itrided.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itrided.android.popularmovies.library.LibraryAdapter;
import com.itrided.android.popularmovies.library.LibraryItemOnClickListener;
import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.utils.JSONUtils;
import com.itrided.android.popularmovies.utils.MovieDbUtils;
import com.itrided.android.popularmovies.utils.MovieLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.Response;

public class LibraryActivity extends AppCompatActivity {

    //region Constants
    public static final String MOVIES = "MOVIES";
    //endregion Constants

    //region Fields
    @Nullable
    @BindView(R.id.library_rv)
    RecyclerView mLibraryRecyclerView;
    @Nullable
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private LibraryAdapter libraryAdapter;
    //endregion Fields

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        setupAdapter();
        setupBottomNavigation();
        loadMovies();
    }
    //endregion Overridden Methods

    //region Private Methods
    private void setupAdapter() {
        final LibraryItemOnClickListener itemOnClickListener = movie -> {
            final Intent startDetailsIntent = new Intent(this, DetailActivity.class);
            startDetailsIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);

            startActivity(startDetailsIntent);
        };
        final int libraryGridColumns = getResources().getInteger(R.integer.library_column_count);

        libraryAdapter = new LibraryAdapter(itemOnClickListener);
        mLibraryRecyclerView.setAdapter(libraryAdapter);
        mLibraryRecyclerView.setLayoutManager(new GridLayoutManager(this, libraryGridColumns));
    }

    private void setupBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_top_rated:
                    MovieLoader.loadMovies(MovieDbUtils.TOP_RATED, getMovieResponseObserver());
                    return true;
                case R.id.navigation_popular:
                    MovieLoader.loadMovies(MovieDbUtils.POPULAR, getMovieResponseObserver());
                    return true;
                case R.id.navigation_favourite:
                    // todo get actual favourite movies
                    libraryAdapter.clear();
                    return true;
            }
            return false;
        });
    }

    private void loadMovies() {
        final ArrayList<Movie> movies = getIntent().getParcelableArrayListExtra(MOVIES);

        if (movies == null || movies.isEmpty()) {
            MovieLoader.loadMovies(MovieDbUtils.TOP_RATED, getMovieResponseObserver());
        } else {
            libraryAdapter.setMovies(movies);
        }
    }

    private DisposableSingleObserver<Response> getMovieResponseObserver() {
        return new DisposableSingleObserver<Response>() {
            @Override
            public void onSuccess(Response response) {
                try {
                    final String topRatedMoviesJson = response.body().string();
                    final List<Movie> movies = JSONUtils.parseMovieList(topRatedMoviesJson);

                    libraryAdapter.setMovies(movies);
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    //endregion Private Methods
}
