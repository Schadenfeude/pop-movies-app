package com.itrided.android.popularmovies.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.popularmovies.R;
import com.itrided.android.popularmovies.models.Movie;
import com.itrided.android.popularmovies.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel on 12.03.18.
 */

public class MovieDetailsView extends CoordinatorLayout {

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fabFavorite;

    @BindView(R.id.backdrop_iv)
    ImageView ivBackdrop;
    @BindView(R.id.poster_iv)
    ImageView ivPoster;
    @BindView(R.id.plot_synopsis_tv)
    TextView tvPlotSynopsis;
    @BindView(R.id.vote_avg_tv)
    TextView tvRating;
    @BindView(R.id.release_date_tv)
    TextView tvReleaseDate;

    public MovieDetailsView(Context context) {
        super(context);
        init(context);
    }

    public MovieDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MovieDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.activity_detail, this);
        ButterKnife.bind(this);
    }

    public void setMovieDetails(@NonNull Movie movieDetails) {
        final ImageLoader imageLoader = ImageLoader.getInstance(getContext());

        imageLoader.loadImageIntoTarget(movieDetails.getBackdrop(), ivBackdrop);
        imageLoader.loadImageIntoTarget(movieDetails.getPoster(), ivPoster);
        toolbarLayout.setTitle(movieDetails.getTitle());
        tvPlotSynopsis.setText(movieDetails.getPlotSynopsis());
        tvRating.setText(movieDetails.getVoteAvg());
        tvReleaseDate.setText(movieDetails.getReleaseDate());
    }

    public CollapsingToolbarLayout getToolbarLayout() {
        return toolbarLayout;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public FloatingActionButton getFabFavorite() {
        return fabFavorite;
    }

    public ImageView getIvBackdrop() {
        return ivBackdrop;
    }

    public ImageView getIvPoster() {
        return ivPoster;
    }

    public TextView getTvPlotSynopsis() {
        return tvPlotSynopsis;
    }

    public TextView getTvRating() {
        return tvRating;
    }

    public TextView getTvReleaseDate() {
        return tvReleaseDate;
    }
}
