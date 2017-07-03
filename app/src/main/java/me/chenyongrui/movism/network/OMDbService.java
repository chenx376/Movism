package me.chenyongrui.movism.network;


import me.chenyongrui.movism.mvp.model.omdb.OMDbMovie;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OMDbService {

    @GET("/")
    Observable<OMDbMovie> getOMDbMovie(@Query("t") String title);


}
