package com.itrided.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.popularmovies.details.MovieDetailsView;
import com.itrided.android.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "MOVIE";

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDetailsView();
    }
    //endregion Overridden Methods

    private void setupDetailsView() {
        final MovieDetailsView detailsView = new MovieDetailsView(this);
        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        setSupportActionBar(detailsView.getToolbar());
        detailsView.setMovieDetails(movie);
        setContentView(detailsView);
    }
}
