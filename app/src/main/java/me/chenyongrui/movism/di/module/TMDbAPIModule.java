package me.chenyongrui.movism.di.module;


import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.BuildConfig;
import me.chenyongrui.movism.network.TMDbService;
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
public class TMDbAPIModule {

    public final String TMDB_BASE_URL = "http://api.themoviedb.org/3/";
    public final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;

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
                        "api_key",
                        TMDB_API_KEY
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
    public TMDbService provideService() {
        return provideRetrofit(TMDB_BASE_URL, provideHttpClientWithAPIKEY()).create(TMDbService.class);
    }
}
