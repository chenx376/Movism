package me.chenyongrui.movism.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.R;


public class OverviewFragment extends BaseFragment {

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
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        unbinder = ButterKnife.bind(this, view);

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

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        
    }
}
