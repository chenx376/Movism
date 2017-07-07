package me.chenyongrui.movism.data.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.service.OMDbService;
import me.chenyongrui.movism.data.api.service.TMDbService;


@Module
public class RepositoryModule {
    @Singleton
    @Provides
    public MovieDetailRepository provideMovieDetailRepository(TMDbService tMDbService, OMDbService oMDbService) {
        return new MovieDetailRepository(tMDbService, oMDbService);
    }

    @Singleton
    @Provides
    public CastCrewRepository provideCastsRepository(TMDbService tMDbService) {
        return new CastCrewRepository(tMDbService);
    }

    @Singleton
    @Provides
    public MovieListRepository provideRepo(TMDbService tMDbService) {
        return new MovieListRepository(tMDbService);
    }

}
