package me.chenyongrui.movism.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.ui.activity.module.MovieDetailModule;
import me.chenyongrui.movism.data.api.model.omdb.OMDbMovie;
import me.chenyongrui.movism.data.api.model.omdb.Rating;
import me.chenyongrui.movism.data.api.model.tmdb.Cast;
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.data.api.model.tmdb.Genre;
import me.chenyongrui.movism.data.api.model.tmdb.ProductionCompany;
import me.chenyongrui.movism.data.api.model.tmdb.ProductionCountry;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieList;
import me.chenyongrui.movism.ui.activity.presenter.MovieDetailPresenter;
import me.chenyongrui.movism.ui.adapters.OmniAdapter;
import me.chenyongrui.movism.ui.adapters.viewholder.CastViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.CrewViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.MovieViewHolderLinearHorizontal;
import me.chenyongrui.movism.ui.fragment.OverviewFragment;
import me.chenyongrui.movism.utils.Constant;

import static me.chenyongrui.movism.R.id.detail_title;


public class MovieDetailActivity extends BaseActivity implements OmniAdapter.ItemClickListener<Object> {


    @BindView(R.id.runtime_holder)
    TextView runtimeHolder;
    @BindView(R.id.genre_holder)
    TextView genreHolder;
    @BindView(R.id.language_holder)
    TextView languageHolder;
    @BindView(R.id.release_holder)
    TextView releaseHolder;
    @BindView(R.id.budget_holder)
    TextView budgetHolder;
    @BindView(R.id.detail_budget)
    TextView detailBudget;
    @BindView(R.id.production_companies_holder)
    TextView productionCompaniesHolder;
    @BindView(R.id.detail_production_companies)
    TextView detailProductionCompanies;
    @BindView(R.id.production_countries_holder)
    TextView productionCountriesHolder;
    @BindView(R.id.detail_production_countries)
    TextView detailProductionCountries;
    private int movieID;
    private String movieTitle;
    private String backdropPath;

    @Inject
    MovieDetailPresenter presenter;

    @Inject
    OmniAdapter<TMDbMovie, MovieViewHolderLinearHorizontal> similarMovieAdapter;

    @Inject
    OmniAdapter<Crew, CrewViewHolder> crewListAdapter;

