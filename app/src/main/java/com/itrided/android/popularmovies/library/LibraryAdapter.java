package com.itrided.android.popularmovies.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.popularmovies.R;
import com.itrided.android.popularmovies.model.Movie;
import com.itrided.android.popularmovies.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel on 2.03.18.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private LibraryItemOnClickListener clickListener;

    public LibraryAdapter(@NonNull LibraryItemOnClickListener clickListener) {
        this(null, clickListener);
    }

    public LibraryAdapter(@Nullable List<Movie> movies,
                          @NonNull LibraryItemOnClickListener clickListener) {
        this.movies = movies;
        this.clickListener = clickListener;
    }

    //region Overridden Methods
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.content_library, null);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        holder.bind(movie, clickListener);
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }
    //endregion Overridden Methods

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        this.movies = null;
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poster_iv)
        ImageView posterIv;
        @BindView(R.id.rating_tv)
        TextView ratingTv;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Movie movie, @NonNull LibraryItemOnClickListener clickListener) {
            ratingTv.setText(movie.getVoteAvg());
            loadPoster(movie.getPoster());
            itemView.setOnClickListener(v -> clickListener.onItemClicked(movie));
        }

        private void loadPoster(@Nullable String imageUrl) {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }
            final Context context = itemView.getContext();

            ImageLoader.getInstance(context)
                    .loadImageIntoTarget(imageUrl, posterIv);
        }
    }
}
