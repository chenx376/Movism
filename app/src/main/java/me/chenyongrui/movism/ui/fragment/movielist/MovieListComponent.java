package me.chenyongrui.movism.ui.fragment.movielist;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;

@ActivityScope
@Subcomponent(modules = {MovieListModule.class})
public interface MovieListComponent {
    void inject(MovieListFragment fragment);
}
