package com.neurotech.basecore.utils;

import com.neurotech.basecore.R;

import me.xiaopan.sketch.display.TransitionImageDisplayer;
import me.xiaopan.sketch.request.DisplayOptions;

/**
 * Created by NeuroAndroid on 2017/10/23.
 */

public class PhotoOptions {
    public static DisplayOptions getPhotoOptions(int spanCount) {
        return new DisplayOptions()
                .setLoadingImage(R.mipmap.ic_launcher)
                .setErrorImage(R.mipmap.ic_launcher)
                .setCacheProcessedImageInDisk(true)
                .setCacheInDiskDisabled(false)
                .setCacheInMemoryDisabled(true)
                .setMaxSize((int) UIUtils.getDimen(R.dimen.x720) / spanCount, (int) UIUtils.getDimen(R.dimen.x720) / spanCount)
                .setDisplayer(new TransitionImageDisplayer());
    }
}
