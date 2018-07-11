package com.itrided.android.popularmovies.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.itrided.android.popularmovies.R;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;

/**
 * Created by Daniel on 6.03.18.
 */

public final class ImageLoader {

    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME_SEC = 2;

    private static ImageLoader instance;

    private final Picasso pablo;

    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            instance = new ImageLoader(context);
        }

        return instance;
    }

    public void loadImageIntoTarget(@NonNull String imageUrl, @NonNull ImageView imageView) {
        final Request imageRequest = MovieDbUtils.buildImageRequest(imageUrl);
        final Uri imageUri = Uri.parse(imageRequest.url().toString());

        pablo.load(imageUri)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_theaters)
                .into(imageView);
    }

    public void loadTrailerImageIntoTarget(@NonNull String youTubeKey, @NonNull ImageView imageView) {
        final Request imageRequest = MovieDbUtils.buildTrailerThumbnailRequest(youTubeKey);
        final Uri imageUri = Uri.parse(imageRequest.url().toString());

        pablo.load(imageUri)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_theaters)
                .into(imageView);
    }

    private static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME_SEC,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new PicassoThreadFactory());
    }

    private static class PicassoThreadFactory implements ThreadFactory {
        public Thread newThread(@NonNull Runnable r) {
            return new PicassoThread(r);
        }
    }

    private static class PicassoThread extends Thread {
        PicassoThread(Runnable r) {
            super(r);
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }

    private ImageLoader(Context context) {
        final int cacheSize = (int) Runtime.getRuntime().maxMemory() / 4;

        pablo = new Picasso.Builder(context)
                .memoryCache(new LruCache(cacheSize))
                .executor(getThreadPoolExecutor())
                .build();
    }
}