    @Inject
    OmniAdapter<Cast, CastViewHolder> castListAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.backdrop)
    ImageView backdropView;
    @BindView(detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_tagline)
    TextView detailTagline;
    @BindView(R.id.detail_overview)
    TextView detailOverview;
    @BindView(R.id.header_container)
    LinearLayout headerContainer;
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
    LinearLayout movieExtraDetail;


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


    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new MovieDetailModule(this)).inject(this);
    }

    private static final String IMAGE_QUALITY = "w500";

    protected String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + IMAGE_QUALITY + posterURL;
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
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }


        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            movieTitle = intent.getStringExtra("movie_title");
            movieID = intent.getIntExtra("movie_id", -1);
            detailTitle.setText(movieTitle);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                collapsingToolbar.setTitle(movieTitle);
                collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            }
        }


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

    public void showMovieDetail(TMDbMovieDetail movieDetail) {
        loadBackdropAndSetColorBar(movieDetail);
        detailTagline.setText(movieDetail.getTagline());
        detailOverview.setText(movieDetail.getOverview());


        detailLanguage.setText(movieDetail.getOriginalLanguage().toUpperCase());
        detailReleased.setText(movieDetail.getReleaseDate());
        detailRuntime.setText(movieDetail.getRuntime() + "mins");
        detailBudget.setText(movieDetail.getRuntime() + "dollars");


        StringBuilder sb = new StringBuilder();
        for (Genre genre : movieDetail.getGenres()) {
            sb.append(", " + genre.getName());
        }
        if (sb.length() > 0) {
            detailGenre.setText(sb.toString().substring(1));
        }

        sb = new StringBuilder();
        for (ProductionCountry country : movieDetail.getProductionCountries()) {
            sb.append(", " + country.getName().toUpperCase());
        }
        if (sb.length() > 0) {
            detailProductionCountries.setText(sb.toString().substring(1));
        }

        sb = new StringBuilder();
        for (ProductionCompany company : movieDetail.getProductionCompanies()) {
            sb.append(", " + company.getName().toUpperCase());
        }
        if (sb.length() > 0) {
            detailProductionCompanies.setText(sb.toString().substring(1));
        }


        tmdbRating.setText(movieDetail.getVoteAverage() + "/10");
    }

    public void showExtraRatingData(OMDbMovie omDbMovie) {
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

    public void showCrewListData(List<Crew> crewList) {
        if (crewList.size() > 3) {
            crewMore.setVisibility(View.VISIBLE);
            setCrewListAdapter();
            crewListAdapter.addData(crewList.subList(0, 4));
            crewRecycler.setVisibility(View.VISIBLE);
        } else if (crewList.size() == 0) {
            crewBar.setVisibility(View.GONE);
        } else {
            setCrewListAdapter();
            crewListAdapter.addData(crewList);
            crewRecycler.setVisibility(View.VISIBLE);
        }
    }

    public void showCastListData(List<Cast> castList) {
        if (castList.size() > 3) {
            castMore.setVisibility(View.VISIBLE);
            setCastListAdapter();
            castListAdapter.addData(castList.subList(0, 4));
            castRecycler.setVisibility(View.VISIBLE);
        } else if (castList.size() == 0) {
            castBar.setVisibility(View.GONE);
        } else {
            setCastListAdapter();
            castListAdapter.addData(castList);
            castRecycler.setVisibility(View.VISIBLE);
        }
    }

    public void showSimilarMovieListData(TMDbMovieList movieList) {
        setSimilarMovieAdapter();
        similarMovieAdapter.addData(movieList.getTMDbMovieList());
        similarRecycler.setVisibility(View.VISIBLE);
    }


    private void setCrewListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        crewRecycler.setLayoutManager(linearLayoutManager);
        crewRecycler.setAdapter(crewListAdapter);
        crewListAdapter.setItemClickListener(this);
    }

    private void setCastListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        castRecycler.setLayoutManager(linearLayoutManager);
        castRecycler.setAdapter(castListAdapter);
        castListAdapter.setItemClickListener(this);
    }

    private void setSimilarMovieAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        similarRecycler.setLayoutManager(linearLayoutManager);
        similarRecycler.setAdapter(similarMovieAdapter);
        similarMovieAdapter.setItemClickListener(this);
    }

    @OnClick({R.id.header_container, R.id.backdrop, R.id.cast_more, R.id.crew_more})
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
                            .replace(R.id.movie_detail_container, overviewFragment).addToBackStack("DESC").commit();
                }
                break;
            case R.id.backdrop:
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
                                            Palette.Swatch swatch;
                                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                            Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                            Palette.Swatch dominantSwatch = palette.getDominantSwatch();
                                            Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();


                                            swatch = swatchList.get((int) (swatchList.size() * Math.random()));
                                            swatch = vibrantSwatch != null ? vibrantSwatch : swatch;
                                            collapsingToolbar.setCollapsedTitleTextColor(swatch.getTitleTextColor());
                                            collapsingToolbar.setContentScrimColor(swatch.getRgb());

                                            final Drawable arrow = ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_arrow_back);
                                            arrow.setColorFilter(swatch.getTitleTextColor(), PorterDuff.Mode.SRC_ATOP);
                                            getSupportActionBar().setHomeAsUpIndicator(arrow);

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                getWindow().setStatusBarColor(swatch.getTitleTextColor());
                                            }
                                            swatch = swatchList.get((int) (swatchList.size() * Math.random()));
                                            swatch = lightVibrantSwatch != null ? lightVibrantSwatch : swatch;
                                            headerContainer.setBackgroundColor(swatch.getRgb());
                                            detailTitle.setTextColor(swatch.getTitleTextColor());
                                            detailTagline.setTextColor(swatch.getTitleTextColor());
                                            detailOverview.setTextColor(swatch.getBodyTextColor());


                                            swatch = swatchList.get((int) (swatchList.size() * Math.random()));
                                            swatch = darkVibrantSwatch != null ? darkVibrantSwatch : swatch;
                                            movieExtraDetail.setBackgroundColor(swatch.getRgb());

                                            runtimeHolder.setTextColor(swatch.getTitleTextColor());
                                            genreHolder.setTextColor(swatch.getTitleTextColor());
                                            releaseHolder.setTextColor(swatch.getTitleTextColor());
                                            languageHolder.setTextColor(swatch.getTitleTextColor());
                                            budgetHolder.setTextColor(swatch.getTitleTextColor());
                                            productionCompaniesHolder.setTextColor(swatch.getTitleTextColor());
                                            productionCountriesHolder.setTextColor(swatch.getTitleTextColor());

                                            detailBudget.setTextColor(swatch.getBodyTextColor());
                                            detailProductionCompanies.setTextColor(swatch.getBodyTextColor());
                                            detailProductionCountries.setTextColor(swatch.getBodyTextColor());
                                            detailRuntime.setTextColor(swatch.getBodyTextColor());
                                            detailGenre.setTextColor(swatch.getBodyTextColor());
                                            detailLanguage.setTextColor(swatch.getBodyTextColor());
                                            detailReleased.setTextColor(swatch.getBodyTextColor());

                                            swatch = swatchList.get((int) (swatchList.size() * Math.random()));
                                            swatch = darkMutedSwatch != null ? darkMutedSwatch : swatch;
                                            ratingBar.setBackgroundColor(swatch.getRgb());
                                            tmdbRating.setTextColor(swatch.getTitleTextColor());
                                            metacriticRating.setTextColor(swatch.getTitleTextColor());
                                            imdbRating.setTextColor(swatch.getTitleTextColor());
                                            tomatoRating.setTextColor(swatch.getTitleTextColor());


                                            swatch = swatchList.get((int) (swatchList.size() * Math.random()));
                                            swatch = dominantSwatch != null ? dominantSwatch : swatch;
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


    public void onItemClicked(Object object) {
        if (object instanceof Cast) {
            Cast cast = (Cast) object;
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("profile_name", cast.getName());
            intent.putExtra("profile_id", cast.getId());
            intent.putExtra("type", "cast");
            startActivity(intent);
        } else if (object instanceof Crew) {
            Crew crew = (Crew) object;
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("profile_name", crew.getName());
            intent.putExtra("profile_id", crew.getId());
            intent.putExtra("type", "crew");
            startActivity(intent);
        } else {
            TMDbMovie movie = (TMDbMovie) object;
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("movie_id", movie.getId());
            intent.putExtra("movie_title", movie.getTitle());
            startActivity(intent);


        }

    }
}
