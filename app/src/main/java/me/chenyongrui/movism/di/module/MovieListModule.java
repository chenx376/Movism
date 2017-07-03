package me.chenyongrui.movism.di.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.presenters.MovieListPresenter;
import me.chenyongrui.movism.mvp.presenters.impl.MovieListPresenterImpl;
import me.chenyongrui.movism.mvp.views.MovieListView;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.MovieListRepository;

@Module
public class MovieListModule {

    private MovieListView view;

    public MovieListModule(MovieListView view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public MovieListView provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public MovieListPresenter providePresenter(MovieListPresenterImpl presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public MovieListRepository provideRepo(TMDbService tMDbService) {
        return new MovieListRepository(tMDbService);
    }


}
