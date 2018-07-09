package com.itrided.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.popularmovies.details.MovieDetailsView;
import com.itrided.android.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "MOVIE";

    private MovieDetailsView detailsView;

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDetailsView();
    }

    @Override
    public void finish() {
        final Intent data = new Intent();
        data.putExtra(EXTRA_MOVIE, detailsView.getMovie());

        setResult(RESULT_OK, data);
        super.finish();
    }

    //endregion Overridden Methods

    private void setupDetailsView() {
        detailsView = new MovieDetailsView(this);
        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        setSupportActionBar(detailsView.getToolbar());
        detailsView.setMovieDetails(movie);
        setContentView(detailsView);
    }
}
