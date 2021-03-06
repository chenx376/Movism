package me.chenyongrui.movism.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.R;


public class AboutActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.about_container)
    ScrollView aboutContainer;

    @Override
    protected void injectDependencies(AppComponent appComponent) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);


        SetupToolBar(toolbar, "About", null, true);

        AboutView view = AboutBuilder.with(this)
                .setPhoto(R.drawable.github)
                .setCover(R.mipmap.profile_cover)
                .setName("Yongrui CHEN")
                .setSubTitle("Software Developer")
                .setBrief("I'm warmed of software technologies. Ideas maker, quick learner, curious and nature lover.")
                .addWebsiteLink("http://chenyongrui.me")
                .addEmailLink("yongrui.chen@outlook.com")
                .addGitHubLink("ChenYongrui")
                .addFacebookLink("yongruic")
                .addLinkedInLink("yongrui-chen")
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addFiveStarsAction()
//                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setDividerColor(R.attr.colorPrimary)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();

        aboutContainer.addView(view);
    }

}
