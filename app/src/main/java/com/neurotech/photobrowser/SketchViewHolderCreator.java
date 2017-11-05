package com.neurotech.photobrowser;

import android.view.ViewStub;
import android.widget.ImageView;

import com.neurotech.photobrowser.adapter.MediaAdapter;
import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.utils.MimeType;

import me.xiaopan.sketch.Sketch;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.display.TransitionImageDisplayer;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public class SketchViewHolderCreator implements ViewHolderCreator {
    @Override
    public ImageView inflateViewStub(ViewStub viewStub) {
        viewStub.setLayoutResource(R.layout.layout_sketch_view);
        viewStub.setInflatedId(R.id.iv_img);
        return (ImageView) viewStub.inflate();
    }

    @Override
    public void onBindViewHolder(MediaAdapter.MediaContentViewHolder viewHolder, FileBean item) {
        SketchImageView ivImg = (SketchImageView) viewHolder.image;
        switch (item.getMediaMimeType()) {
            case MimeType.PHOTO:
                Sketch.with(ivImg.getContext())
                        .display(item.getPath(), ivImg)
                        .maxSize(ivImg.getLayoutParams().width, ivImg.getLayoutParams().height)
                        .resize(ivImg.getLayoutParams().width, ivImg.getLayoutParams().height)
                        .cacheProcessedImageInDisk()
                        .loadingImage(R.drawable.img_loading)
                        .displayer(new TransitionImageDisplayer())
                        .thumbnailMode()
                        .commit();
                break;
            case MimeType.VIDEO:
                Sketch.with(ivImg.getContext())
                        .display(VideoThumbnailUriModel.makeUri(item.getPath()), ivImg)
                        .maxSize(ivImg.getLayoutParams().width, ivImg.getLayoutParams().height)
                        .resize(ivImg.getLayoutParams().width, ivImg.getLayoutParams().height)
                        .cacheProcessedImageInDisk()
                        .loadingImage(R.drawable.img_loading)
                        .displayer(new TransitionImageDisplayer())
                        .thumbnailMode()
                        .commit();
                break;
            case MimeType.AUDIO:
                Sketch.with(ivImg.getContext())
                        .display(AudioThumbnailUriModel.makeUri(item.getPath()), ivImg)
                        .maxSize(ivImg.getLayoutParams().width, ivImg.getLayoutParams().height)
                        .resize(ivImg.getLayoutParams().width, ivImg.getLayoutParams().height)
                        .cacheProcessedImageInDisk()
                        .loadingImage(R.drawable.img_loading)
                        .displayer(new TransitionImageDisplayer())
                        .thumbnailMode()
                        .commit();
                break;
        }
    }
}
