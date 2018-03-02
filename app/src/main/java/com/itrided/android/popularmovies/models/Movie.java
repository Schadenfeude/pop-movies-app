package com.itrided.android.popularmovies.models;

/**
 * Created by Daniel on 2.03.18.
 */

public class Movie {

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

    public Movie(String title, String releaseDate, String poster, String voteAvg, String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.voteAvg = voteAvg;
        this.plotSynopsis = plotSynopsis;
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
