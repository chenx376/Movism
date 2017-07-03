package me.chenyongrui.movism.mvp.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TMDbMovieList {


    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<TMDbMovie> TMDbMovieList = null;
    private final static long serialVersionUID = 4992101053132428633L;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<TMDbMovie> getTMDbMovieList() {
        return TMDbMovieList;
    }

    public void setTMDbMovieList(List<TMDbMovie> TMDbMovieList) {
        this.TMDbMovieList = TMDbMovieList;
    }

}
