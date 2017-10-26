package com.neurotech.photobrowser.ui.adapter.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mItemView;
    private Context mContext;

    public View getItemView() {
        return mItemView;
    }

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public static BaseViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }

    public static BaseViewHolder createViewHolder(Context context, View itemView) {
        return new BaseViewHolder(context, itemView);
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        if (view != null) view.setText(value);
        return this;
    }

    public BaseViewHolder setChecked(@IdRes int viewId, boolean checked) {
        CompoundButton cb = getView(viewId);
        if (cb != null) cb.setChecked(checked);
        return this;
    }

    public BaseViewHolder setTextSize(@IdRes int viewId, float size) {
        TextView view = getView(viewId);
        if (view != null) view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int viewId, int color) {
        TextView view = getView(viewId);
        if (view != null) view.setTextColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        if (view != null) view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) view.setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) view.setOnClickListener(onClickListener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener onLongClickListener) {
        View view = getView(viewId);
        if (view != null) view.setOnLongClickListener(onLongClickListener);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int viewId, int resId) {
        ImageView iv = getView(viewId);
        if (iv != null) iv.setImageResource(resId);
        return this;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
