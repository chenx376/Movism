package me.chenyongrui.movism.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import me.chenyongrui.movism.MovismApp;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.util.ToastUtil;


public abstract class BaseFragment extends Fragment {

    @Inject
    ToastUtil toastUtil;



    protected abstract void injectDependencies(AppComponent appComponent);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(MovismApp.getInstance().getAppComponent());

    }

    protected void showToast(String message) {
        toastUtil.showToast(message);
    }

}
