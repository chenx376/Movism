package me.chenyongrui.movism.mvp.presenters.impl;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.model.tmdb.Casts;
import me.chenyongrui.movism.mvp.presenters.CastListPresenter;
import me.chenyongrui.movism.mvp.views.CastListView;
import me.chenyongrui.movism.repository.CastsRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CastListPresenterImpl implements CastListPresenter {

    private final CastListView view;

    private Subscription subscription = null;
    private CastsRepository castsRepository;

    @Inject
    public CastListPresenterImpl(CastListView view, CastsRepository castsRepository) {
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
    public void presentCastListData(int movieID) {
        subscription = castsRepository
                .getMovieCastsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Casts>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Casts casts) {
                        if (view != null) {
                            view.showCastListData(casts.getCast());
                        }
                    }
                });
    }

}
