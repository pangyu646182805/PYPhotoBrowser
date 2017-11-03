package com.neurotech.photobrowser.ui.activities;

import com.neurotech.photobrowser.MediaSelector;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.SketchViewHolderCreator;
import com.neurotech.photobrowser.base.SupportActivity;
import com.neurotech.photobrowser.utils.MimeType;
import com.neurotech.photobrowser.utils.SelectMode;

import butterknife.OnClick;

/**
 * Created by NeuroAndroid on 2017/10/31.
 */

public class EntranceActivity extends SupportActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_entrance;
    }

    @OnClick(R.id.btn_start)
    public void start() {
        // startActivity(new Intent(this, PhotoSelectorActivity.class));
        MediaSelector.from(this)
                .choose(MimeType.PHOTO)
                .selectMode(SelectMode.MULTIPLE_MODE)
                .customViewHolder(new SketchViewHolderCreator())
                .forResult(0);
    }
}
