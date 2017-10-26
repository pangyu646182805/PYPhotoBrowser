package com.neurotech.photobrowser.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;

import com.neurotech.photobrowser.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class DividerUtils {
    /**
     * 默认RecyclerView分割线 (横向)
     * 2px - #eeeeee
     */
    public static HorizontalDividerItemDecoration defaultHorizontalDivider(Context context) {
        return generateHorizontalDivider(context, R.dimen.x2, R.color.split);
    }

    public static HorizontalDividerItemDecoration generateHorizontalDivider(
            Context context, @DimenRes int sizeId, @ColorRes int colorId) {
        return new HorizontalDividerItemDecoration.Builder(context)
                .sizeResId(sizeId).colorResId(colorId).build();
    }
    /**
     * 默认RecyclerView分割线 (纵向)
     * 2px - #eeeeee
     */
    public static VerticalDividerItemDecoration defaultVerticalDivider(Context context) {
        return generateVerticalDivider(context, R.dimen.x2, R.color.split);
    }

    public static VerticalDividerItemDecoration generateVerticalDivider(
            Context context, @DimenRes int sizeId, @ColorRes int colorId) {
        return new VerticalDividerItemDecoration.Builder(context)
                .sizeResId(sizeId).colorResId(colorId).build();
    }
}
