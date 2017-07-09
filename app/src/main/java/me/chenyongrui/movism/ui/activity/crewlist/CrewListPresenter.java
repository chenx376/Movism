package me.chenyongrui.movism.ui.activity.crewlist;

import javax.inject.Inject;

import me.chenyongrui.movism.data.repository.CastCrewRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class CrewListPresenter implements CrewListContract.Presenter {

    private final CrewListContract.View view;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private CastCrewRepository castCrewRepository;


    @Inject
    public CrewListPresenter(CrewListContract.View view, CastCrewRepository castCrewRepository) {
        this.view = view;
        this.castCrewRepository = castCrewRepository;
    }

    @Override
    public void clearSubscription() {
        mCompositeSubscription.clear();
    }

    @Override
    public void presentCrewListData(int movieID) {
        mCompositeSubscription.add(castCrewRepository
                .getMovieCastsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castsData -> {
                    if (view != null) {
                        view.showCrewListData(castsData.getCrew());
                    }
                }));
    }

}
