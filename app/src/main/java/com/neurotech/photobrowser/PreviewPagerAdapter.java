package com.neurotech.photobrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.neurotech.photobrowser.bean.FileBean;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/11/6.
 */

public class PreviewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<FileBean> mImages;

    public PreviewPagerAdapter(FragmentManager fm, ArrayList<FileBean> images) {
        super(fm);
        this.mImages = images;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoGalleryFragment.newInstance(mImages.get(position));
    }

    @Override
    public int getCount() {
        return mImages == null ? 0 : mImages.size();
    }
}
