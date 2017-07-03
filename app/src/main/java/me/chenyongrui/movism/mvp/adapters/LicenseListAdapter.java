package me.chenyongrui.movism.mvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;

public class LicenseListAdapter extends RecyclerView.Adapter<LicenseListAdapter.ViewHolder> {
    private Context context;


    private List<LicenseItem> licenseItemList;

    public LicenseListAdapter(Context context, List<LicenseItem> licenseItemList) {
        this.context = context;
        this.licenseItemList = licenseItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_license, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LicenseItem item = licenseItemList.get(position);
        holder.libLicenseAuthor.setText(item.libLicenseAuthor);
        holder.libLicenseName.setText(item.libLicenseName);
        holder.libLicenseIntro.setText(item.libLicenseIntro);
    }


    @Override
    public int getItemCount() {
        return licenseItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lib_license_name)
        TextView libLicenseName;
        @BindView(R.id.lib_license_author)
        TextView libLicenseAuthor;
        @BindView(R.id.lib_license_intro)
        TextView libLicenseIntro;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class LicenseItem {
        String libLicenseAuthor;
        String libLicenseIntro;
        String libLicenseName;

        public LicenseItem(String libLicenseAuthor, String libLicenseIntro, String libLicenseName) {
            this.libLicenseAuthor = libLicenseAuthor;
            this.libLicenseIntro = libLicenseIntro;
            this.libLicenseName = libLicenseName;
        }
    }
}

