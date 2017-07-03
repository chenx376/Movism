package me.chenyongrui.movism.network;

import me.chenyongrui.movism.mvp.model.tmdb.Casts;
import me.chenyongrui.movism.mvp.model.tmdb.MovieCredits;
import me.chenyongrui.movism.mvp.model.tmdb.Profile;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieDetail;
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovieList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TMDbService {

    @GET("movie/top_rated")
    Observable<TMDbMovieList> getPopularMovies(@Query("page") Integer page);

    @GET("movie/upcoming")
    Observable<TMDbMovieList> getUpComingMovies(@Query("page") Integer page);

    @GET("movie/now_playing")
    Observable<TMDbMovieList> getInTheaterMovies(@Query("page") Integer page);

    @GET("search/movie")
    Observable<TMDbMovieList> searchMovies(@Query("query") String query, @Query("page") Integer page);

    @GET("movie/{id}/casts")
    Observable<Casts> getMovieCasts(@Path("id") String id);

    @GET("movie/{id}/similar")
    Observable<TMDbMovieList> getSimilarMovies(@Path("id") String id);

    @GET("movie/{id}")
    Observable<TMDbMovieDetail> getMovieDetail(@Path("id") String id);

    @GET("person/{id}")
    Observable<Profile> getProfile(@Path("id") String id);

    @GET("person/{id}/movie_credits")
    Observable<MovieCredits> getMovieCredits(@Path("id") String id);
}