package me.chenyongrui.movism.mvp.presenters.impl;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.model.tmdb.MovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;
import me.chenyongrui.movism.mvp.presenters.ProfilePresenter;
import me.chenyongrui.movism.mvp.views.ProfileView;
import me.chenyongrui.movism.repository.CastsRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ProfilePresenterImpl implements ProfilePresenter {

    private final ProfileView view;

    private Subscription subscription = null;
    private CastsRepository castsRepository;

    @Inject
    public ProfilePresenterImpl(ProfileView view, CastsRepository castsRepository) {
        this.view = view;
        this.castsRepository = castsRepository;
    }

    public void unsubscribeRx() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void presentProfiletData(int profileID) {
        subscription = castsRepository
                .getProfileData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Profile>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Profile profile) {
                        if (view != null) {
                            view.showProfileData(profile);
                        }
                    }
                });
    }

    @Override
    public void presentCastCreditsData(int profileID) {
        subscription = castsRepository
                .getMovieCreditsData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieCredits>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieCredits movieCredits) {
                        if (view != null) {
                            view.showCastCreditsData(movieCredits.getCast());
                        }
                    }
                });
    }


    @Override
    public void presentCrewCreditsData(int profileID) {
        subscription = castsRepository
                .getMovieCreditsData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieCredits>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieCredits movieCredits) {
                        if (view != null) {
                            view.showCrewCreditsData(movieCredits.getCrew());
                        }
                    }
                });
    }
}
