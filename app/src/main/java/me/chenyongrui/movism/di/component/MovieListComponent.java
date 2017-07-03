package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.MovieListModule;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.fragment.MovieListFragment;
import me.chenyongrui.movism.mvp.fragment.SearchListFragment;

@ActivityScope
@Subcomponent(modules = {MovieListModule.class})
public interface MovieListComponent {
    void inject(MovieListFragment fragment);

    void inject(SearchListFragment fragment);
}
