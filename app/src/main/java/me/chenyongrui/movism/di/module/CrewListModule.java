package me.chenyongrui.movism.di.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scopes.ActivityScope;
import me.chenyongrui.movism.mvp.presenters.CrewListPresenter;
import me.chenyongrui.movism.mvp.presenters.impl.CrewListPresenterImpl;
import me.chenyongrui.movism.mvp.views.CrewListView;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastsRepository;

@Module
public class CrewListModule {

    private CrewListView view;

    public CrewListModule(CrewListView view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public CrewListView provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public CrewListPresenter providePresenter(CrewListPresenterImpl presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public CastsRepository provideRepo(TMDbService tMDbService) {
        return new CastsRepository(tMDbService);
    }


}
