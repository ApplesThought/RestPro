package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.ui.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PhotoBigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_big);

        initView();
    }

    private void initView() {
        String imgUrl = getIntent().getExtras().getString("imgUrl");
        PhotoView photoView = (PhotoView) findViewById(R.id.photoView);
        Glide.with(this).load(imgUrl).crossFade()
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
