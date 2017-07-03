package me.chenyongrui.movism.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.chenyongrui.movism.MovismApp;
import me.chenyongrui.movism.di.module.AppModule;
import me.chenyongrui.movism.di.module.CastListModule;
import me.chenyongrui.movism.di.module.CrewListModule;
import me.chenyongrui.movism.di.module.MovieDetailModule;
import me.chenyongrui.movism.di.module.MovieListModule;
import me.chenyongrui.movism.di.module.OMDbAPIModule;
import me.chenyongrui.movism.di.module.ProfileModule;
import me.chenyongrui.movism.di.module.TMDbAPIModule;
import me.chenyongrui.movism.mvp.activities.AboutActivity;
import me.chenyongrui.movism.mvp.activities.FullImageActivity;
import me.chenyongrui.movism.mvp.activities.LicenseActivity;
import me.chenyongrui.movism.mvp.activities.MainActivity;
import me.chenyongrui.movism.mvp.activities.SettingsActivity;

@Singleton
@Component(modules = {AppModule.class, OMDbAPIModule.class, TMDbAPIModule.class})
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
