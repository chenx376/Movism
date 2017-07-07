package me.chenyongrui.movism.ui.activity;

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
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.data.api.model.tmdb.MovieCredits;
import me.chenyongrui.movism.data.api.model.tmdb.Profile;
import me.chenyongrui.movism.ui.activity.module.ProfileModule;
import me.chenyongrui.movism.ui.activity.presenter.ProfilePresenter;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieCreditsViewHolder;
import me.chenyongrui.movism.utils.Constant;


public class ProfileActivity extends BaseActivity implements OmniAdapter.ItemClickListener<MovieCredits> {
    @Inject
    ProfilePresenter presenter;
    @Inject
    OmniAdapter<MovieCredits, MovieCreditsViewHolder> creditsAdapter;

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

    public void showCreditsData(List<MovieCredits> credits) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        profileMovieList.setLayoutManager(linearLayoutManager);
        profileMovieList.setAdapter(creditsAdapter);
        creditsAdapter.setItemClickListener(this);
        creditsAdapter.addData(credits);
    }

    @Override
    public void onItemClicked(MovieCredits movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }
}