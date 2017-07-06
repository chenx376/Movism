package me.chenyongrui.movism.di.module;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.mvp.presenters.CastListPresenter;
import me.chenyongrui.movism.mvp.view.activities.CastListActivity;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CastViewHolder;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CastViewHolderFactory;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastCrewRepository;

@Module
public class CastListModule {
    //-------------------------------     mvp     -----------------------
    private CastListActivity view;

    public CastListModule(CastListActivity view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public CastListActivity provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public CastListPresenter providePresenter(CastCrewRepository castCrewRepository) {
        return new CastListPresenter(view, castCrewRepository);
    }

    //-------------------------------     adapters     -----------------------
    @Provides
    @ActivityScope
    OmniAdapter<Cast, CastViewHolder> provideCastAdapter(CastListActivity view, @Named("CastViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view, baseViewHolderFactory);
    }

    @Named("CastViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCastViewHolderFactory() {
        return new CastViewHolderFactory();
    }

    //-------------------------------     repositories     -----------------------
    @ActivityScope
    @Provides
    public CastCrewRepository provideRepo(TMDbService tMDbService) {
        return new CastCrewRepository(tMDbService);
    }


}
