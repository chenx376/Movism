package me.chenyongrui.movism.mvp.presenters.impl;

import javax.inject.Inject;

import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.presenters.MovieListPresenter;
import me.chenyongrui.movism.mvp.views.MovieListView;
import me.chenyongrui.movism.repository.MovieListRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieListPresenterImpl implements MovieListPresenter {

    private final MovieListView view;

    private Subscription subscription = null;

    private MovieListRepository repository;


    @Inject
    public MovieListPresenterImpl(MovieListView view, MovieListRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void presentSearchedResult(String query, int page) {
        subscription = repository
                .getSearchedListData(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TMDbMovie>() {

                    boolean isEmptyResult = true;

                    @Override
                    public void onCompleted() {
                        if (isEmptyResult) {
                            if (view != null) {
                                view.onNoMoreMovieListData();
                            }
                        } else {
                            if (view != null) {
                                view.onMovieListDataLoadComplete();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TMDbMovie movie) {
                        isEmptyResult = false;
                        if (view != null) {
                            view.updateMovieListData(movie);
                        }
                    }
                });

    }

    @Override
    public void presentMovieListData(int movieListType, int page) {
        subscription = repository
                .getMovieListData(movieListType, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TMDbMovie>() {

                    boolean isEmptyResult = true;

                    @Override
                    public void onCompleted() {
                        if (isEmptyResult) {
                            if (view != null) {
                                view.onNoMoreMovieListData();
                            }
                        } else {
                            if (view != null) {
                                view.onMovieListDataLoadComplete();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TMDbMovie movie) {
                        isEmptyResult = false;
                        if (view != null) {
                            view.updateMovieListData(movie);
                        }
                    }
                });
    }

    public void unsubscribeRx() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
