package me.chenyongrui.movism.ui.activity.profile;

import javax.inject.Inject;

import me.chenyongrui.movism.data.repository.CastCrewRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ProfilePresenter {

    private final ProfileActivity view;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private CastCrewRepository castCrewRepository;

    @Inject
    public ProfilePresenter(ProfileActivity view, CastCrewRepository castCrewRepository) {
        this.view = view;
        this.castCrewRepository = castCrewRepository;
    }

    public void unsubscribeRx() {
        mCompositeSubscription.unsubscribe();
    }

    public void presentProfiletData(int profileID) {
        mCompositeSubscription.add(castCrewRepository
                .getProfileData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                            if (view != null) {
                                view.showProfileData(profile);
                            }
                        }
                ));
    }

    public void presentCastCreditsData(int profileID) {
        mCompositeSubscription.add(castCrewRepository
                .getMovieCreditsData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieCreditsData -> {
                    if (view != null) {
                        view.showCreditsData(movieCreditsData.getCast());
                    }
                }));
    }


    public void presentCrewCreditsData(int profileID) {
        mCompositeSubscription.add(castCrewRepository
                .getMovieCreditsData(profileID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieCreditsData -> {
                            if (view != null) {
                                view.showCreditsData(movieCreditsData.getCrew());
                            }
                        }
                ));
    }
}
