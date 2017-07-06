package me.chenyongrui.movism.mvp.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieCreditsData implements Serializable {

    @SerializedName("cast")
    @Expose
    private List<MovieCredits> cast = null;
    @SerializedName("crew")
    @Expose
    private List<MovieCredits> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = -2956840539493406192L;

    public List<MovieCredits> getCast() {
        return cast;
    }

    public void setCast(List<MovieCredits> cast) {
        this.cast = cast;
    }

    public List<MovieCredits> getCrew() {
        return crew;
    }

    public void setCrew(List<MovieCredits> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}