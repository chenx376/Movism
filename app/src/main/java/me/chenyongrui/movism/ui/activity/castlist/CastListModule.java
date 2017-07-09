package me.chenyongrui.movism.ui.activity.castlist;


import android.content.Context;

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
    private CastListContract.View view;

    public CastListModule(CastListContract.View view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public CastListContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public CastListContract.Presenter providePresenter(CastListContract.View view, CastCrewRepository castCrewRepository) {
        return new CastListPresenter(view, castCrewRepository);
    }

    @Provides
    @ActivityScope
    OmniAdapter<Cast, CastViewHolder> provideCastAdapter(CastListContract.View view, @Named("CastViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>((Context) view, baseViewHolderFactory);
    }

    @Named("CastViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCastViewHolderFactory() {
        return new CastViewHolderFactory();
    }


}
