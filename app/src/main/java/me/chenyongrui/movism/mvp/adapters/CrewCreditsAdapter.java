package me.chenyongrui.movism.mvp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.mvp.model.tmdb.CrewMovieCredits;
import me.chenyongrui.movism.util.Constant;

public class CrewCreditsAdapter extends RecyclerView.Adapter<CrewCreditsAdapter.ViewHolder> {
    private Context context;
    private List<CrewMovieCredits> movieList;
    private MovieClickListener movieClickListener;

    protected String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.LIST_IMAGE_QUALITY + posterURL;
    }


    public CrewCreditsAdapter(Context context, List<CrewMovieCredits> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_movie_credit_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final CrewMovieCredits movie = movieList.get(position);
        viewHolder.movieTitle.setText(movie.getTitle());
        try {
            Glide.with(context).load(getRealPosterURL(movie.getPosterPath()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.moviePoster);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieClickListener != null) {
                    movieClickListener.onMovieClicked(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovieClickListener(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    public interface MovieClickListener {
        void onMovieClicked(CrewMovieCredits movie);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root_view)
        View rootView;
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_title)
        TextView movieTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

