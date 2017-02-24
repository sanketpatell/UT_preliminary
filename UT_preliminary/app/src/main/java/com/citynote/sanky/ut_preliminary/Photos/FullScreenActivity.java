package com.citynote.sanky.ut_preliminary.Photos;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citynote.sanky.ut_preliminary.R;
import com.citynote.sanky.ut_preliminary.Photos.TouchImageView;

public class FullScreenActivity extends AppCompatActivity {


    String name, url;
    TouchImageView touchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        initView();
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        name = bundle.getString("name");


        touchImageView = (TouchImageView) findViewById(R.id.img_full);

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(touchImageView);
    }
}
