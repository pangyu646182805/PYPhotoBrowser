package com.neurotech.photobrowser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.utils.L;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/5.
 */

public class PhotoGalleryActivity extends AppCompatActivity {
    private ViewPager mVpPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        mVpPreview = findViewById(R.id.vp_preview);

        initViewPager();
    }

    private void initViewPager() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            FileBean image = (FileBean) extras.getSerializable("item");
            int position = extras.getInt("position");
            ArrayList<FileBean> images = (ArrayList<FileBean>) extras.getSerializable("images");

            L.e("images size : " + images.size());
        }
    }
}
