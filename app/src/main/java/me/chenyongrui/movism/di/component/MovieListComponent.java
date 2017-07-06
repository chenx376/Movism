package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.MovieListModule;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.view.fragment.MovieListFragment;

@ActivityScope
@Subcomponent(modules = {MovieListModule.class})
public interface MovieListComponent {
    void inject(MovieListFragment fragment);
}
