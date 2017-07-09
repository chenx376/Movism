package me.chenyongrui.movism.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolder;
import me.chenyongrui.movism.ui.adapters.viewholder.BaseViewHolderFactory;


public class OmniAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private Context context;
    private BaseViewHolderFactory viewHolderFactories;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ItemClickListener itemClickListener;
    private final List<T> dataList = new ArrayList<>();

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public OmniAdapter(Context Context, BaseViewHolderFactory viewHolderFactories) {
        this.context = Context;
        this.viewHolderFactories = viewHolderFactories;

    }

    public void addData(List<T> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void removeAllData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    public void addData(T t) {
        this.dataList.add(t);
        notifyItemInserted(dataList.size() - 1);
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final VH viewHolder = (VH) viewHolderFactories.createViewHolder(parent);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(dataList.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        viewHolder.bind(dataList.get(position));
    }

    public interface ItemClickListener<T> {
        void onItemClicked(T object);
    }

}
