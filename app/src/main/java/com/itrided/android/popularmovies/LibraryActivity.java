package com.itrided.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itrided.android.popularmovies.adapters.LibraryAdapter;
import com.itrided.android.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryActivity extends AppCompatActivity {

    //region Constants
    private static final int LIBRARY_GRID_COLUMNS = 3;
    //endregion Constants

    //region Fields
    @Nullable @BindView(R.id.library_rv) RecyclerView mLibraryRecyclerView;
    @Nullable @BindView(R.id.navigation) BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:

                return true;
            case R.id.navigation_top_rated:

                return true;
            case R.id.navigation_popular:

                return true;
        }
        return false;
    };
    //endregion Fields

    //region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mLibraryRecyclerView.setAdapter(new LibraryAdapter(new ArrayList<Movie>(){{
            add(new Movie());
        }}));
        mLibraryRecyclerView.setLayoutManager(new GridLayoutManager(this, LIBRARY_GRID_COLUMNS));
    }
    //endregion Overridden Methods
}
