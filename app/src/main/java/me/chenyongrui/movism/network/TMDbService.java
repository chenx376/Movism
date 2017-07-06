package me.chenyongrui.movism.network;

import me.chenyongrui.movism.mvp.model.tmdb.CastsData;
import me.chenyongrui.movism.mvp.model.tmdb.MovieCreditsData;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TMDbService {

    @GET("movie/top_rated?sort_by=popularity.desc")
    Observable<TMDbMovieList> getTopRatedMovies(@Query("page") Integer page);

    @GET("movie/upcoming")
    Observable<TMDbMovieList> getUpComingMovies(@Query("page") Integer page);

    @GET("movie/now_playing")
    Observable<TMDbMovieList> getInTheaterMovies(@Query("page") Integer page);

    @GET("search/movie?sort_by=popularity.desc")
    Observable<TMDbMovieList> searchMovies(@Query("query") String query, @Query("page") Integer page);

    @GET("movie/{id}/casts")
    Observable<CastsData> getCastsData(@Path("id") String id);

    @GET("movie/{id}/similar?sort_by=popularity.desc")
    Observable<TMDbMovieList> getSimilarMovies(@Path("id") String id);

    @GET("movie/{id}")
    Observable<TMDbMovieDetail> getMovieDetail(@Path("id") String id);

    @GET("person/{id}")
    Observable<Profile> getProfile(@Path("id") String id);

    @GET("person/{id}/movie_credits?sort_by=popularity.desc")
    Observable<MovieCreditsData> getMovieCreditsData(@Path("id") String id);
}