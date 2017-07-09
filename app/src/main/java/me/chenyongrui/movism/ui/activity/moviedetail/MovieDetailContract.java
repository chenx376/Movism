package me.chenyongrui.movism.ui.activity.moviedetail;

import java.util.List;

import me.chenyongrui.movism.data.api.model.omdb.OMDbMovie;
import me.chenyongrui.movism.data.api.model.tmdb.Cast;
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovieList;

public interface MovieDetailContract {
    interface View {
        String getRealPosterURL(String posterURL);

        void showMovieDetail(TMDbMovieDetail movieDetail);

        void showExtraRatingData(OMDbMovie omDbMovie);

        void showCrewListData(List<Crew> crewList);

        void showCastListData(List<Cast> castList);

        void showSimilarMovieListData(TMDbMovieList movieList);

        void setCrewListAdapter();

        void setCastListAdapter();

        void setSimilarMovieAdapter();

        void loadBackdropAndSetColorBar(TMDbMovieDetail movieDetail);
    }

    interface Presenter {
        void clearSubscription();

        void presentMovieDetailData(int movieID);

        void presentMovieCastsData(int movieID);

        void presentSimilarMovieData(int movieID);

        void presentExtraRatingData(String movieTitle);
    }
}
