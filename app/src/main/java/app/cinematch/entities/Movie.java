package app.cinematch.entities;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private String title;
    @SerializedName("genre_ids")
    private int[] genreIds;
    @SerializedName("original_language")
    private String orgLang;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("release_date")
    private String releaseDate;


    public String getTitle() {
        return title;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOrgLang() {
        return orgLang;
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
}
