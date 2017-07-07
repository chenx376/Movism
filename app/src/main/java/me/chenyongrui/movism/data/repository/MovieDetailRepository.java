package me.chenyongrui.movism.data.repository;

import me.chenyongrui.movism.data.api.service.OMDbService;
import me.chenyongrui.movism.data.api.service.TMDbService;
import me.chenyongrui.movism.data.api.model.omdb.OMDbMovie;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieList;
import rx.Observable;
import rx.functions.Func1;

public class MovieDetailRepository {
    private TMDbService mTMDbService;
    private OMDbService mOMDbService;

    public MovieDetailRepository(TMDbService mTMDbService, OMDbService mOMDbService) {
        this.mOMDbService = mOMDbService;
        this.mTMDbService = mTMDbService;
    }

    public Observable<TMDbMovieDetail> getMovieDetailData(int movieID) {
        String id = String.valueOf(movieID);
        return mTMDbService.getMovieDetail(id);
    }

    public Observable<TMDbMovieList> getSimilarMovieData(int movieID) {
        String id = String.valueOf(movieID);
        return mTMDbService.getSimilarMovies(id);
    }

    public Observable<OMDbMovie> getMovieDetailData(String movieTitle) {
        return mOMDbService.getOMDbMovie(movieTitle).filter(new Func1<OMDbMovie, Boolean>() {
            @Override
            public Boolean call(OMDbMovie omDbMovie) {
                return omDbMovie.getTitle() != null;
            }
        });
    }
}
