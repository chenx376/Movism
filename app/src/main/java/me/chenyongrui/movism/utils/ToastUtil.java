package me.chenyongrui.movism.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private Context mContext;

    public ToastUtil(Context context) {
        this.mContext = context;
    }

    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


}
