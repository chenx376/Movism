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
import me.chenyongrui.movism.mvp.model.tmdb.Crew;
import me.chenyongrui.movism.util.Constant;

public class CrewListAdapter extends RecyclerView.Adapter<CrewListAdapter.ViewHolder> {
    private Context context;
    private List<Crew> crewList;
    private ClickListener clickListener;

    protected String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.AVATAR_IMAGE_QUALITY + posterURL;
    }

    public CrewListAdapter(Context context, List<Crew> crewList) {
        this.crewList = crewList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_crew_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Crew crew = crewList.get(position);

        viewHolder.crewName.setText(crew.getName());
        viewHolder.crewJob.setText(crew.getJob());

            if (crew.getProfilePath() != null) {
                try {
                    Glide.with(context).load(getRealPosterURL(crew.getProfilePath())).
                            diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(viewHolder.crewProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onCrewClicked(crew);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public void setCrewClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onCrewClicked(Crew crew);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root_view)
        View rootView;
        @BindView(R.id.crew_profile)
        CircularImageView crewProfile;
        @BindView(R.id.crew_name)
        TextView crewName;
        @BindView(R.id.crew_job)
        TextView crewJob;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

}

