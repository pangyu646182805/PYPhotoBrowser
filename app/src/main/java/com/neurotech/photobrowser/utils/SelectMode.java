package com.neurotech.photobrowser.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public class SelectMode {
    public static final int SINGLE_MODE = 0;
    public static final int MULTIPLE_MODE = 1;

    @MediaSelectMode
    private int mSelectMode = MULTIPLE_MODE;

    public void setSelectMode(@MediaSelectMode int selectMode) {
        mSelectMode = selectMode;
    }

    @MediaSelectMode
    public int getSelectMode() {
        return mSelectMode;
    }

    @IntDef({SINGLE_MODE, MULTIPLE_MODE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaSelectMode {
    }
}
