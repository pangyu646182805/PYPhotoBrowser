package com.neurotech.photobrowser;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.neurotech.photobrowser.bean.FileBean;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public interface ViewHolderCreator {
    RecyclerView.ViewHolder createViewHolder(ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, FileBean item);
}
