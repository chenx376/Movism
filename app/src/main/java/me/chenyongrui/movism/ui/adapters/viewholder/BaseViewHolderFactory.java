package me.chenyongrui.movism.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface BaseViewHolderFactory {
    RecyclerView.ViewHolder createViewHolder(ViewGroup parent);
}