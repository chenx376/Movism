package me.chenyongrui.movism.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.MovismApp;
import me.chenyongrui.movism.utils.Constant;
import me.chenyongrui.movism.utils.ToastUtil;


public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    ToastUtil toastUtil;

    public boolean isNightMode = Constant.isNightMode;

    protected abstract void injectDependencies(AppComponent appComponent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(MovismApp.getInstance().getAppComponent());
    }

    protected void showToast(String message) {
        toastUtil.showToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNightMode != Constant.isNightMode) {
            this.recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
