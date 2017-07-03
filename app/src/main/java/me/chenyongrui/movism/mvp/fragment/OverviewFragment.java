package me.chenyongrui.movism.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chenyongrui.movism.R;


public class OverviewFragment extends Fragment {

    String titleValue, descValue;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.overview_title)
    TextView overviewTitle;
    @BindView(R.id.overview_content)
    TextView overviewContent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview_layout, container, false);
        ButterKnife.bind(this, view);

        titleValue = getArguments().getString("movie_title", "");
        descValue = getArguments().getString("movie_overview", "");
        overviewTitle.setText(titleValue);
        overviewContent.setText(descValue);
        return view;
    }


    @OnClick(R.id.btn_close)
    public void onViewClicked() {
        getFragmentManager().popBackStack();
    }
}