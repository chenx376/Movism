package me.chenyongrui.movism.ui.adapters.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.auto.factory.AutoFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.data.api.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.utils.Constant;

@AutoFactory(implementing = BaseViewHolderFactory.class)
public class MovieViewHolderLinearHorizontal extends BaseViewHolder<TMDbMovie> {

    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView movieTitle;

    private String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.LIST_IMAGE_QUALITY + posterURL;
    }

    public MovieViewHolderLinearHorizontal(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_linear_horizontal, parent, false));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(TMDbMovie movie) {
        movieTitle.setText(movie.getTitle());
        try {
            Glide.with(itemView.getContext())
                    .load(getRealPosterURL(movie.getPosterPath()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(moviePoster);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
