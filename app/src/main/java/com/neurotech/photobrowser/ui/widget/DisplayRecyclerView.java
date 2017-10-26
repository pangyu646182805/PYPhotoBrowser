package com.neurotech.photobrowser.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by NeuroAndroid on 2017/9/29.
 * 仅做展示用的rv
 */

public class DisplayRecyclerView extends RecyclerView {
    public DisplayRecyclerView(Context context) {
        super(context);
    }

    public DisplayRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DisplayRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    @Override  // 表示事件是否拦截, 返回false表示不拦截
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
