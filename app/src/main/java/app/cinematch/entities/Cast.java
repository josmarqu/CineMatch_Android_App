package app.cinematch.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast {
    @SerializedName("cast")
    private List<Actor> actors;
    private List<Crew> crew;

    public static class Crew {
        private String name;
        private String job;

        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }
    }

    public static class Actor {
        private String name;
        private String character;

        public String getName() {
            return name;
        }

        public String getCharacter() {
            return character;
        }
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Crew> getCrew() {
        return crew;
    }
}
