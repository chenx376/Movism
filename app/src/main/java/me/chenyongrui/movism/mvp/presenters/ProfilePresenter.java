package me.chenyongrui.movism.mvp.presenters;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.view.activities.ProfileActivity;
import me.chenyongrui.movism.mvp.model.tmdb.MovieCreditsData;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;
import me.chenyongrui.movism.repository.CastCrewRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ProfilePresenter {

    private final ProfileActivity view;

    private Subscription subscription = null;
    private CastCrewRepository castCrewRepository;

    @Inject
    public ProfilePresenter(ProfileActivity view, CastCrewRepository castCrewRepository) {
        this.view = view;
        this.castCrewRepository = castCrewRepository;
    }

    public void unsubscribeRx() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    public void presentProfiletData(int profileID) {
        subscription = castCrewRepository
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

    public void presentCastCreditsData(int profileID) {
        subscription = castCrewRepository
                .getMovieCreditsData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieCreditsData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieCreditsData movieCreditsData) {
                        if (view != null) {
                            view.showCreditsData(movieCreditsData.getCast());
                        }
                    }
                });
    }


    public void presentCrewCreditsData(int profileID) {
        subscription = castCrewRepository
                .getMovieCreditsData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieCreditsData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieCreditsData movieCreditsData) {
                        if (view != null) {
                            view.showCreditsData(movieCreditsData.getCrew());
                        }
                    }
                });
    }
}
