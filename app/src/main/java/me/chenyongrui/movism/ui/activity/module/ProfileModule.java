package me.chenyongrui.movism.ui.activity.module;


import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.data.api.model.tmdb.MovieCredits;
import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.ui.ActivityScope;
import me.chenyongrui.movism.ui.activity.ProfileActivity;
import me.chenyongrui.movism.ui.activity.presenter.ProfilePresenter;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieCreditsViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieCreditsViewHolderFactory;

@Module
public class ProfileModule {
    //-------------------------------     mvp     -----------------------
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

    //-------------------------------     adapters     -----------------------

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


}
