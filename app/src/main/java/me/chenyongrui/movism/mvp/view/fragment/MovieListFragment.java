package me.chenyongrui.movism.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.module.MovieListModule;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.mvp.presenters.MovieListPresenter;
import me.chenyongrui.movism.mvp.view.activities.MovieDetailActivity;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.MovieViewHolderStaggeredGrid;


public class MovieListFragment extends BaseFragment implements OmniAdapter.ItemClickListener<TMDbMovie>
        , SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    @Inject
    MovieListPresenter presenter;
    @Inject
    OmniAdapter<TMDbMovie, MovieViewHolderStaggeredGrid> movieAdapter;

    @BindView(R.id.movie_recycler)
    SuperRecyclerView movieRecycler;

    private int movieListType;
    private int pageNum;
    private String query;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new MovieListModule(this)).inject(this);
    }

    public static MovieListFragment newInstance(int movieListType) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt("Type", movieListType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieListType = getArguments().getInt("Type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        setRecycleAdapter();

        refreshMovieListData();

        return view;
    }

    private void refreshMovieListData() {
        movieAdapter.removeAllData();
        pageNum = 1;
        presenter.presentMovieListData(movieListType, pageNum);
    }

    private void setRecycleAdapter() {
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        movieRecycler.setLayoutManager(staggeredGridLayoutManager);
        movieRecycler.setAdapter(movieAdapter);
        movieAdapter.setItemClickListener(this);
        if (movieListType != -1) {
            movieRecycler.setRefreshListener(this);
        }
        movieRecycler.setupMoreListener(this, 1);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribeRx();
    }

    @Override
    public void onItemClicked(TMDbMovie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refreshMovieListData();
    }

    public void updateMovieListData(TMDbMovieList movieList) {
        movieAdapter.addData(movieList.getTMDbMovieList());
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        if (movieListType == -1) {
            presenter.presentSearchedResult(query, ++pageNum);
        } else {
            presenter.presentMovieListData(movieListType, ++pageNum);
        }
    }

    public void onNoMoreData() {
        //stop it;
        movieRecycler.setLoadingMore(true);
    }

    public void getSearchedResult(String query) {
        presenter.unsubscribeRx();
        movieAdapter.removeAllData();
        this.query = query.trim().replace(" ", "-");
        //initialize page number, and refresh all movie list data
        pageNum = 1;
        presenter.presentSearchedResult(query, pageNum);
        hideSoftKeyboard();
    }

    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}