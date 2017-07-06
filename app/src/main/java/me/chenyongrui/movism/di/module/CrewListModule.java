package me.chenyongrui.movism.di.module;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.model.tmdb.Crew;
import me.chenyongrui.movism.mvp.presenters.CrewListPresenter;
import me.chenyongrui.movism.mvp.view.activities.CrewListActivity;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CrewViewHolder;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CrewViewHolderFactory;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastCrewRepository;

@Module
public class CrewListModule {
    //-------------------------------     mvp     -----------------------
    private CrewListActivity view;

    public CrewListModule(CrewListActivity view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public CrewListActivity provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public CrewListPresenter providePresenter(CastCrewRepository castCrewRepository) {
        return new CrewListPresenter(view, castCrewRepository);
    }

    //-------------------------------     adapters     -----------------------
    @Provides
    @ActivityScope
    OmniAdapter<Crew, CrewViewHolder> provideCrewAdapter(CrewListActivity view, @Named("CrewViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view, baseViewHolderFactory);
    }

    @Named("CrewViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCrewViewHolderFactory() {
        return new CrewViewHolderFactory();
    }


    //-------------------------------     repositories     -----------------------
    @ActivityScope
    @Provides
    public CastCrewRepository provideRepo(TMDbService tMDbService) {
        return new CastCrewRepository(tMDbService);
    }


}
