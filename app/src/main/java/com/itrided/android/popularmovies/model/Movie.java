package com.itrided.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Daniel on 2.03.18.
 */
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private int id;
    private String title;
    private String releaseDate;
    private String poster;
    private String backdrop;
    private String voteAvg;
    private String plotSynopsis;
    private boolean favourite;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    //region Parcelable Implementation

    /**
     * This constructor's read order and {@link #writeToParcel(Parcel, int)}'s write order MUST match!
     *
     * @param in The Movie parcel
     */
    private Movie(@NonNull Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.voteAvg = in.readString();
        this.plotSynopsis = in.readString();
        this.favourite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
        dest.writeString(this.voteAvg);
        dest.writeString(this.plotSynopsis);
        dest.writeByte((byte) (this.favourite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
    //endregion Parcelable Implementation
}
