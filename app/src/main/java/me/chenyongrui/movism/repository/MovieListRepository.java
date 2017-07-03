package me.chenyongrui.movism.repository;

import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.network.TMDbService;
import rx.Observable;
import rx.functions.Func1;

public class MovieListRepository {

    private TMDbService mTMDbService;

    public MovieListRepository(TMDbService mTMDbService) {
        this.mTMDbService = mTMDbService;
    }


    public Observable<TMDbMovie> getMovieListData(final int movieListType, final int page) {
        Observable<TMDbMovieList> movieListObservable;
        switch (movieListType) {
            case 0: {
                movieListObservable = mTMDbService.getPopularMovies(page);
                break;
            }
            case 1: {
                movieListObservable = mTMDbService.getInTheaterMovies(page);
                break;
            }
            case 2: {
                movieListObservable = mTMDbService.getUpComingMovies(page);
                break;
            }
            default:
                return Observable.empty();
        }
        return movieListObservable.concatMap(new Func1<TMDbMovieList, Observable<TMDbMovie>>() {
            @Override
            public Observable<TMDbMovie> call(TMDbMovieList mTMDbMovieList) {
                return Observable.from(mTMDbMovieList.getTMDbMovieList());
            }
        });
    }

    public Observable<TMDbMovie> getSearchedListData(String query, int page) {
        Observable<TMDbMovieList> searchedListObservable = mTMDbService.searchMovies(query, page);
        return searchedListObservable.concatMap(new Func1<TMDbMovieList, Observable<TMDbMovie>>() {
            @Override
            public Observable<TMDbMovie> call(TMDbMovieList mTMDbMovieList) {
                return Observable.from(mTMDbMovieList.getTMDbMovieList());
            }
        });
    }
}
