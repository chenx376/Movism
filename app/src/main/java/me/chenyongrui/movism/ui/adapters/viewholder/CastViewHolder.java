package me.chenyongrui.movism.ui.adapters.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.auto.factory.AutoFactory;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.data.api.model.tmdb.Cast;
import me.chenyongrui.movism.utils.Constant;

@AutoFactory(implementing = BaseViewHolderFactory.class)
public class CastViewHolder extends BaseViewHolder<Cast> {
    @BindView(R.id.cast_profile)
    CircularImageView castProfile;
    @BindView(R.id.cast_name)
    TextView castName;
    @BindView(R.id.cast_character)
    TextView castCharacter;

    private String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.AVATAR_IMAGE_QUALITY + posterURL;
    }

    public CastViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Cast cast) {

        castName.setText(cast.getName());
        castCharacter.setText(cast.getCharacter());
        if (cast.getProfilePath() != null) {
            try {
                Glide.with(itemView.getContext()).load(getRealPosterURL(cast.getProfilePath())).
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(castProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
