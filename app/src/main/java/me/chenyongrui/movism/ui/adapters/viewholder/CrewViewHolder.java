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
import me.chenyongrui.movism.data.api.model.tmdb.Crew;
import me.chenyongrui.movism.utils.Constant;

@AutoFactory(implementing = BaseViewHolderFactory.class)
public class CrewViewHolder extends BaseViewHolder<Crew> {
    @BindView(R.id.crew_profile)
    CircularImageView crewProfile;
    @BindView(R.id.crew_name)
    TextView crewName;
    @BindView(R.id.crew_job)
    TextView crewJob;

    private String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + Constant.AVATAR_IMAGE_QUALITY + posterURL;
    }

    public CrewViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew, parent, false));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Crew crew) {

        crewName.setText(crew.getName());
        crewJob.setText(crew.getJob());

        if (crew.getProfilePath() != null) {
            try {
                Glide.with(itemView.getContext()).load(getRealPosterURL(crew.getProfilePath())).
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(crewProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
