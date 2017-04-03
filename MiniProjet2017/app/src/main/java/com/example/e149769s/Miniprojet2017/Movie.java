package com.example.e149769s.Miniprojet2017;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by E149769S on 03/04/17.
 */
public class Movie implements Parcelable {
    private  String backdropPath;
    private  String originalTitle;
    private  int id;
    private  String popularity;
    private  String posterPath;
    private  String releaseDate;
    private  String title;

    public Movie(String backdropPath, String originalTitle, int id, String popularity, String posterPath, String releaseDate, String title) {
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.id = id;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
    }


    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getId() {
        return id;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(backdropPath);
        parcel.writeString(originalTitle);
        parcel.writeString(String.valueOf(id));
        parcel.writeString(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(title);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel source)
        {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };

    public Movie(Parcel in) {
        this.backdropPath = in.readString();
        this.originalTitle = in.readString();
        this.id = Integer.parseInt(in.readString());
        this.popularity = in.readString();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
    }
}