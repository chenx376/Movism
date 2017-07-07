package me.chenyongrui.movism.ui.activity.component;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.activity.MovieDetailActivity;
import me.chenyongrui.movism.ui.activity.module.MovieDetailModule;

@ActivityScope
@Subcomponent(modules = {MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);

}
