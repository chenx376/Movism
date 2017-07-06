package me.chenyongrui.movism.mvp.presenters;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.model.omdb.OMDbMovie;
import me.chenyongrui.movism.mvp.model.tmdb.CastsData;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.mvp.view.activities.MovieDetailActivity;
import me.chenyongrui.movism.repository.CastCrewRepository;
import me.chenyongrui.movism.repository.MovieDetailRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieDetailPresenter {

    private final MovieDetailActivity view;

    private Subscription subscriptionDetail;
    private Subscription subscriptionCasts;
    private Subscription subscriptionSimilar;
    private Subscription subscriptionExtraRating;
    private MovieDetailRepository movieDetailRepository;
    private CastCrewRepository castCrewRepository;

    @Inject
    public MovieDetailPresenter(MovieDetailActivity view
            , MovieDetailRepository movieDetailRepository
            , CastCrewRepository castCrewRepository) {
        this.view = view;
        this.movieDetailRepository = movieDetailRepository;
        this.castCrewRepository = castCrewRepository;
    }

    public void unsubscribeRx() {
        if (subscriptionDetail != null) {
            if (!subscriptionDetail.isUnsubscribed()) {
                subscriptionDetail.unsubscribe();
            }
        }
        if (subscriptionCasts != null) {
            if (!subscriptionCasts.isUnsubscribed()) {
                subscriptionCasts.unsubscribe();
            }
        }
        if (subscriptionSimilar != null) {
            if (!subscriptionSimilar.isUnsubscribed()) {
                subscriptionSimilar.unsubscribe();
            }
        }
        if (subscriptionExtraRating != null) {
            if (!subscriptionExtraRating.isUnsubscribed()) {
                subscriptionExtraRating.unsubscribe();
            }
        }
    }

    public void presentMovieDetailData(int movieID) {
        subscriptionDetail = movieDetailRepository
                .getMovieDetailData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TMDbMovieDetail>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TMDbMovieDetail movieDetail) {
                        if (view != null) {
                            view.showMovieDetail(movieDetail);
                        }
                    }
                });
    }

    public void presentMovieCastsData(int movieID) {
        subscriptionCasts = castCrewRepository
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
                            view.showCrewListData(castsData.getCrew());
                        }
                    }
                });
    }

    public void presentSimilarMovieData(int movieID) {
        subscriptionSimilar = movieDetailRepository
                .getSimilarMovieData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TMDbMovieList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TMDbMovieList movieList) {
                        if (view != null) {
                            view.showSimilarMovieListData(movieList);
                        }
                    }
                });
    }

    public void presentExtraRatingData(String movieTitle) {
        subscriptionExtraRating = movieDetailRepository
                .getMovieDetailData(movieTitle.replace(" ", "+"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OMDbMovie>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(OMDbMovie movie) {
                        if (view != null) {
                            view.showExtraRatingData(movie);
                        }
                    }
                });
    }
}
