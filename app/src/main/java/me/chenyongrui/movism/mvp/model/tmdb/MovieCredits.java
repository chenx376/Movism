package me.chenyongrui.movism.mvp.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieCredits implements Serializable {

    @SerializedName("cast")
    @Expose
    private List<CastMovieCredits> cast = null;
    @SerializedName("crew")
    @Expose
    private List<CrewMovieCredits> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = -2956840539493406192L;

    public List<CastMovieCredits> getCast() {
        return cast;
    }

    public void setCast(List<CastMovieCredits> cast) {
        this.cast = cast;
    }

    public List<CrewMovieCredits> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewMovieCredits> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}