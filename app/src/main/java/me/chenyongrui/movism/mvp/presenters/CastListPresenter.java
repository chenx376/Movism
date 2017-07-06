package me.chenyongrui.movism.mvp.presenters;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.view.activities.CastListActivity;
import me.chenyongrui.movism.mvp.model.tmdb.CastsData;
import me.chenyongrui.movism.repository.CastCrewRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CastListPresenter {

    private final CastListActivity view;

    private Subscription subscription = null;
    private CastCrewRepository castCrewRepository;

    @Inject
    public CastListPresenter(CastListActivity view, CastCrewRepository castCrewRepository) {
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

    public void presentCastListData(int movieID) {
        subscription = castCrewRepository
                .getMovieCastsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CastsData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CastsData castsData) {
                        if (view != null) {
                            view.showCastListData(castsData.getCast());
                        }
                    }
                });
    }

}
