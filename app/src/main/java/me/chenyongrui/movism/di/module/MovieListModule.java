package me.chenyongrui.movism.di.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.presenters.MovieListPresenter;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieViewHolderStaggeredGrid;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieViewHolderStaggeredGridFactory;
import me.chenyongrui.movism.mvp.view.fragment.MovieListFragment;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.MovieListRepository;

@Module
public class MovieListModule {

    private MovieListFragment view;

    public MovieListModule(MovieListFragment view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public MovieListFragment provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public MovieListPresenter providePresenter(MovieListRepository repository) {
        return new MovieListPresenter(view, repository);
    }

    @ActivityScope
    @Provides
    public MovieListRepository provideRepo(TMDbService tMDbService) {
        return new MovieListRepository(tMDbService);
    }


    @Provides
    @ActivityScope
    OmniAdapter<TMDbMovie, MovieViewHolderStaggeredGrid> provideAdapter(MovieListFragment view, BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view.getContext(), baseViewHolderFactory);
    }

    @Provides
    @ActivityScope
    BaseViewHolderFactory provideViewHolderFactory() {
        return new MovieViewHolderStaggeredGridFactory();
    }


}
