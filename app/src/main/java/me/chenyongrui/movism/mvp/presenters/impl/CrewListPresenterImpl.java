package me.chenyongrui.movism.mvp.presenters.impl;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.model.tmdb.Casts;
import me.chenyongrui.movism.mvp.presenters.CrewListPresenter;
import me.chenyongrui.movism.mvp.views.CrewListView;
import me.chenyongrui.movism.repository.CastsRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CrewListPresenterImpl implements CrewListPresenter {

    private final CrewListView view;

    private Subscription subscription = null;
    private CastsRepository castsRepository;


    @Inject
    public CrewListPresenterImpl(CrewListView view, CastsRepository castsRepository) {
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
    public void presentCrewListData(int movieID) {
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
                            view.showCrewListData(casts.getCrew());
                        }
                    }
                });
    }

}
