package app.cinematch.entities;

import com.google.gson.annotations.SerializedName;

public class Theater {
    @SerializedName("page")
    private int currentPage;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private Movie[] movies;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Movie[] getMovies() {
        return movies;
    }
}
