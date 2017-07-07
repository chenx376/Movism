package me.chenyongrui.movism.data.api.service;


import me.chenyongrui.movism.data.api.model.omdb.OMDbMovie;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OMDbService {

    @GET("/")
    Observable<OMDbMovie> getOMDbMovie(@Query("t") String title);


}
