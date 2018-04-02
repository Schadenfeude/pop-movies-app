package com.itrided.android.popularmovies.utils;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieLoader {

    @Nullable
    public static void loadMovies(@NonNull @MovieDbUtils.MovieCategory String category,
                                  @NonNull DisposableSingleObserver<Response> responseObserver) {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                Single
                        .create(getEmitter(category))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(responseObserver));
    }

    private static SingleOnSubscribe<Response> getEmitter(@Nullable String category) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request movieRequest = MovieDbUtils.buildMovieCategoryRequest(category);

        return emitter -> {
            final Response response = okHttpClient.newCall(movieRequest).execute();

            if (!emitter.isDisposed()) {
                emitter.onSuccess(response);
            }
        };
    }
}
