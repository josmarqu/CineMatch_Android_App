package app.cinematch.entities;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private String title;
    @SerializedName("genre_ids")
    private int[] genreIds;
    private String overview;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("poster_path")
    private String posterPath;
    private double popularity;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private int movieId;


    public String getTitle() {
        return title;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getPopularity() {
        return popularity;
    }
}
