package com.neurotech.photobrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.TextView;

import com.neurotech.photobrowser.adapter.FolderAdapter;
import com.neurotech.photobrowser.bean.FolderBean;
import com.neurotech.photobrowser.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public class MediaFolderSpinner {
    private static final int MAX_SHOWN_COUNT = 6;

    private ListPopupWindow mListPopupWindow;
    private TextView mSelectedTextView;
    private FolderAdapter mFolderAdapter;

    public MediaFolderSpinner(@NonNull Context context) {
        mListPopupWindow = new ListPopupWindow(context);
        mListPopupWindow.setModal(true);
        float density = context.getResources().getDisplayMetrics().density;
        mListPopupWindow.setContentWidth((int) (216 * density));
        mListPopupWindow.setHorizontalOffset((int) (16 * density));
        mListPopupWindow.setVerticalOffset((int) (-48 * density));
    }

    public void setAdapter(FolderAdapter folderAdapter) {
        mFolderAdapter = folderAdapter;
        mListPopupWindow.setAdapter(mFolderAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setSelectedTextView(TextView tv) {
        mSelectedTextView = tv;
        mSelectedTextView.setVisibility(View.GONE);
        mSelectedTextView.setOnClickListener(view -> {
            int itemHeight = UIUtils.getResources().getDimensionPixelSize(R.dimen.folder_item_height);
            mListPopupWindow.setHeight(
                    mFolderAdapter.getCount() > MAX_SHOWN_COUNT ? itemHeight * MAX_SHOWN_COUNT
                            : itemHeight * mFolderAdapter.getCount());
            mListPopupWindow.show();
        });
        mSelectedTextView.setOnTouchListener(mListPopupWindow.createDragToOpenListener(mSelectedTextView));
    }

    public void swapFolderList(ArrayList<FolderBean> folderList) {
        mFolderAdapter.setFolderList(folderList);
        String displayName = folderList.get(0).getFolderName();
        mSelectedTextView.setVisibility(View.VISIBLE);
        mSelectedTextView.setText(displayName);
    }

    public void setPopupAnchorView(View view) {
        mListPopupWindow.setAnchorView(view);
    }
}
