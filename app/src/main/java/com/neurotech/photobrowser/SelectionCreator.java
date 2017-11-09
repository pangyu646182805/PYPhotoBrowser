package com.neurotech.photobrowser;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

import com.neurotech.photobrowser.ui.activities.photo.PhotoSelectorActivity;
import com.neurotech.photobrowser.utils.MimeType;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public final class SelectionCreator {
    private final MediaSelector mMediaSelector;
    private final SelectionOptions mOptions;

    SelectionCreator(MediaSelector mediaSelector, @MimeType.MediaMimeType int mimeType) {
        mMediaSelector = mediaSelector;
        mOptions = SelectionOptions.getCleanOptions();
        mOptions.mimeType = mimeType;
    }

    /**
     * 最大可选择数量
     *
     * @param maxSelectable 必须大于1
     */
    public SelectionCreator maxSelectable(int maxSelectable) {
        if (maxSelectable < 1) {
            throw new IllegalArgumentException("maxSelectable must be greater than or equal to one");
        }
        mOptions.maxSelectable = maxSelectable;
        return this;
    }

    /**
     * PhotoSelector显示的网格大小
     *
     * @param gridSize 必须大于2
     */
    public SelectionCreator gridSize(int gridSize) {
        if (gridSize < 2) {
            throw new IllegalArgumentException("gridSize must be greater than or equal to two");
        }
        mOptions.gridSize = gridSize;
        return this;
    }

    /**
     * 是否展示gif图片
     */
    public SelectionCreator showGif(boolean showGif) {
        mOptions.showGif = showGif;
        return this;
    }

    /**
     * 是否显示gif标志
     */
    public SelectionCreator showGifFlag(boolean showGifFlag) {
        mOptions.showGifFlag = showGifFlag;
        return this;
    }

    /**
     * 设置gif标志的resId
     */
    public SelectionCreator setGifFlagResId(@DrawableRes int gifFlagResId) {
        mOptions.gifFlagResId = gifFlagResId;
        return this;
    }

    public SelectionCreator setBackgroundColor(@ColorInt int backgroundColor) {
        mOptions.backgroundColor = backgroundColor;
        return this;
    }

    /**
     * 是否展示PhotoSelector第一项(即拍照item)
     */
    public SelectionCreator showHeaderItem(boolean showHeaderItem) {
        mOptions.showHeaderItem = showHeaderItem;
        return this;
    }

    /**
     * 设置是否点击空白区域取消PhotoSelector
     */
    public SelectionCreator setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mOptions.canceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public SelectionCreator customViewHolder(ViewHolderCreator viewHolderCreator) {
        if (null == viewHolderCreator) {
            throw new IllegalArgumentException("viewHolderCreator must not be null");
        }
        mOptions.viewHolderCreator = viewHolderCreator;
        return this;
    }

    public void forResult(int requestCode) {
        Activity activity = mMediaSelector.getActivity();
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, PhotoSelectorActivity.class);

        Fragment fragment = mMediaSelector.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
