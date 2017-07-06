package me.chenyongrui.movism.di.module;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.mvp.model.tmdb.Crew;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.presenters.MovieDetailPresenter;
import me.chenyongrui.movism.mvp.view.activities.MovieDetailActivity;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CastViewHolder;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CastViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CrewViewHolder;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CrewViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieViewHolderLinearHorizontal;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieViewHolderLinearHorizontalFactory;
import me.chenyongrui.movism.network.OMDbService;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastCrewRepository;
import me.chenyongrui.movism.repository.MovieDetailRepository;

@Module
public class MovieDetailModule {

    //-------------------------------     mvp     -----------------------

    private MovieDetailActivity view;

    public MovieDetailModule(MovieDetailActivity view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MovieDetailActivity provideView() {
        return view;
    }

    @ActivityScope
    @Provides
    public MovieDetailPresenter providePresenter(MovieDetailRepository movieDetailRepository, CastCrewRepository castCrewRepository) {
        return new MovieDetailPresenter(view, movieDetailRepository, castCrewRepository);
    }


    //-------------------------------     adapters     -----------------------

    @Provides
    @ActivityScope
    OmniAdapter<TMDbMovie, MovieViewHolderLinearHorizontal> provideMovieAdapter(MovieDetailActivity view, @Named("MovieViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view, baseViewHolderFactory);
    }


    @Named("MovieViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideMovieViewHolderFactory() {
        return new MovieViewHolderLinearHorizontalFactory();
    }


    @Provides
    @ActivityScope
    OmniAdapter<Crew, CrewViewHolder> provideCrewAdapter(MovieDetailActivity view, @Named("CrewViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view, baseViewHolderFactory);
    }

    @Named("CrewViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCrewViewHolderFactory() {
        return new CrewViewHolderFactory();
    }


    @Provides
    @ActivityScope
    OmniAdapter<Cast, CastViewHolder> provideCastAdapter(MovieDetailActivity view, @Named("CastViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view, baseViewHolderFactory);
    }

    @Named("CastViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCastViewHolderFactory() {
        return new CastViewHolderFactory();
    }

//-------------------------------     repositories     -----------------------

    @ActivityScope
    @Provides
    public MovieDetailRepository provideMovieDetailRepository(TMDbService tMDbService, OMDbService oMDbService) {
        return new MovieDetailRepository(tMDbService, oMDbService);
    }

    @ActivityScope
    @Provides
    public CastCrewRepository provideCastsRepository(TMDbService tMDbService) {
        return new CastCrewRepository(tMDbService);
    }

}
