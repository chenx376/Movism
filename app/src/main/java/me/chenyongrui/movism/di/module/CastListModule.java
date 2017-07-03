package me.chenyongrui.movism.di.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.presenters.CastListPresenter;
import me.chenyongrui.movism.mvp.presenters.impl.CastListPresenterImpl;
import me.chenyongrui.movism.mvp.views.CastListView;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastsRepository;

@Module
public class CastListModule {

    private CastListView view;

    public CastListModule(CastListView view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public CastListView provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public CastListPresenter providePresenter(CastListPresenterImpl presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public CastsRepository provideRepo(TMDbService tMDbService) {
        return new CastsRepository(tMDbService);
    }


}
