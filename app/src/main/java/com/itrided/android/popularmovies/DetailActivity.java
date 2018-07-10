package com.itrided.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.popularmovies.details.MovieDetailsView;
import com.itrided.android.popularmovies.details.TrailerOnClickListener;
import com.itrided.android.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "MOVIE";
    private static final String YOUTUBE_URI_SCHEME = "vnd.youtube://";

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
        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        detailsView = new MovieDetailsView(this);
        detailsView.setMovieDetails(movie);
        detailsView.setTrailerListener(getTrailerClickListener());

        setSupportActionBar(detailsView.getToolbar());
        setContentView(detailsView);
    }

    private TrailerOnClickListener getTrailerClickListener() {
        return trailer -> {
            final Uri trailerUri = Uri.parse(YOUTUBE_URI_SCHEME + trailer.getYouTubeKey());
            final Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);

            startActivity(intent);
        };
    }
}
