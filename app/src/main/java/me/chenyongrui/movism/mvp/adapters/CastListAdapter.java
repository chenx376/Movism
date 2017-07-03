package me.chenyongrui.movism.mvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.mvp.model.tmdb.Cast;
import me.chenyongrui.movism.util.Constant;

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.ViewHolder> {
    private Context context;
    private List<Cast> castList;
    private ClickListener clickListener;

    protected String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.AVATAR_IMAGE_QUALITY + posterURL;
    }

    public CastListAdapter(Context context, List<Cast> castList) {
        this.castList = castList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_cast_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Cast cast = castList.get(position);

        viewHolder.castName.setText(cast.getName());
        viewHolder.castCharacter.setText(cast.getCharacter());
        if (cast.getProfilePath() != null) {
            try {
                Glide.with(context).load(getRealPosterURL(cast.getProfilePath())).
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(viewHolder.castProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onCastClicked(cast);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public void setCastClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onCastClicked(Cast cast);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root_view)
        View rootView;
        @BindView(R.id.cast_profile)
        CircularImageView castProfile;
        @BindView(R.id.cast_name)
        TextView castName;
        @BindView(R.id.cast_character)
        TextView castCharacter;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

}

