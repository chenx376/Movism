package me.chenyongrui.movism.di.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.presenters.ProfilePresenter;
import me.chenyongrui.movism.mvp.presenters.impl.ProfilePresenterImpl;
import me.chenyongrui.movism.mvp.views.ProfileView;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastsRepository;

@Module
public class ProfileModule {

    private ProfileView view;

    public ProfileModule(ProfileView view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public ProfileView provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public ProfilePresenter providePresenter(ProfilePresenterImpl presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public CastsRepository provideRepo(TMDbService tMDbService) {
        return new CastsRepository(tMDbService);
    }


}
