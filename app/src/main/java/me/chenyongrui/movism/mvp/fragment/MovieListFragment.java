package me.chenyongrui.movism.mvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nukc.LoadMoreWrapper.LoadMoreAdapter;
import com.github.nukc.LoadMoreWrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.module.MovieListModule;
import me.chenyongrui.movism.mvp.activities.MovieDetailActivity;
import me.chenyongrui.movism.mvp.adapters.MovieListAdapter;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.presenters.MovieListPresenter;
import me.chenyongrui.movism.mvp.views.MovieListView;


public class MovieListFragment extends BaseFragment implements MovieListView, MovieListAdapter.MovieClickListener {
    @Inject
    MovieListPresenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler)
    RecyclerView movieListRecycler;

    private MovieListAdapter movieListAdapter;
    private List<TMDbMovie> movieList = new ArrayList<>();
    private int movieListType;
    private boolean isRefreshing = false;
    private LoadMoreAdapter loadMoreAdapter;
    private int pageNum;

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

        setSwipeRefreshLayout();

        setRecycleAdapter();

        refreshMovieListData();
        return view;
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new MovieListModule(this)).inject(this);
    }

    @Override
    public void updateMovieListData(TMDbMovie movie) {
        movieList.add(movie);
        movieListAdapter.notifyItemInserted(movieList.size() - 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribeRx();
    }


    private void setRecycleAdapter() {
        movieListAdapter = new MovieListAdapter(getActivity(), movieList);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        movieListRecycler.setLayoutManager(gridLayoutManager);
        movieListRecycler.setAdapter(movieListAdapter);
        movieListAdapter.setMovieClickListener(this);


        loadMoreAdapter = LoadMoreWrapper.with(movieListAdapter)
                .setFooterView(R.layout.item_footer_more_movie)
                .setNoMoreView(R.layout.item_footer_no_more_movie)
                .setListener(new LoadMoreAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
                        enabled.setLoadMoreEnabled(false);
                        pageNum++;
                        presenter.presentMovieListData(movieListType, pageNum);
                    }
                })
                .into(movieListRecycler);
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setDistanceToTriggerSync(300);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefreshing) {
                    showToast("Refreshing...please wait.");
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    refreshMovieListData();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void refreshMovieListData() {
        isRefreshing = true;
        loadMoreAdapter.setLoadMoreEnabled(false);

        movieList.clear();
        movieListAdapter.notifyDataSetChanged();
        //initialize page number, and refresh all movie list data
        pageNum = 1;
        presenter.presentMovieListData(movieListType, pageNum);
    }

    @Override
    public void onMovieListDataLoadComplete() {
        isRefreshing = false;
        loadMoreAdapter.setLoadMoreEnabled(true);
    }

    @Override
    public void onNoMoreMovieListData() {
        loadMoreAdapter.setShowNoMoreEnabled(true);
        loadMoreAdapter.setLoadMoreEnabled(false);
        movieListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieClicked(TMDbMovie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }


}