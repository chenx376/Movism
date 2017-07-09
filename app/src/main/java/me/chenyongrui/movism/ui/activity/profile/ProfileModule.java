package me.chenyongrui.movism.ui.activity.profile;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.MovieCredits;
import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieCreditsViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieCreditsViewHolderFactory;

@Module
public class ProfileModule {
    private ProfileContract.View view;

    public ProfileModule(ProfileContract.View view) {
        this.view = view;
    }


    @ActivityScope
    @Provides
    public ProfileContract.View provideView() {
        return view;
    }


    @ActivityScope
    @Provides
    public ProfileContract.Presenter providePresenter(ProfileContract.View view, CastCrewRepository castCrewRepository) {
        return new ProfilePresenter(view, castCrewRepository);
    }

    @Provides
    @ActivityScope
    OmniAdapter<MovieCredits, MovieCreditsViewHolder> provideAdapter(ProfileContract.View view, BaseViewHolderFactory baseViewHolderFactory) {
        return new OmniAdapter<>((Context) view, baseViewHolderFactory);
    }

    @Provides
    @ActivityScope
    BaseViewHolderFactory provideViewHolderFactory() {
        return new MovieCreditsViewHolderFactory();
    }


}
