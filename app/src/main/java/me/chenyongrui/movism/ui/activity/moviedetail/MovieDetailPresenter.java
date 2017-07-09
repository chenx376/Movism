package me.chenyongrui.movism.ui.activity.moviedetail;

import javax.inject.Inject;

import me.chenyongrui.movism.data.repository.CastCrewRepository;
import me.chenyongrui.movism.data.repository.MovieDetailRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private final MovieDetailContract.View view;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private MovieDetailRepository movieDetailRepository;
    private CastCrewRepository castCrewRepository;

    @Inject
    public MovieDetailPresenter(MovieDetailContract.View view
            , MovieDetailRepository movieDetailRepository
            , CastCrewRepository castCrewRepository) {
        this.view = view;
        this.movieDetailRepository = movieDetailRepository;
        this.castCrewRepository = castCrewRepository;
    }

    @Override
    public void clearSubscription() {
        mCompositeSubscription.clear();
    }

    @Override
    public void presentMovieDetailData(int movieID) {
        mCompositeSubscription.add(movieDetailRepository
                .getMovieDetailData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDetail -> {
                    if (view != null) {
                        view.showMovieDetail(movieDetail);
                    }
                }));
    }

    @Override
    public void presentMovieCastsData(int movieID) {
        mCompositeSubscription.add(castCrewRepository
                .getMovieCastsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castsData -> {
                    if (view != null) {
                        view.showCastListData(castsData.getCast());
                        view.showCrewListData(castsData.getCrew());
                    }
                }));
    }

    @Override
    public void presentSimilarMovieData(int movieID) {
        mCompositeSubscription.add(movieDetailRepository
                .getSimilarMovieData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                    if (view != null) {
                        view.showSimilarMovieListData(movieList);
                    }
                }));
    }

    @Override
    public void presentExtraRatingData(String movieTitle) {
        mCompositeSubscription.add(movieDetailRepository
                .getMovieDetailData(movieTitle.replace(" ", "+"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    if (view != null) {
                        view.showExtraRatingData(movie);
                    }
                }));
    }
}
