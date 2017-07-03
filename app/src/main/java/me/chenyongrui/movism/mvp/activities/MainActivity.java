package me.chenyongrui.movism.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.mvp.adapters.MovismPagerAdapter;
import me.chenyongrui.movism.mvp.fragment.MovieListFragment;
import me.chenyongrui.movism.mvp.fragment.SearchListFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;
    @BindView(R.id.toolbar_scroller)
    FrameLayout toolbarScroller;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private SearchListFragment searchListFragment;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.app_name);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setSearchView();

    }


    private void setupViewPager(ViewPager viewPager) {
        MovieListFragment popularMovieFragment = MovieListFragment.newInstance(0);
        MovieListFragment inTheaterMovieFragment = MovieListFragment.newInstance(1);
        MovieListFragment upComingMovieFragment = MovieListFragment.newInstance(2);

        MovismPagerAdapter adapter = new MovismPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(popularMovieFragment, getString(R.string.trending));
        adapter.addFragment(inTheaterMovieFragment, getString(R.string.theatres));
        adapter.addFragment(upComingMovieFragment, getString(R.string.upcoming));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }


    private void setSearchView() {
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchListFragment != null) {
                    searchListFragment.getSearchedResult(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                tabLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);

                disableToolbarScrolling();

                searchListFragment = new SearchListFragment();
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.search_container, searchListFragment)
                        .commit();
            }

            @Override
            public void onSearchViewClosed() {

                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(searchListFragment)
                        .commit();

                enableToolbarScrolling();

                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
            }
        });
        materialSearchView.setVoiceSearch(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    materialSearchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchListFragment.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void disableToolbarScrolling() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbarScroller.getLayoutParams();
        params.setScrollFlags(0);
    }

    private void enableToolbarScrolling() {

        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) toolbarScroller.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

    }


}