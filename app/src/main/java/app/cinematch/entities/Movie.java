package app.cinematch.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    private String title;
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


    protected Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        posterPath = in.readString();
        popularity = in.readDouble();
        releaseDate = in.readString();
        movieId = in.readInt();
    }

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

    public String getTitle() {
        return title;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeDouble(voteAverage);
        parcel.writeString(posterPath);
        parcel.writeDouble(popularity);
        parcel.writeString(releaseDate);
        parcel.writeInt(movieId);
    }
}
