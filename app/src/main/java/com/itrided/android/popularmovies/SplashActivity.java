package com.itrided.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long DUMMY_SLEEP_MILLIS = 1000; //1 second

    //region API Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            final Intent intent = new Intent(this, LibraryActivity.class);
            startActivity(intent);
            finish();
        }, DUMMY_SLEEP_MILLIS);
    }
//endregion API Methods
}
