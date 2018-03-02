package com.itrided.android.popularmovies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long DUMMY_SLEEP_MILLIS = 1000; //1 second

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setTheme(R.style.SplashThemeOreo);
            //todo add support for android 8+
        }
        super.onCreate(savedInstanceState);

        //todo remove delayed post and query the movies API
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            final Intent intent = new Intent(this, LibraryActivity.class);
            startActivity(intent);
            finish();
        }, DUMMY_SLEEP_MILLIS);
    }
    //endregion Overridden Methods
}
