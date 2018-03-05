package com.itrided.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itrided.android.popularmovies.adapters.LibraryAdapter;
import com.itrided.android.popularmovies.models.Movie;
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
    //todo choose these dynamically
    private static final int LIBRARY_GRID_COLUMNS = 2;
    //endregion Constants

    //region Fields
    @Nullable @BindView(R.id.library_rv) RecyclerView mLibraryRecyclerView;
    @Nullable @BindView(R.id.navigation) BottomNavigationView navigation;

    private LibraryAdapter libraryAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:

                return true;
            case R.id.navigation_top_rated:
                getTopRatedMovies();
                return true;
            case R.id.navigation_popular:

                return true;
        }
        return false;
    };
    //endregion Fields

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        libraryAdapter = new LibraryAdapter();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mLibraryRecyclerView.setAdapter(libraryAdapter);
        mLibraryRecyclerView.setLayoutManager(new GridLayoutManager(this, LIBRARY_GRID_COLUMNS));
    }
    //endregion Overridden Methods

    private void getTopRatedMovies() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request topRatedMoviesRequest = MovieDbUtils.getTopRatedMoviesRequest();
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create((SingleEmitter<Response> emitter) -> {
                            final Response response =
                                    okHttpClient.newCall(topRatedMoviesRequest).execute();

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
                                } catch (IOException e) {
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
}
