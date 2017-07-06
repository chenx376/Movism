package me.chenyongrui.movism.mvp.view.adapters.viewholder;

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
import me.chenyongrui.movism.mvp.model.tmdb.TMDbMovie;
import me.chenyongrui.movism.util.Constant;

@AutoFactory(implementing = BaseViewHolderFactory.class)
public class MovieViewHolderStaggeredGrid extends BaseViewHolder<TMDbMovie> {

    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView movieTitle;

    private String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.LIST_IMAGE_QUALITY + posterURL;
    }

    public MovieViewHolderStaggeredGrid(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_staggered_grid, parent, false));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(TMDbMovie movie) {
        movieTitle.setText(movie.getTitle() + " / "
                + movie.getReleaseDate());
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
