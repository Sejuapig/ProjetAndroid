package com.example.e149769s.Miniprojet2017;

/**
 * Created by E149769S on 03/04/17.
 */
public class Movie {
    private  String backdropPath;
    private  String originalTitle;
    private  int id;
    private  String popularity;
    private  String posterPath;
    private  String releaseDate;
    private  String title;

    public Movie(String backdropPath, String originalTitle, int id, String popularity, String posterPath, String releaseDate, String title){
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.id = id;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
    }

    private Movie(Builder builder) {
        backdropPath = builder.backdropPath;
        originalTitle = builder.originalTitle;
        id = builder.id;
        popularity = builder.popularity;
        posterPath = builder.posterPath;
        releaseDate = builder.releaseDate;
        title = builder.title;
    }


    public static class Builder {
        private String backdropPath;
        private String originalTitle;
        private int id;
        private String popularity;
        private String posterPath;
        private String releaseDate;
        private String title;

        public Builder(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public Builder setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
            return this;
        }

        public Builder setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPopularity(String popularity) {
            this.popularity = popularity;
            return this;
        }

        public Builder setPosterPath(String posterPath) {
            this.posterPath = posterPath;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }

    }

    public static Builder newBuilder(int id, String title) {
        return new Builder(id, title);
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
        return getTitle();
    }

}