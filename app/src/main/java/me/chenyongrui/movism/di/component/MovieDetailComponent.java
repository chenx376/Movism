package me.chenyongrui.movism.di.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.di.module.MovieDetailModule;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.activities.MovieDetailActivity;

@ActivityScope
@Subcomponent(modules = {MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);

}
