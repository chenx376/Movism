package me.chenyongrui.movism.ui.activity.castlist;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.Cast;
import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.CastViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.CastViewHolderFactory;

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



}
