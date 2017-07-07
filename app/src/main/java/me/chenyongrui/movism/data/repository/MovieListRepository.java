package me.chenyongrui.movism.data.repository;

import me.chenyongrui.movism.data.api.service.TMDbService;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieList;
import rx.Observable;

public class MovieListRepository {

    private TMDbService mTMDbService;

    public MovieListRepository(TMDbService mTMDbService) {
        this.mTMDbService = mTMDbService;
    }


    public Observable<TMDbMovieList> getMovieListData(final int movieListType, final int page) {
        switch (movieListType) {
            case 0: {
                return mTMDbService.getTopRatedMovies(page);
            }
            case 1: {
                return mTMDbService.getInTheaterMovies(page);
            }
            case 2: {
                return mTMDbService.getUpComingMovies(page);
            }
            default:
                return Observable.empty();
        }
    }

    public Observable<TMDbMovieList> getSearchedListData(String query, int page) {
        return mTMDbService.searchMovies(query, page);
    }
}
