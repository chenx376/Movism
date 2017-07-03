package me.chenyongrui.movism.mvp.presenters;


public interface MovieListPresenter {

    void presentMovieListData(int movieListType, int page);

    void unsubscribeRx();

     void presentSearchedResult(String query, int page);
}
