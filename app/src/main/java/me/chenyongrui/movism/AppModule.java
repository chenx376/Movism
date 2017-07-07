package me.chenyongrui.movism;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chenyongrui.movism.utils.ToastUtil;

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context mContext) {
        this.mContext = mContext;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public ToastUtil provideToastUtil() {
        return new ToastUtil(mContext);
    }


    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

}
