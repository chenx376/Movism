package me.chenyongrui.movism.mvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

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

import static android.content.Context.INPUT_METHOD_SERVICE;
import static me.chenyongrui.movism.R.id.search_list_recycler;


public class SearchListFragment extends BaseFragment implements MovieListView, MovieListAdapter.MovieClickListener {
    @Inject
    MovieListPresenter presenter;

    @BindView(R.id.layout_movie_list_empty)
    LinearLayout layoutMovieListEmpty;


    @BindView(search_list_recycler)
    RecyclerView searchListRecycler;


    private MovieListAdapter searchListAdapter;
    private List<TMDbMovie> movieList = new ArrayList<>();


    private LoadMoreAdapter loadMoreAdapter;
    private int pageNum;
    private String query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        ButterKnife.bind(this, view);

        setRecycleAdapter();
        return view;
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new MovieListModule(this)).inject(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeRx();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setRecycleAdapter() {
        searchListAdapter = new MovieListAdapter(getActivity(), movieList);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        searchListRecycler.setLayoutManager(gridLayoutManager);
        searchListRecycler.setAdapter(searchListAdapter);
        searchListAdapter.setMovieClickListener(this);

        loadMoreAdapter = LoadMoreWrapper.with(searchListAdapter)
                .setListener(new LoadMoreAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
                        enabled.setLoadMoreEnabled(false);
                        pageNum++;
                        presenter.presentSearchedResult(query, pageNum);
                        Log.d("debug", this.hashCode() + " " +
                                getClass().getSimpleName() + " onLoadMore!!!!");
                    }
                })
                .setFooterView(R.layout.item_footer_more_movie)
                .setNoMoreView(R.layout.item_footer_no_more_movie)
                .setLoadMoreEnabled(false)
                .setShowNoMoreEnabled(false)
                .into(searchListRecycler);

        //add this to avoid showing the load more view
        searchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateMovieListData(TMDbMovie movie) {
        movieList.add(movie);
        searchListAdapter.notifyItemInserted(movieList.size() - 1);
    }

    @Override
    public void onNoMoreMovieListData() {
        if (movieList.isEmpty()) {
            //show the no result view
            layoutMovieListEmpty.setVisibility(View.VISIBLE);
            searchListRecycler.setVisibility(View.GONE);
            loadMoreAdapter.setLoadMoreEnabled(false);
            loadMoreAdapter.setLoadMoreEnabled(false);
            searchListAdapter.notifyDataSetChanged();
        } else {
            //show the no more load view
            loadMoreAdapter.setShowNoMoreEnabled(true);
            loadMoreAdapter.setLoadMoreEnabled(false);
            searchListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMovieListDataLoadComplete() {
        if (movieList.isEmpty()) {
            layoutMovieListEmpty.setVisibility(View.VISIBLE);
            searchListRecycler.setVisibility(View.GONE);
            loadMoreAdapter.setLoadMoreEnabled(false);
            loadMoreAdapter.setShowNoMoreEnabled(false);
            searchListAdapter.notifyDataSetChanged();
        } else {
            layoutMovieListEmpty.setVisibility(View.GONE);
            searchListRecycler.setVisibility(View.VISIBLE);
            loadMoreAdapter.setLoadMoreEnabled(true);
            loadMoreAdapter.setShowNoMoreEnabled(false);
            searchListAdapter.notifyDataSetChanged();
        }
    }

    public void getSearchedResult(String query) {
        presenter.unsubscribeRx();

        movieList.clear();
        loadMoreAdapter.setLoadMoreEnabled(false);
        loadMoreAdapter.setShowNoMoreEnabled(false);
        searchListAdapter.notifyDataSetChanged();

        layoutMovieListEmpty.setVisibility(View.GONE);
        searchListRecycler.setVisibility(View.VISIBLE);

        this.query = query.trim().replace(" ", "-");

        //initialize page number, and refresh all movie list data
        pageNum = 1;
        presenter.presentSearchedResult(query, pageNum);
        hideSoftKeyboard();
    }

    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onMovieClicked(TMDbMovie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }
}