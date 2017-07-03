package me.chenyongrui.movism.mvp.views;

import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;

public interface MovieListView {
    void updateMovieListData(TMDbMovie movie);

    void onMovieListDataLoadComplete();

    void onNoMoreMovieListData();
}
