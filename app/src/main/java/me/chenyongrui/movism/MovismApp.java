package me.chenyongrui.movism;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

import javax.inject.Inject;

import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.di.component.DaggerAppComponent;
import me.chenyongrui.movism.di.module.AppModule;
import me.chenyongrui.movism.util.Constant;


public class MovismApp extends Application {

    private static MovismApp instance;

    private AppComponent appComponent;

    public static MovismApp getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        MovismApp.instance = this;


        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);

        Constant.isNightMode = sharedPreferences.getBoolean("night_mode", false);
        if (Constant.isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (sharedPreferences.getBoolean("image_quality", true)) {
            Constant.LIST_IMAGE_QUALITY = "w342";
            Constant.DETAIL_IMAGE_QUALITY = "w780";
            Constant.AVATAR_IMAGE_QUALITY = "w185";
        } else {
            Constant.LIST_IMAGE_QUALITY = "w185";
            Constant.DETAIL_IMAGE_QUALITY = "w500";
            Constant.AVATAR_IMAGE_QUALITY = "w92";
        }
    }

}
