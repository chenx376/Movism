package me.chenyongrui.movism.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class BaseViewHolder<Object> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Object object);
}
