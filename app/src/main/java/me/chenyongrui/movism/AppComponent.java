package me.chenyongrui.movism;

import javax.inject.Singleton;

import dagger.Component;
import me.chenyongrui.movism.data.api.OMDbAPIModule;
import me.chenyongrui.movism.data.api.TMDbAPIModule;
import me.chenyongrui.movism.data.repository.RepositoryModule;
import me.chenyongrui.movism.ui.activity.AboutActivity;
import me.chenyongrui.movism.ui.activity.FullImageActivity;
import me.chenyongrui.movism.ui.activity.LicenseActivity;
import me.chenyongrui.movism.ui.activity.MainActivity;
import me.chenyongrui.movism.ui.activity.SettingsActivity;
import me.chenyongrui.movism.ui.activity.CastList.CastListComponent;
import me.chenyongrui.movism.ui.activity.CrewList.CrewListComponent;
import me.chenyongrui.movism.ui.activity.MovieDetail.MovieDetailComponent;
import me.chenyongrui.movism.ui.activity.Profile.ProfileComponent;
import me.chenyongrui.movism.ui.activity.CastList.CastListModule;
import me.chenyongrui.movism.ui.activity.CrewList.CrewListModule;
import me.chenyongrui.movism.ui.activity.MovieDetail.MovieDetailModule;
import me.chenyongrui.movism.ui.activity.Profile.ProfileModule;
import me.chenyongrui.movism.ui.fragment.MovieList.MovieListComponent;
import me.chenyongrui.movism.ui.fragment.MovieList.MovieListModule;

@Singleton
@Component(modules = {AppModule.class, OMDbAPIModule.class, TMDbAPIModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(MovismApp app);

    void inject(MainActivity activity);

    MovieListComponent plus(MovieListModule module);

    MovieDetailComponent plus(MovieDetailModule module);

    CastListComponent plus(CastListModule module);

    CrewListComponent plus(CrewListModule module);

    ProfileComponent plus(ProfileModule module);

    void inject(FullImageActivity fullImageActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(LicenseActivity licenseActivity);

    void inject(AboutActivity aboutActivity);
}
