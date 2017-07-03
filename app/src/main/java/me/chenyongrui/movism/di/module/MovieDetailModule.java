package me.chenyongrui.movism.di.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.presenters.MovieDetailPresenter;
import me.chenyongrui.movism.mvp.presenters.impl.MovieDetailPresenterImpl;
import me.chenyongrui.movism.mvp.views.MovieDetailView;
import me.chenyongrui.movism.network.OMDbService;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastsRepository;
import me.chenyongrui.movism.repository.MovieDetailRepository;

@Module
public class MovieDetailModule {

    private MovieDetailView view;

    public MovieDetailModule(MovieDetailView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MovieDetailView provideView() {
        return view;
    }

    @ActivityScope
    @Provides
    public MovieDetailPresenter providePresenter(MovieDetailPresenterImpl presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public MovieDetailRepository provideMovieDetailRepository(TMDbService tMDbService, OMDbService oMDbService) {
        return new MovieDetailRepository(tMDbService, oMDbService);
    }

    @ActivityScope
    @Provides
    public CastsRepository provideCastsRepository(TMDbService tMDbService) {
        return new CastsRepository(tMDbService);
    }


}
