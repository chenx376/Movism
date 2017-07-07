package me.chenyongrui.movism.ui.fragment.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.data.repository.MovieListRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderStaggeredGrid;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderStaggeredGridFactory;
import me.chenyongrui.movism.ui.fragment.MovieListFragment;
import me.chenyongrui.movism.ui.fragment.presenter.MovieListPresenter;

@Module
public class MovieListModule {

    //-------------------------------     mvp     -----------------------

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


    //-------------------------------     adapters     -----------------------
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
