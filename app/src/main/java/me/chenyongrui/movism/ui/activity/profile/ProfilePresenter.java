package me.chenyongrui.movism.ui.activity.profile;

import javax.inject.Inject;

import me.chenyongrui.movism.data.repository.CastCrewRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private CastCrewRepository castCrewRepository;

    @Inject
    public ProfilePresenter(ProfileContract.View view, CastCrewRepository castCrewRepository) {
        this.view = view;
        this.castCrewRepository = castCrewRepository;
    }

    @Override
    public void clearSubscription() {
        mCompositeSubscription.clear();
    }

    @Override
    public void presentProfileData(int profileID) {
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

    @Override
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


    @Override
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
