package com.neurotech.basecore.base;

import android.content.Context;

import com.neurotech.basecore.ui.widget.MultipleStatusLayout;

/**
 * Created by NeuroAndroid on 2017/6/13.
 */

public interface IView<T extends IPresenter> {
    /**
     * 设置presenter
     *
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示错误状态
     */
    void showError(MultipleStatusLayout.OnRetryListener onRetryListener);

    Context getAppContext();

    /**
     * 显示提示
     */
    void showTip(String tip);
}
