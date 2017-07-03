package me.chenyongrui.movism.mvp.presenters;


public interface MovieDetailPresenter {

    void unsubscribeRx();

    void presentMovieDetailData(int movieID);

    void presentMovieCastsData(int movieID);

    void presentSimilarMovieData(int movieID);

    void presentExtraRatingData(String movieTitle);
}
