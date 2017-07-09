package me.chenyongrui.movism.ui.activity.crewlist;


import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolderFactory;

@Module
public class CrewListModule {
    private CrewListContract.View view;

    public CrewListModule(CrewListContract.View view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public CrewListContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public CrewListContract.Presenter providePresenter(CrewListContract.View view, CastCrewRepository castCrewRepository) {
        return new CrewListPresenter(view, castCrewRepository);
    }

    @Provides
    @ActivityScope
    OmniAdapter<Crew, CrewViewHolder> provideCrewAdapter(CrewListContract.View view, @Named("CrewViewHolderFactory") BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>((Context) view, baseViewHolderFactory);
    }

    @Named("CrewViewHolderFactory")
    @Provides
    @ActivityScope
    BaseViewHolderFactory provideCrewViewHolderFactory() {
        return new CrewViewHolderFactory();
    }




}
