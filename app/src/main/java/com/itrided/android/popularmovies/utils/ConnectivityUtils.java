package com.itrided.android.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public final class ConnectivityUtils {

    private ConnectivityUtils() {
    }

    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null;
    }
}
