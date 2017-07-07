package me.chenyongrui.movism.ui.activity.module;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.activity.CrewListActivity;
import me.chenyongrui.movism.ui.activity.presenter.CrewListPresenter;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolderFactory;

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




}
