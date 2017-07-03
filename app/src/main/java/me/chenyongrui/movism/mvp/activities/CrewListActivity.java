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
import me.chenyongrui.movism.di.module.CrewListModule;
import me.chenyongrui.movism.mvp.adapters.CrewListAdapter;
import me.chenyongrui.movism.mvp.model.tmdb.Crew;
import me.chenyongrui.movism.mvp.presenters.CrewListPresenter;
import me.chenyongrui.movism.mvp.views.CrewListView;


public class CrewListActivity extends BaseActivity implements CrewListAdapter.ClickListener, CrewListView {
    @Inject
    CrewListPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.crew_recycler)
    RecyclerView crewRecycler;
    private String movieTitle;
    private CrewListAdapter crewListAdapter;

    private List<Crew> crewList = new ArrayList<>();
    private int movieID;


    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new CrewListModule(this)).inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribeRx();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_list);
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

        presenter.presentCrewListData(movieID);


    }

    @Override
    public void showCrewListData(List<Crew> crew) {
        crewList.addAll(crew);
        setCrewListAdapter(crew);
    }

    private void setCrewListAdapter(List<Crew> crewList) {
        crewListAdapter = new CrewListAdapter(this, crewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        crewRecycler.setLayoutManager(linearLayoutManager);
        crewRecycler.setAdapter(crewListAdapter);
        crewListAdapter.setCrewClickListener(this);
    }


    @Override
    public void onCrewClicked(Crew crew) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile_name", crew.getName());
        intent.putExtra("profile_id", crew.getId());
        intent.putExtra("type", "crew");

        startActivity(intent);
    }
}