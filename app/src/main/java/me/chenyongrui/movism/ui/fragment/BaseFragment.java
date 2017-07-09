package me.chenyongrui.movism.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import butterknife.Unbinder;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.MovismApp;
import me.chenyongrui.movism.utils.ToastUtil;


public abstract class BaseFragment extends Fragment {

    @Inject
    ToastUtil toastUtil;
    protected Unbinder unbinder;


    protected abstract void injectDependencies(AppComponent appComponent);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(MovismApp.getInstance().getAppComponent());

    }

    protected void showToast(String message) {
        toastUtil.showToast(message);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
