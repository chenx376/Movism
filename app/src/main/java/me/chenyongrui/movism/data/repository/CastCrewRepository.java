package me.chenyongrui.movism.data.repository;

import me.chenyongrui.movism.data.api.service.TMDbService;
import me.chenyongrui.movism.data.api.model.tmdb.CastsData;
import me.chenyongrui.movism.data.api.model.tmdb.MovieCreditsData;
import me.chenyongrui.movism.data.api.model.tmdb.Profile;
import rx.Observable;

public class CastCrewRepository {
    private TMDbService mTMDbService;

    public CastCrewRepository(TMDbService mTMDbService) {
        this.mTMDbService = mTMDbService;
    }

    public Observable<CastsData> getMovieCastsData(int movieID) {
        String id = String.valueOf(movieID);
        return mTMDbService.getCastsData(id);
    }

    public Observable<Profile> getProfileData(int profileID) {
        String id = String.valueOf(profileID);
        return mTMDbService.getProfile(id);
    }

    public Observable<MovieCreditsData> getMovieCreditsData(int profileID) {
        String id = String.valueOf(profileID);
        return mTMDbService.getMovieCreditsData(id);
    }

}