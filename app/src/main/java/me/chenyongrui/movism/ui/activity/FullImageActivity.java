package me.chenyongrui.movism.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.utils.Constant;


public class FullImageActivity extends BaseActivity {
    @BindView(R.id.backdrop_image)
    PhotoView backdropView;
    private String backdropPath;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }


    private static final String IMAGE_QUALITY = "original";

    protected String getRealPosterURL(String posterURL) {
        return Constant.BASE_IMAGE_URL + IMAGE_QUALITY + posterURL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            backdropPath = intent.getStringExtra("backdrop_path");
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(intent.getStringExtra("movie_title"));
            }
        }
        progressWheel.setVisibility(View.VISIBLE);

        backdropView.enable();
        try {
            Glide.with(this)
                    .load(getRealPosterURL(backdropPath))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            backdropView.setImageBitmap(resource);
                            progressWheel.setVisibility(View.INVISIBLE);
                        }
                    });
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

}
