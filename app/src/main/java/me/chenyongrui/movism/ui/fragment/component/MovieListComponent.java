package me.chenyongrui.movism.ui.fragment.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.fragment.MovieListFragment;
import me.chenyongrui.movism.ui.fragment.module.MovieListModule;

@ActivityScope
@Subcomponent(modules = {MovieListModule.class})
public interface MovieListComponent {
    void inject(MovieListFragment fragment);
}
