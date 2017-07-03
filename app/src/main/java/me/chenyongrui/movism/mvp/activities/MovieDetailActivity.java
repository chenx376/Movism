package me.chenyongrui.movism.mvp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.module.MovieDetailModule;
import me.chenyongrui.movism.mvp.adapters.CastListAdapter;
import me.chenyongrui.movism.mvp.adapters.CrewListAdapter;
import me.chenyongrui.movism.mvp.adapters.SimilarMovieAdapter;
import me.chenyongrui.movism.mvp.fragment.OverviewFragment;
import me.chenyongrui.movism.mvp.model.omdb.OMDbMovie;
import me.chenyongrui.movism.mvp.model.omdb.Rating;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.mvp.model.tmdb.Crew;
import me.chenyongrui.movism.mvp.model.tmdb.Genre;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.mvp.presenters.MovieDetailPresenter;
import me.chenyongrui.movism.mvp.views.MovieDetailView;

import static me.chenyongrui.movism.R.id.detail_title;
import static me.chenyongrui.movism.R.id.movie_description;

public class MovieDetailActivity extends BaseActivity
        implements MovieDetailView, SimilarMovieAdapter.MovieClickListener, CrewListAdapter.ClickListener, CastListAdapter.ClickListener {
    @Inject
    MovieDetailPresenter presenter;

    @BindView(R.id.backdrop)
    ImageView backdropView;
    @BindView(R.id.movie_backdrop)
    FrameLayout movieBackdrop;
    @BindView(detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_tagline)
    TextView detailTagline;
    @BindView(R.id.detail_overview)
    TextView detailOverview;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.header_container)
    FrameLayout headerContainer;
    @BindView(R.id.imdb_rating)
    TextView imdbRating;
    @BindView(R.id.tomato_rating)
    TextView tomatoRating;
    @BindView(R.id.metacritic_rating)
    TextView metacriticRating;
    @BindView(R.id.tmdb_rating)
    TextView tmdbRating;
    @BindView(R.id.ratingBar)
    LinearLayout ratingBar;
    @BindView(R.id.detail_runtime)
    TextView detailRuntime;
    @BindView(R.id.detail_genre)
    TextView detailGenre;
    @BindView(R.id.detail_language)
    TextView detailLanguage;
    @BindView(R.id.detail_released)
    TextView detailReleased;
    @BindView(R.id.movie_extra_detail)
    RelativeLayout movieExtraDetail;
    @BindView(movie_description)
    LinearLayout movieDescription;
    @BindView(R.id.movie_main_info_container)
    RelativeLayout moviePosterContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_all_details_container)
    FrameLayout movieAllDetailsContainer;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.cast_bar)
    TextView castBar;
    @BindView(R.id.cast_more)
    TextView castMore;
    @BindView(R.id.cast_recycler)
    RecyclerView castRecycler;
    @BindView(R.id.cast_layout)
    CardView castLayout;
    @BindView(R.id.crew_bar)
    TextView crewBar;
    @BindView(R.id.crew_more)
    TextView crewMore;
    @BindView(R.id.crew_recycler)
    RecyclerView crewRecycler;
    @BindView(R.id.crew_layout)
    CardView crewLayout;
    @BindView(R.id.similar_movie_bar)
    TextView similarMovieBar;
    @BindView(R.id.similar_recycler)
    RecyclerView similarRecycler;
    @BindView(R.id.similar_layout)
    CardView similarLayout;

    private int movieID;
    private String movieTitle;
    private String backdropPath;


    private SimilarMovieAdapter similarMovieAdapter;
    private CrewListAdapter crewListAdapter;
    private CastListAdapter castListAdapter;


    private List<TMDbMovie> similarList = new ArrayList<>();
    private List<Cast> castList = new ArrayList<>();
    private List<Crew> crewList = new ArrayList<>();


    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new MovieDetailModule(this)).inject(this);
    }

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_QUALITY = "w500"; //"w92", "w154", "w185", "w342", "w500", "w780", or "original"

    protected String getRealPosterURL(String posterURL) {
        return BASE_IMAGE_URL + IMAGE_QUALITY + posterURL;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);


        // version >= 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Intent intent = getIntent();
        movieTitle = intent.getStringExtra("movie_title");
        movieID = intent.getIntExtra("movie_id", -1);
        detailTitle.setText(movieTitle);

        //present movie's information
        presenter.presentMovieDetailData(movieID);
        presenter.presentSimilarMovieData(movieID);
        presenter.presentMovieCastsData(movieID);

        presenter.presentExtraRatingData(movieTitle);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribeRx();
    }

    @Override
    public void showMovieDetail(TMDbMovieDetail movieDetail) {

        loadBackdropAndSetColorBar(movieDetail);

        detailTagline.setText(movieDetail.getTagline());
        detailLanguage.setText(movieDetail.getOriginalLanguage().toUpperCase());
        detailOverview.setText(movieDetail.getOverview());
        detailReleased.setText(movieDetail.getReleaseDate());
        detailRuntime.setText(movieDetail.getRuntime() + "");

        StringBuilder sb = new StringBuilder();
        for (Genre genre : movieDetail.getGenres()) {
            sb.append(", " + genre.getName());
        }
        if (sb.length() > 0) {
            detailGenre.setText(sb.toString().substring(1));
        }

        tmdbRating.setText(movieDetail.getVoteAverage() + "/10");
    }

    @Override
    public void showExtraRatingData(OMDbMovie omDbMovie) {
        metacriticRating.setText("N/A");
        imdbRating.setText("N/A");
        tomatoRating.setText("N/A");
        for (Rating rating : omDbMovie.getRatings()) {
            String source = rating.getSource();
            String value = rating.getValue();
            switch (source) {
                case "Internet Movie Database":
                    imdbRating.setText(value);
                    break;
                case "Rotten Tomatoes":
                    tomatoRating.setText(value);
                    break;
                case "Metacritic":
                    metacriticRating.setText(value);
                    break;
            }
        }
    }

    @Override
    public void showCrewListData(List<Crew> crew) {
        crewList.addAll(crew);
        if (crewList.size() > 3) {
            crewMore.setVisibility(View.VISIBLE);
            setCrewListAdapter(new ArrayList<>(crew.subList(0, 4)));
            crewRecycler.setVisibility(View.VISIBLE);
        } else if (crewList.size() == 0) {
            crewBar.setVisibility(View.GONE);
        } else {
            setCrewListAdapter(crew);
            crewRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCastlistData(List<Cast> cast) {
        castList.addAll(cast);
        if (castList.size() > 3) {
            castMore.setVisibility(View.VISIBLE);
            setCastListAdapter(new ArrayList<>(cast.subList(0, 4)));
            castRecycler.setVisibility(View.VISIBLE);
        } else if (castList.size() == 0) {
            castBar.setVisibility(View.GONE);
        } else {
            setCastListAdapter(cast);
            castRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showSimilarMovieListData(TMDbMovieList movieList) {
        setSimilarMovieAdapter();
        similarList.addAll(movieList.getTMDbMovieList());
        similarMovieAdapter.notifyDataSetChanged();
        similarRecycler.setVisibility(View.VISIBLE);
    }


    private void setCrewListAdapter(List<Crew> crewList) {
        crewListAdapter = new CrewListAdapter(this, crewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        crewRecycler.setLayoutManager(linearLayoutManager);
        crewRecycler.setAdapter(crewListAdapter);
        crewListAdapter.setCrewClickListener(this);
    }

    private void setCastListAdapter(List<Cast> castList) {
        castListAdapter = new CastListAdapter(this, castList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        castRecycler.setLayoutManager(linearLayoutManager);
        castRecycler.setAdapter(castListAdapter);
        castListAdapter.setCastClickListener(this);
    }

    private void setSimilarMovieAdapter() {
        similarMovieAdapter = new SimilarMovieAdapter(this, similarList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        similarRecycler.setLayoutManager(linearLayoutManager);
        similarRecycler.setAdapter(similarMovieAdapter);
        similarMovieAdapter.setMovieClickListener(this);
    }

    @Override
    public void onMovieClicked(TMDbMovie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        intent.putExtra("movie_title", movie.getTitle());
        startActivity(intent);
    }

    @Override
    public void onCrewClicked(Crew crew) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile_name", crew.getName());
        intent.putExtra("profile_id", crew.getId());
        intent.putExtra("type", "crew");
        startActivity(intent);
    }

    @Override
    public void onCastClicked(Cast cast) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("profile_name", cast.getName());
        intent.putExtra("profile_id", cast.getId());
        intent.putExtra("type", "cast");
        startActivity(intent);
    }


    @OnClick({R.id.header_container, R.id.movie_backdrop, R.id.cast_more, R.id.crew_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_container:
                if (detailOverview != null && !detailOverview.getText().toString().isEmpty()) {
                    OverviewFragment overviewFragment = new OverviewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("movie_title", movieTitle);
                    bundle.putString("movie_overview", detailOverview.getText().toString());
                    overviewFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_all_details_container, overviewFragment).addToBackStack("DESC").commit();
                }
                break;
            case R.id.movie_backdrop:
                if (backdropPath != null && !backdropPath.isEmpty()) {
                    Intent intent = new Intent(this, FullImageActivity.class);
                    intent.putExtra("movie_title", movieTitle);
                    intent.putExtra("backdrop_path", backdropPath);
                    startActivity(intent);
                }
                break;
            case R.id.cast_more:
                if (movieTitle != null && !movieTitle.isEmpty()) {
                    Intent intent = new Intent(this, CastListActivity.class);
                    intent.putExtra("movie_title", movieTitle);
                    intent.putExtra("movie_id", movieID);
                    startActivity(intent);
                }
                break;
            case R.id.crew_more:
                if (movieTitle != null && !movieTitle.isEmpty()) {
                    Intent intent = new Intent(this, CrewListActivity.class);
                    intent.putExtra("movie_title", movieTitle);
                    intent.putExtra("movie_id", movieID);
                    startActivity(intent);
                }
                break;
        }
    }


    private void loadBackdropAndSetColorBar(TMDbMovieDetail movieDetail) {
        backdropPath = movieDetail.getBackdropPath();

        progressWheel.setVisibility(View.VISIBLE);
        try {
            Glide.with(this)
                    .load(getRealPosterURL(backdropPath))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            backdropView.setImageBitmap(resource);
                            progressWheel.setVisibility(View.INVISIBLE);
                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    if (!isNightMode) {
                                        List<Palette.Swatch> swatchList = palette.getSwatches();
                                        if (swatchList != null) {
                                            int p = (int) (swatchList.size() * Math.random());
                                            Palette.Swatch swatch;
                                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                            Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
                                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                            Palette.Swatch dominantSwatch = palette.getDominantSwatch();

                                            swatch = swatchList.get(p++ % swatchList.size());
                                            swatch = dominantSwatch != null ? dominantSwatch : swatch;
                                            header.setBackgroundColor(swatch.getRgb());
                                            detailTitle.setTextColor(swatch.getTitleTextColor());
                                            detailOverview.setTextColor(swatch.getTitleTextColor());
                                            detailTagline.setTextColor(swatch.getTitleTextColor());

                                            swatch = swatchList.get(p++ % swatchList.size());
                                            swatch = vibrantSwatch != null ? vibrantSwatch : swatch;
                                            movieExtraDetail.setBackgroundColor(swatch.getRgb());
                                            detailRuntime.setTextColor(swatch.getTitleTextColor());
                                            detailGenre.setTextColor(swatch.getTitleTextColor());
                                            detailLanguage.setTextColor(swatch.getTitleTextColor());
                                            detailReleased.setTextColor(swatch.getTitleTextColor());


                                            swatch = swatchList.get(p++ % swatchList.size());
                                            swatch = lightMutedSwatch != null ? lightMutedSwatch : swatch;
                                            ratingBar.setBackgroundColor(swatch.getRgb());
                                            tmdbRating.setTextColor(swatch.getTitleTextColor());
                                            metacriticRating.setTextColor(swatch.getTitleTextColor());
                                            imdbRating.setTextColor(swatch.getTitleTextColor());
                                            tomatoRating.setTextColor(swatch.getTitleTextColor());


                                            swatch = swatchList.get(p++ % swatchList.size());
                                            swatch = darkVibrantSwatch != null ? darkVibrantSwatch : swatch;
                                            castBar.setBackgroundColor(swatch.getRgb());
                                            castMore.setBackgroundColor(swatch.getRgb());
                                            castBar.setTextColor(swatch.getTitleTextColor());
                                            castMore.setTextColor(swatch.getTitleTextColor());


                                            crewBar.setBackgroundColor(swatch.getRgb());
                                            crewMore.setBackgroundColor(swatch.getRgb());
                                            crewBar.setTextColor(swatch.getTitleTextColor());
                                            crewMore.setTextColor(swatch.getTitleTextColor());


                                            similarMovieBar.setBackgroundColor(swatch.getRgb());
                                            similarMovieBar.setTextColor(swatch.getTitleTextColor());

                                        }
                                    }
                                }
                            });
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
