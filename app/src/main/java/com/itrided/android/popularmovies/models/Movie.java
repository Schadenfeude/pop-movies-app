package com.itrided.android.popularmovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Daniel on 2.03.18.
 */
@Entity
public class Movie {

    //todo WIP probably for next version
    @NonNull
    @PrimaryKey
    private int id;
    private String title;
    private String releaseDate;
    private String poster;
    private String backdrop;
    private String voteAvg;
    private String plotSynopsis;
//    private String language;
//    private String genres;

    public Movie() {
    }

    public Movie(int id, String title, String releaseDate, String poster, String backdrop,
                 String voteAvg, String plotSynopsis) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.backdrop = backdrop;
        this.voteAvg = voteAvg;
        this.plotSynopsis = plotSynopsis;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(String voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }
}
