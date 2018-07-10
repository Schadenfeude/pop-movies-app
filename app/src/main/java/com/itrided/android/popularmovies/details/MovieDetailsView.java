package com.itrided.android.popularmovies.details;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.popularmovies.R;
import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.model.Review;
import com.itrided.android.popularmovies.model.Trailer;
import com.itrided.android.popularmovies.persistence.MovieContract;
import com.itrided.android.popularmovies.utils.ImageLoader;
import com.itrided.android.popularmovies.utils.JSONUtils;
import com.itrided.android.popularmovies.utils.MovieLoader;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.Response;

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

    @BindView(R.id.trailers_rv)
    RecyclerView rvTrailers;
    @BindView(R.id.reviews_rv)
    RecyclerView rvReviews;

    private Movie movie;
    private ContentResolver contentResolver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TrailerOnClickListener trailerListener;

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

        contentResolver = context.getContentResolver();
        fabFavorite.setOnClickListener(v -> toggleFavourite(contentResolver));
    }

    public void setMovieDetails(@NonNull Movie movieDetails) {
        final ImageLoader imageLoader = ImageLoader.getInstance(getContext());
        movie = movieDetails;

        imageLoader.loadImageIntoTarget(movieDetails.getBackdrop(), ivBackdrop);
        imageLoader.loadImageIntoTarget(movieDetails.getPoster(), ivPoster);
        toolbarLayout.setTitle(movieDetails.getTitle());
        tvPlotSynopsis.setText(movieDetails.getPlotSynopsis());
        tvRating.setText(movieDetails.getVoteAvg());
        tvReleaseDate.setText(movieDetails.getReleaseDate());
        refreshMovieFavoriteStatus(contentResolver);

        final String movieId = String.valueOf(movie.getId());
        addTrailers(movieId);
        addReviews(movieId);
    }

    public void setTrailerListener(TrailerOnClickListener trailerListener) {
        this.trailerListener = trailerListener;
    }

    public Movie getMovie() {
        return movie;
    }

    private void addTrailers(@NonNull String movieId) {
        MovieLoader.loadTrailers(movieId, getTrailerResponseObserver());
    }

    private DisposableSingleObserver<Response> getTrailerResponseObserver() {
        return new DisposableSingleObserver<Response>() {
            @Override
            public void onSuccess(Response response) {
                try {
                    final String movieTrailersJson = response.body().string();
                    //todo filter list by type to only trailers
                    final ArrayList<Trailer> trailersList = JSONUtils.parseTrailers(movieTrailersJson);
                    final TrailersAdapter trailersAdapter = new TrailersAdapter(trailersList, trailerListener);

                    rvTrailers.setAdapter(trailersAdapter);
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private void addReviews(@NonNull String movieId) {
        MovieLoader.loadReviews(movieId, getReviewResponseObserver());
    }

    private DisposableSingleObserver<Response> getReviewResponseObserver() {
        return new DisposableSingleObserver<Response>() {
            @Override
            public void onSuccess(Response response) {
                try {
                    final String movieReviewsJson = response.body().string();
                    final ArrayList<Review> reviewsList = JSONUtils.parseReviews(movieReviewsJson);
                    final ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsList);

                    rvReviews.setAdapter(reviewsAdapter);
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private void toggleFavourite(@NonNull ContentResolver contentResolver) {
        if (movie == null) {
            return;
        }

        if (movie.isFavourite()) {
            removeFromFavourites(contentResolver);
        } else {
            addToFavourites(contentResolver);
        }
    }

    private void removeFromFavourites(@NonNull ContentResolver contentResolver) {
        String stringId = Integer.toString(movie.getId());
        final Uri uri = MovieContract.MovieEntry.FAVOURITE_CONTENT_URI
                .buildUpon()
                .appendPath(stringId)
                .build();

        compositeDisposable
                .add(Single.create((SingleEmitter<Integer> emitter) -> {
                    int deleted = contentResolver.delete(uri, null, null);
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(deleted);
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Integer>() {
                            @Override
                            public void onSuccess(Integer deleted) {
                                if (deleted > 0) {
                                    setFavourite(false);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
    }

    private void addToFavourites(@NonNull ContentResolver contentResolver) {
        compositeDisposable
                .add(Single.create((SingleEmitter<Uri> emitter) -> {
                    Uri uri = contentResolver
                            .insert(MovieContract.MovieEntry.FAVOURITE_CONTENT_URI,
                                    buildMovieContentValues());
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(uri);
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (uri != null) {
                                    setFavourite(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
    }

    @NonNull
    private ContentValues buildMovieContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry._ID,
                movie.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_TITLE,
                movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW,
                movie.getPlotSynopsis());
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH,
                movie.getPoster());
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH,
                movie.getBackdrop());
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE,
                movie.getVoteAvg());
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE,
                movie.getReleaseDate());
        return contentValues;
    }

    private void setFavourite(boolean favourite) {
        movie.setFavourite(favourite);
        toggleFavouriteIcon(favourite);
    }

    private void toggleFavouriteIcon(boolean favourite) {
        if (favourite) {
            fabFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void refreshMovieFavoriteStatus(@NonNull ContentResolver contentResolver) {
        final Cursor cursor = contentResolver
                .query(MovieContract.MovieEntry.FAVOURITE_CONTENT_URI,
                        null,
                        MovieContract.MovieEntry._ID + "=?",
                        new String[]{Integer.toString(movie.getId())},
                        null);

        if (cursor != null) {
            setFavourite(cursor.getCount() > 0);
            cursor.close();
        } else {
            setFavourite(false);
        }
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
