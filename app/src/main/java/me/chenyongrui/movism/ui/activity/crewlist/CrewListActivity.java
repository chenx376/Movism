package me.chenyongrui.movism.ui.activity.crewlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.ui.activity.BaseActivity;
import me.chenyongrui.movism.ui.activity.profile.ProfileActivity;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolder;


public class CrewListActivity extends BaseActivity implements OmniAdapter.ItemClickListener<Crew>, CrewListContract.View {
    private String movieTitle;
    private int movieID;

    @Inject
    CrewListContract.Presenter presenter;
    @Inject
    OmniAdapter<Crew, CrewViewHolder> crewListAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.crew_recycler)
    RecyclerView crewRecycler;


    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new CrewListModule(this)).inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clearSubscription();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_list);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        if (intent != null) {
            movieTitle = intent.getStringExtra("movie_title");
            movieID = intent.getIntExtra("movie_id", -1);

            SetupToolBar(toolbar, movieTitle, null, true);

        }

        presenter.presentCrewListData(movieID);

    }

    @Override
    public void showCrewListData(List<Crew> crewList) {
        crewListAdapter.addData(crewList);
        setCrewListAdapter();

    }

    @Override
    public void setCrewListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        crewRecycler.setLayoutManager(linearLayoutManager);
        crewRecycler.setAdapter(crewListAdapter);
        crewListAdapter.setItemClickListener(this);
    }


    @Override
    public void onItemClicked(Crew crew) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile_name", crew.getName());
        intent.putExtra("profile_id", crew.getId());
        intent.putExtra("type", "crew");

        startActivity(intent);
    }
}