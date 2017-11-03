package com.neurotech.photobrowser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.utils.UIUtils;

import me.xiaopan.sketch.Sketch;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.display.TransitionImageDisplayer;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public class SketchViewHolderCreator implements ViewHolderCreator {
    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        return new MediaContentViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media_content, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, FileBean item) {
        MediaContentViewHolder holder = (MediaContentViewHolder) viewHolder;
        Sketch.with(holder.mIvImg.getContext())
                .display(item.getPath(), holder.mIvImg)
                .maxSize((int) UIUtils.getDimen(R.dimen.x720) / 4, (int) UIUtils.getDimen(R.dimen.x720) / 4)
                .resize((int) UIUtils.getDimen(R.dimen.x720) / 4, (int) UIUtils.getDimen(R.dimen.x720) / 4)
                .cacheProcessedImageInDisk()
                .loadingImage(R.drawable.img_loading)
                .displayer(new TransitionImageDisplayer())
                .thumbnailMode()
                .commit();
    }

    public class MediaContentViewHolder extends RecyclerView.ViewHolder {
        SketchImageView mIvImg;

        MediaContentViewHolder(View itemView) {
            super(itemView);
            mIvImg = itemView.findViewById(R.id.iv_img);
            ViewGroup.LayoutParams layoutParams = mIvImg.getLayoutParams();
            if (null != layoutParams) {
                layoutParams.width = (int) UIUtils.getDimen(R.dimen.x720) / 4;
                layoutParams.height = (int) UIUtils.getDimen(R.dimen.x720) / 4;
                mIvImg.setLayoutParams(layoutParams);
            }
        }
    }
}
