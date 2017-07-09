package me.chenyongrui.movism.ui.fragment.movielist;

import javax.inject.Inject;

import me.chenyongrui.movism.data.repository.MovieListRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MovieListPresenter {

    private final MovieListFragment view;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private MovieListRepository repository;
    private Subscription lastSearchSubscription;


    @Inject
    public MovieListPresenter(MovieListFragment view, MovieListRepository repository) {
        this.view = view;
        this.repository = repository;
    }


    public void presentMovieListData(int movieListType, int page) {
        mCompositeSubscription.add(repository
                .getMovieListData(movieListType, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                    if (view != null) {
                        view.updateMovieListData(movieList);
                        if (movieList.getPage() == movieList.getTotalPages()) {
                            view.onNoMoreData();
                        }
                    }
                }));
    }


    public void presentSearchedResult(String query, int page) {
        lastSearchSubscription = repository
                .getSearchedListData(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                            if (view != null) {
                                view.updateMovieListData(movieList);
                                if (movieList.getPage() == movieList.getTotalPages()) {
                                    view.onNoMoreData();
                                }
                            }
                        }
                );
        mCompositeSubscription.add(lastSearchSubscription);
    }

    public void clearSubscription() {
        mCompositeSubscription.clear();
    }


    public void cleanSubscribe() {
        if (lastSearchSubscription != null) {
            mCompositeSubscription.remove(lastSearchSubscription);
        }
    }
}
