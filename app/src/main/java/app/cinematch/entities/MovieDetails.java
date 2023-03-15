package app.cinematch.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {
    private List<Genre> genres;
    @SerializedName("production_countries")
    private List<ProdCtry> prodCtries;

    public List<Genre> getGenres() {
        return genres;
    }

    public List<ProdCtry> getProdCtries() {
        return prodCtries;
    }


    public static class Genre {
        private int id;
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class ProdCtry {
        private String iso_3166_1;
        private String name;

        public String getName() {
            return name;
        }
    }

}
