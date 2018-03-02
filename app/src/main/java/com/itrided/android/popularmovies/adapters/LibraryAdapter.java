package com.itrided.android.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itrided.android.popularmovies.R;
import com.itrided.android.popularmovies.models.Movie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Daniel on 2.03.18.
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MovieViewHolder> {

    private Set<MovieViewHolder> viewHolders = new HashSet<>();
    private List<Movie> mMovies;

    public LibraryAdapter(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    //region Overridden Methods
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.content_library, parent);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        viewHolders.add(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public void onViewRecycled(@NonNull MovieViewHolder holder) {
        viewHolders.remove(holder);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    //endregion Overridden Methods

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        MovieViewHolder(View itemView) {
            super(itemView);
        }

        void bind() {
            itemView.setTag("placeholder");
        }
    }
}
