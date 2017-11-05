package com.neurotech.photobrowser;

import android.app.Activity;
import android.content.Intent;
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

    public SelectionCreator maxSelectable(int maxSelectable) {
        if (maxSelectable < 1) {
            throw new IllegalArgumentException("maxSelectable must be greater than or equal to one");
        }
        mOptions.maxSelectable = maxSelectable;
        return this;
    }

    public SelectionCreator gridSize(int gridSize) {
        if (gridSize < 2) {
            throw new IllegalArgumentException("gridSize must be greater than or equal to two");
        }
        mOptions.gridSize = gridSize;
        return this;
    }

    public SelectionCreator customViewHolder(ViewHolderCreator viewHolderCreator) {
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
