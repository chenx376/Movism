package me.chenyongrui.movism.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.module.ProfileModule;
import me.chenyongrui.movism.mvp.adapters.CastCreditsAdapter;
import me.chenyongrui.movism.mvp.adapters.CrewCreditsAdapter;
import me.chenyongrui.movism.mvp.model.tmdb.CastMovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.CrewMovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;
import me.chenyongrui.movism.mvp.presenters.ProfilePresenter;
import me.chenyongrui.movism.mvp.views.ProfileView;
import me.chenyongrui.movism.util.Constant;


public class ProfileActivity extends BaseActivity implements ProfileView, CastCreditsAdapter.MovieClickListener, CrewCreditsAdapter.MovieClickListener {
    @Inject
    ProfilePresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profile_poster)
    CircularImageView profilePoster;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.birth)
    TextView birth;
    @BindView(R.id.birth_place)
    TextView birthPlace;
    @BindView(R.id.biography)
    TextView biography;
    @BindView(R.id.profile_movie_list)
    RecyclerView profileMovieList;

    private CastCreditsAdapter castCreditsAdapter;
    private CrewCreditsAdapter crewCreditsAdapter;

    private String type;
    private int profileID;

    protected String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.AVATAR_IMAGE_QUALITY + posterURL;
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new ProfileModule(this)).inject(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribeRx();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");

            profileID = intent.getIntExtra("profile_id", -1);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(intent.getStringExtra("profile_name"));
            }
        }
        presenter.presentProfiletData(profileID);
        if (type.equals("crew")) {
            presenter.presentCrewCreditsData(profileID);
        } else {
            presenter.presentCastCreditsData(profileID);
        }
    }

    @Override
    public void showProfileData(Profile profile) {
        try {
            Glide.with(this)
                    .load(getRealPosterURL(profile.getProfilePath()))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(profilePoster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        birth.setText(profile.getBirthday());
        birthPlace.setText(profile.getPlaceOfBirth());
        biography.setText(profile.getBiography());
        profileName.setText(profile.getName());
    }

    @Override
    public void showCastCreditsData(List<CastMovieCredits> castMovieCredits) {
        castCreditsAdapter = new CastCreditsAdapter(this, castMovieCredits);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        profileMovieList.setLayoutManager(linearLayoutManager);
        profileMovieList.setAdapter(castCreditsAdapter);
        castCreditsAdapter.setMovieClickListener(this);

    }

    @Override
    public void showCrewCreditsData(List<CrewMovieCredits> crewMovieCredits) {
        crewCreditsAdapter = new CrewCreditsAdapter(this, crewMovieCredits);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        profileMovieList.setLayoutManager(linearLayoutManager);
        profileMovieList.setAdapter(crewCreditsAdapter);
        crewCreditsAdapter.setMovieClickListener(this);
    }

    @Override
    public void onMovieClicked(CastMovieCredits movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }

    @Override
    public void onMovieClicked(CrewMovieCredits movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }
}