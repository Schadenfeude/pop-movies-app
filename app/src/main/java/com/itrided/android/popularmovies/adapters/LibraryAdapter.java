package com.itrided.android.popularmovies.adapters;

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
import com.itrided.android.popularmovies.library.LibraryItemOnClickListener;
import com.itrided.android.popularmovies.models.Movie;
import com.itrided.android.popularmovies.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel on 2.03.18.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    private LibraryItemOnClickListener mClickListener;


    public LibraryAdapter(@NonNull LibraryItemOnClickListener clickListener) {
        this(null, clickListener);
    }

    public LibraryAdapter(@Nullable List<Movie> movies,
                          @NonNull LibraryItemOnClickListener clickListener) {
        this.mMovies = movies;
        this.mClickListener = clickListener;
    }

    //region Overridden Methods
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.content_library, null);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        holder.bind(movie, mClickListener);
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }
    //endregion Overridden Methods

    public void setMovies(List<Movie> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        this.mMovies = null;
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poster_iv)
        ImageView poster;
        @BindView(R.id.rating_tv)
        TextView rating;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Movie movie, @NonNull LibraryItemOnClickListener clickListener) {
            rating.setText(movie.getVoteAvg());
            loadPoster(movie.getPoster());
            itemView.setOnClickListener(v -> clickListener.onItemClicked(movie));
        }

        private void loadPoster(@Nullable String imageUrl) {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }
            final Context context = itemView.getContext();

            ImageLoader.getInstance(context)
                    .loadImageIntoTarget(imageUrl, poster);
        }
    }
}
