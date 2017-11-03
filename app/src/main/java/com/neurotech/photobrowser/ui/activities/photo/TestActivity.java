package com.neurotech.photobrowser.ui.activities.photo;

import android.content.Intent;
import android.view.MotionEvent;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.base.SupportActivity;

/**
 * Created by NeuroAndroid on 2017/10/30.
 */
public class TestActivity extends SupportActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startActivity(new Intent(this, PhotoSelectorActivity.class));
        return super.onTouchEvent(event);
    }
}
