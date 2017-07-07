package me.chenyongrui.movism.data.api;


import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.BuildConfig;
import me.chenyongrui.movism.data.api.service.OMDbService;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class OMDbAPIModule {

    public final String OMDB_BASE_URL = "http://www.omdbapi.com";
    public final String OMDB_API_KEY = BuildConfig.OMDB_API_KEY;

    @Singleton
    @Provides
    public OkHttpClient provideHttpClientWithAPIKEY() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(
                        "apikey",
                        OMDB_API_KEY
                ).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public OMDbService provideService() {
        return provideRetrofit(OMDB_BASE_URL, provideHttpClientWithAPIKEY()).create(OMDbService.class);
    }
}
