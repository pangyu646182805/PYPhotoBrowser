package com.neurotech.basecore.ui.adapter.base;

import android.view.View;

/**
 * Created by NeuroAndroid on 2017/3/9.
 */

public interface ISelect {
    int SINGLE_MODE = 1;
    int MULTIPLE_MODE = 2;

    boolean isSelected();

    void setSelected(boolean selected);

    interface OnItemSelectedListener<T> {
        void onItemSelected(View view, int position, boolean isSelected, T t);
        void onNothingSelected();
    }
}
