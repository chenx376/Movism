package me.chenyongrui.movism.ui.activity.moviedetail;


import dagger.Subcomponent;
import me.chenyongrui.movism.ui.ActivityScope;

@ActivityScope
@Subcomponent(modules = {MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);

}
