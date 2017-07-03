package me.chenyongrui.movism.repository;

import me.chenyongrui.movism.mvp.model.tmdb.Casts;
import me.chenyongrui.movism.mvp.model.tmdb.MovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;
import me.chenyongrui.movism.network.TMDbService;
import rx.Observable;

public class CastsRepository {
    private TMDbService mTMDbService;

    public CastsRepository(TMDbService mTMDbService) {
        this.mTMDbService = mTMDbService;
    }

    public Observable<Casts> getMovieCastsData(int movieID) {
        String id = String.valueOf(movieID);
        return mTMDbService.getMovieCasts(id);
    }

    public Observable<Profile> getProfileData(int profileID) {
        String id = String.valueOf(profileID);
        return mTMDbService.getProfile(id);
    }

    public Observable<MovieCredits> getMovieCreditsData(int profileID) {
        String id = String.valueOf(profileID);
        return mTMDbService.getMovieCredits(id);
    }

}