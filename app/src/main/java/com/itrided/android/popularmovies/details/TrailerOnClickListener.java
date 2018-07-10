package com.itrided.android.popularmovies.details;

import android.support.annotation.NonNull;

import com.itrided.android.popularmovies.model.Trailer;

public interface TrailerOnClickListener {
    void onItemClicked(@NonNull Trailer trailer);
}
