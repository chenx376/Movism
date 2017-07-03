package me.chenyongrui.movism.mvp.presenters.impl;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.model.omdb.OMDbMovie;
import me.chenyongrui.movism.mvp.model.tmdb.Casts;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.mvp.presenters.MovieDetailPresenter;
import me.chenyongrui.movism.mvp.views.MovieDetailView;
import me.chenyongrui.movism.repository.CastsRepository;
import me.chenyongrui.movism.repository.MovieDetailRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieDetailPresenterImpl implements MovieDetailPresenter {

    private final MovieDetailView view;

    private Subscription subscriptionDetail;
    private Subscription subscriptionCasts;
    private Subscription subscriptionSimilar;
    private Subscription subscriptionExtraRating;
    private MovieDetailRepository movieDetailRepository;
    private CastsRepository castsRepository;

    @Inject
    public MovieDetailPresenterImpl(MovieDetailView view
            , MovieDetailRepository movieDetailRepository
            , CastsRepository castsRepository) {
        this.view = view;
        this.movieDetailRepository = movieDetailRepository;
        this.castsRepository = castsRepository;
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

    @Override
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

    @Override
    public void presentMovieCastsData(int movieID) {
        subscriptionCasts = castsRepository
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
                            view.showCastlistData(casts.getCast());
                            view.showCrewListData(casts.getCrew());
                        }
                    }
                });
    }

    @Override
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

    @Override
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
