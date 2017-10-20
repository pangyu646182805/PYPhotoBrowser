package com.neurotech.basecore.base;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected M mModel;
    protected V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        if (mModel != null) {
            mModel.onDestroy();
            mModel = null;
        }
        this.mView = null;
    }
}
