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
import me.chenyongrui.movism.ui.activity.component.CastListComponent;
import me.chenyongrui.movism.ui.activity.component.CrewListComponent;
import me.chenyongrui.movism.ui.activity.component.MovieDetailComponent;
import me.chenyongrui.movism.ui.activity.component.ProfileComponent;
import me.chenyongrui.movism.ui.activity.module.CastListModule;
import me.chenyongrui.movism.ui.activity.module.CrewListModule;
import me.chenyongrui.movism.ui.activity.module.MovieDetailModule;
import me.chenyongrui.movism.ui.activity.module.ProfileModule;
import me.chenyongrui.movism.ui.fragment.component.MovieListComponent;
import me.chenyongrui.movism.ui.fragment.module.MovieListModule;

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
