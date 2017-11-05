package com.neurotech.photobrowser;

import android.view.ViewStub;
import android.widget.ImageView;

import com.neurotech.photobrowser.adapter.MediaAdapter;
import com.neurotech.photobrowser.bean.FileBean;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public interface ViewHolderCreator {
    ImageView inflateViewStub(ViewStub viewStub);

    void onBindViewHolder(MediaAdapter.MediaContentViewHolder viewHolder, FileBean item);
}
