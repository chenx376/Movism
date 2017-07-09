package me.chenyongrui.movism;

import javax.inject.Singleton;

import dagger.Component;
import me.chenyongrui.movism.data.api.OMDbAPIModule;
import me.chenyongrui.movism.data.api.TMDbAPIModule;
import me.chenyongrui.movism.data.repository.RepositoryModule;
import me.chenyongrui.movism.ui.activity.MainActivity;
import me.chenyongrui.movism.ui.activity.castlist.CastListComponent;
import me.chenyongrui.movism.ui.activity.castlist.CastListModule;
import me.chenyongrui.movism.ui.activity.crewlist.CrewListComponent;
import me.chenyongrui.movism.ui.activity.crewlist.CrewListModule;
import me.chenyongrui.movism.ui.activity.moviedetail.MovieDetailComponent;
import me.chenyongrui.movism.ui.activity.moviedetail.MovieDetailModule;
import me.chenyongrui.movism.ui.activity.profile.ProfileComponent;
import me.chenyongrui.movism.ui.activity.profile.ProfileModule;
import me.chenyongrui.movism.ui.fragment.movielist.MovieListComponent;
import me.chenyongrui.movism.ui.fragment.movielist.MovieListModule;

@Singleton
@Component(modules = {
        AppModule.class,
        OMDbAPIModule.class,
        TMDbAPIModule.class,
        RepositoryModule.class
})
public interface AppComponent {
    void inject(MovismApp app);


    MovieListComponent plus(MovieListModule module);

    MovieDetailComponent plus(MovieDetailModule module);

    CastListComponent plus(CastListModule module);

    CrewListComponent plus(CrewListModule module);

    ProfileComponent plus(ProfileModule module);

    void inject(MainActivity mainActivity);
}
