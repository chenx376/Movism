package me.chenyongrui.movism.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.module.CastListModule;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.mvp.presenters.CastListPresenter;
import me.chenyongrui.movism.mvp.view.adapters.OmniAdapter;
import me.chenyongrui.movism.mvp.view.adapters.viewholder.CastViewHolder;


public class CastListActivity extends BaseActivity implements OmniAdapter.ItemClickListener<Cast> {
    private String movieTitle;
    private int movieID;
    @Inject
    CastListPresenter presenter;
    @Inject
    OmniAdapter<Cast, CastViewHolder> castListAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cast_recycler)
    RecyclerView castRecycler;

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

    public void showCastListData(List<Cast> castList) {
        setCastListAdapter();
        castListAdapter.addData(castList);

    }

    private void setCastListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        castRecycler.setLayoutManager(linearLayoutManager);
        castRecycler.setAdapter(castListAdapter);
        castListAdapter.setItemClickListener(this);
    }


    @Override
    public void onItemClicked(Cast cast) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile_name", cast.getName());
        intent.putExtra("profile_id", cast.getId());
        intent.putExtra("type", "cast");

        startActivity(intent);
    }
}