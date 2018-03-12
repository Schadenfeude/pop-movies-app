package com.itrided.android.popularmovies.library;

import android.support.annotation.NonNull;

import com.itrided.android.popularmovies.models.Movie;

/**
 * Created by Daniel on 12.03.18.
 */

public interface LibraryItemOnClickListener {
    void onItemClicked(@NonNull Movie movie);
}
