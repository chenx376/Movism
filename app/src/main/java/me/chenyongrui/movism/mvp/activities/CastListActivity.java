package me.chenyongrui.movism.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.module.CastListModule;
import me.chenyongrui.movism.mvp.adapters.CastListAdapter;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.mvp.presenters.CastListPresenter;
import me.chenyongrui.movism.mvp.views.CastListView;


public class CastListActivity extends BaseActivity implements CastListAdapter.ClickListener, CastListView {
    @Inject
    CastListPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cast_recycler)
    RecyclerView castRecycler;
    private String movieTitle;
    private CastListAdapter castListAdapter;

    private List<Cast> castList = new ArrayList<>();
    private int movieID;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new CastListModule(this)).inject(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribeRx();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_list);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            movieTitle = intent.getStringExtra("movie_title");
            movieID = intent.getIntExtra("movie_id", -1);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(movieTitle);
            }
        }

        presenter.presentCastListData(movieID);


    }

    @Override
    public void showCastListData(List<Cast> cast) {
        castList.addAll(cast);
        setCastListAdapter(cast);
    }

    private void setCastListAdapter(List<Cast> castList) {
        castListAdapter = new CastListAdapter(this, castList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        castRecycler.setLayoutManager(linearLayoutManager);
        castRecycler.setAdapter(castListAdapter);
        castListAdapter.setCastClickListener(this);
    }


    @Override
    public void onCastClicked(Cast cast) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile_name", cast.getName());
        intent.putExtra("profile_id", cast.getId());
        intent.putExtra("type", "cast");

        startActivity(intent);
    }
}