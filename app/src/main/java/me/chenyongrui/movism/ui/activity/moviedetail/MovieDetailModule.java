package me.chenyongrui.movism.ui.activity.moviedetail;


import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.Cast;
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.data.repository.MovieDetailRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.CastViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.CastViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderLinearHorizontal;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderLinearHorizontalFactory;

@Module
public class MovieDetailModule {


    private MovieDetailContract.View view;

    public MovieDetailModule(MovieDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MovieDetailContract.View provideView() {
        return view;
    }

    @ActivityScope
    @Provides
    public MovieDetailContract.Presenter providePresenter(MovieDetailContract.View view, MovieDetailRepository movieDetailRepository, CastCrewRepository castCrewRepository) {
        return new MovieDetailPresenter(view, movieDetailRepository, castCrewRepository);
    }


    @Provides
    @ActivityScope
    OmniAdapter<TMDbMovie, MovieViewHolderLinearHorizontal> provideMovieAdapter(MovieDetailContract.View view, @Named("MovieViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>((Context) view, baseViewHolderFactory);
    }


    @Named("MovieViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideMovieViewHolderFactory() {
        return new MovieViewHolderLinearHorizontalFactory();
    }


    @Provides
    @ActivityScope
    OmniAdapter<Crew, CrewViewHolder> provideCrewAdapter(MovieDetailContract.View view, @Named("CrewViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>((Context) view, baseViewHolderFactory);
    }

    @Named("CrewViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCrewViewHolderFactory() {
        return new CrewViewHolderFactory();
    }

    @Provides
    @ActivityScope
    OmniAdapter<Cast, CastViewHolder> provideCastAdapter(MovieDetailContract.View view, @Named("CastViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>((Context) view, baseViewHolderFactory);
    }

    @Named("CastViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCastViewHolderFactory() {
        return new CastViewHolderFactory();
    }


}
