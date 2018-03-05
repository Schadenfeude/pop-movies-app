package com.itrided.android.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.popularmovies.R;
import com.itrided.android.popularmovies.models.Movie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel on 2.03.18.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MovieViewHolder> {

    private Set<MovieViewHolder> viewHolders = new HashSet<>();
    private List<Movie> mMovies;

    public LibraryAdapter() {
    }

    //region Overridden Methods
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.content_library, null);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        viewHolders.add(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        holder.bind(movie);
    }

    @Override
    public void onViewRecycled(@NonNull MovieViewHolder holder) {
        viewHolders.remove(holder);
        super.onViewRecycled(holder);
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

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poster_iv) ImageView poster;
        @BindView(R.id.title_tv) TextView title;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Movie movie) {
            title.setText(movie.getTitle());
        }
    }
}
