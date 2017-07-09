package me.chenyongrui.movism.ui.fragment.movielist;


import android.content.Context;

import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieList;

public interface MovieListContract {


    interface View {
        void refreshMovieListData();

        void setRecycleAdapter();

        void updateMovieListData(TMDbMovieList movieList);

        void onNoMoreData();

        void getSearchedResult(String query);

        void hideSoftKeyboard();

        Context getViewContext();

    }


    interface Presenter {
        void presentMovieListData(int movieListType, int page);

        void presentSearchedResult(String query, int page);

        void clearSubscription();

        void cleanSubscribe();
    }
}
