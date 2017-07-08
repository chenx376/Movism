package me.chenyongrui.movism.ui.fragment.movielist;

import javax.inject.Inject;

import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.data.repository.MovieListRepository;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieListPresenter {

    private final MovieListFragment view;

    private Subscription subscription = null;

    private MovieListRepository repository;


    @Inject
    public MovieListPresenter(MovieListFragment view, MovieListRepository repository) {
        this.view = view;
        this.repository = repository;
    }


    public void presentMovieListData(int movieListType, int page) {
        subscription = repository
                .getMovieListData(movieListType, page)
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
                            view.updateMovieListData(movieList);
                            if (movieList.getPage() == movieList.getTotalPages()) {
                                view.onNoMoreData();
                            }
                        }
                    }
                });
    }


    public void presentSearchedResult(String query, int page) {
        subscription = repository
                .getSearchedListData(query, page)
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
                            view.updateMovieListData(movieList);
                            if (movieList.getPage() == movieList.getTotalPages()) {
                                view.onNoMoreData();
                            }
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
