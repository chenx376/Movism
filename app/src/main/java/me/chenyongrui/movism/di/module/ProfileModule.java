package me.chenyongrui.movism.di.module;


import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.di.scope.ActivityScope;
import me.chenyongrui.movism.mvp.model.tmdb.MovieCredits;
import me.chenyongrui.movism.mvp.presenters.ProfilePresenter;
import me.chenyongrui.movism.mvp.view.activities.ProfileActivity;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieCreditsViewHolder;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieCreditsViewHolderFactory;
import me.chenyongrui.movism.network.TMDbService;
import me.chenyongrui.movism.repository.CastCrewRepository;

@Module
public class ProfileModule {

    private ProfileActivity view;

    public ProfileModule(ProfileActivity view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public ProfileActivity provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public ProfilePresenter providePresenter(CastCrewRepository castCrewRepository) {
        return new ProfilePresenter(view, castCrewRepository);
    }

    @ActivityScope
    @Provides
    public CastCrewRepository provideRepo(TMDbService tMDbService) {
        return new CastCrewRepository(tMDbService);
    }

    @Provides
    @ActivityScope
    OmniAdapter<MovieCredits, MovieCreditsViewHolder> provideAdapter(ProfileActivity view, BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>(view, baseViewHolderFactory);
    }

    @Provides
    @ActivityScope
    BaseViewHolderFactory provideViewHolderFactory() {
        return new MovieCreditsViewHolderFactory();
    }

    @Provides
    @ActivityScope
    LinearLayoutManager provideLayoutManager(ProfileActivity view) {
        return new LinearLayoutManager(view);
    }

}
