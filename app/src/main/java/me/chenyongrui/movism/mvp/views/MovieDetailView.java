package me.chenyongrui.movism.mvp.views;

import java.util.List;

import me.chenyongrui.movism.mvp.model.omdb.OMDbMovie;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.mvp.model.tmdb.Crew;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;

public interface MovieDetailView {

    void showMovieDetail(TMDbMovieDetail movieDetail);

    void showCastlistData(List<Cast> cast);

    void showCrewListData(List<Crew> crew);

    void showSimilarMovieListData(TMDbMovieList movieList);

    void showExtraRatingData(OMDbMovie omDbMovie);

}
