package me.chenyongrui.movism.ui.fragment.movielist;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.data.repository.MovieListRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderStaggeredGrid;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderStaggeredGridFactory;

@Module
public class MovieListModule {

    private MovieListContract.View view;

    public MovieListModule(MovieListContract.View view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public MovieListContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public MovieListContract.Presenter providePresenter(MovieListContract.View view, MovieListRepository repository) {
        return new MovieListPresenter(view, repository);
    }


    @Provides
    @ActivityScope
    OmniAdapter<TMDbMovie, MovieViewHolderStaggeredGrid> provideAdapter(MovieListContract.View view, BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view.getViewContext(), baseViewHolderFactory);
    }

    @Provides
    @ActivityScope
    BaseViewHolderFactory provideViewHolderFactory() {
        return new MovieViewHolderStaggeredGridFactory();
    }


}
