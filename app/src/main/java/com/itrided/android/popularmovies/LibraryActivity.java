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

public class LibraryActivity extends AppCompatActivity {

    //region Constants
    //todo choose these dynamically or use different values for small, medium, large & xl screens
    private static final int LIBRARY_GRID_COLUMNS = 2;
    //endregion Constants

    //region Fields
    @Nullable
    @BindView(R.id.library_rv)
    RecyclerView mLibraryRecyclerView;
    @Nullable
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private LibraryAdapter libraryAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_top_rated:
                loadMovies(MovieDbUtils.TOP_RATED);
                return true;
            case R.id.navigation_popular:
                loadMovies(MovieDbUtils.POPULAR);
                return true;
            case R.id.navigation_favourite:
                // todo get actual favourite movies
                libraryAdapter.clear();
                return true;
        }
        return false;
    };
    private LibraryItemOnClickListener itemOnClickListener = movie -> {
        final Intent startDetailsIntent = new Intent(this, DetailActivity.class);
        startDetailsIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.getId());

        startActivity(startDetailsIntent);
    };
    //endregion Fields

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        libraryAdapter = new LibraryAdapter(itemOnClickListener);

        //todo load top rated movies beforehand
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mLibraryRecyclerView.setAdapter(libraryAdapter);
        mLibraryRecyclerView.setLayoutManager(new GridLayoutManager(this, LIBRARY_GRID_COLUMNS));
    }
    //endregion Overridden Methods

    //region Private Methods
    //todo move this somewhere else and refactor when local persistence is done
    private void loadMovies(@MovieDbUtils.MovieCategory String category) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request movieRequest = MovieDbUtils.buildMovieCategoryRequest(category);
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
                                String topRatedMoviesJson = "";
                                try {
                                    topRatedMoviesJson = response.body().string();
                                } catch (NullPointerException | IOException e) {
                                    e.printStackTrace();
                                }
                                final List<Movie> movies = JSONUtils.parseMovieList(topRatedMoviesJson);
                                libraryAdapter.setMovies(movies);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
    }
    //endregion Private Methods
}
