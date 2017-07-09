package me.chenyongrui.movism.ui.activity.castlist;

import javax.inject.Inject;

import me.chenyongrui.movism.data.repository.CastCrewRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class CastListPresenter {

    private final CastListActivity view;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private CastCrewRepository castCrewRepository;

    @Inject
    public CastListPresenter(CastListActivity view, CastCrewRepository castCrewRepository) {
        this.view = view;
        this.castCrewRepository = castCrewRepository;
    }

    public void unsubscribeRx() {
        mCompositeSubscription.unsubscribe();
    }

    public void presentCastListData(int movieID) {
        mCompositeSubscription.add(castCrewRepository
                .getMovieCastsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castsData -> {
                    if (view != null) {
                        view.showCastListData(castsData.getCast());
                    }
                }));
    }

}
