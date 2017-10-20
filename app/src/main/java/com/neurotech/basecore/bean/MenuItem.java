package com.neurotech.basecore.bean;

import android.support.annotation.DrawableRes;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class MenuItem {
    @DrawableRes private int icon;
    private String text;

    public MenuItem() {
    }

    public MenuItem(String text) {
        this.text = text;
    }

    public MenuItem(int iconResId, String text) {
        this.icon = iconResId;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
