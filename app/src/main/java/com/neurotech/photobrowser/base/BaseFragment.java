package com.neurotech.photobrowser.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.systembar.SystemBarHelper;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.widget.MultipleStatusLayout;
import com.neurotech.photobrowser.widget.TitleBar;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by NeuroAndroid on 2017/10/12.
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IView<P> {
    protected final String TAG = getClass().getSimpleName();

    /**
     * 把 LoadingLayout 放在基类统一处理，@Nullable 表明 View 可以为 null
     */
    @Nullable
    @BindView(R.id.status_layout)
    MultipleStatusLayout mStatusLayout;

    @Nullable
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    protected P mPresenter;
    private Unbinder mUnBinder;

    protected FragmentActivity mActivity;
    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (useEventBus()) EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutRes(), null);
        mUnBinder = ButterKnife.bind(this, view);
        initPresenter();
        if (mTitleBar != null) {
            BaseActivity baseActivity = getActivity(BaseActivity.class);
            if (baseActivity != null) {
                baseActivity.setSupportActionBar(mTitleBar.getToolbar());

                setHasOptionsMenu(true);
                if (baseActivity.supportImmersive()) {
                    if (supportDarkStatusBar()) {
                        SystemBarHelper.setStatusBarDarkMode(mActivity);
                    }
                }
            }
            initTitleBar();
        }
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * 是否使用EventBus(默认不适用)
     * 如果需要使用子类实现此方法并且返回true
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 是否支持暗色状态栏
     * 默认是亮色
     */
    protected boolean supportDarkStatusBar() {
        return false;
    }

    /**
     * 绑定布局文件
     */
    protected abstract int attachLayoutRes();

    protected void initPresenter() {
    }

    protected void initTitleBar() {
    }

    /**
     * 初始化视图控件
     */
    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    /**
     * 获取activity
     */
    public <T> T getActivity(Class<T> clazz) {
        return clazz.cast(mActivity);
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        if (mStatusLayout != null) {
            mStatusLayout.setStatus(MultipleStatusLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mStatusLayout != null) {
            mStatusLayout.hide();
        }
    }

    @Override
    public void showError(MultipleStatusLayout.OnRetryListener onRetryListener) {
        if (mStatusLayout != null) {
            mStatusLayout.setStatus(MultipleStatusLayout.STATUS_ERROR);
            mStatusLayout.setOnRetryListener(onRetryListener);
        }
    }

    @Override
    public Context getAppContext() {
        return mContext;
    }

    @Override
    public void showTip(String tip) {
    }

    /**
     * 设置返回按钮
     */
    protected void setDisplayHomeAsUpEnabled() {
        if (mTitleBar != null) {
            ActionBar supportActionBar = getActivity(BaseActivity.class).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * 是否显示ToolBar标题
     */
    protected void setDisplayShowTitleEnabled(boolean showTitle) {
        if (mTitleBar != null) {
            ActionBar supportActionBar = getActivity(BaseActivity.class).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayShowTitleEnabled(showTitle);
            }
        }
    }

    protected void setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        if (mTitleBar != null) {
            mTitleBar.setContentInsetStartWithNavigation(insetStartWithNavigation);
        }
    }

    protected void setToolbarTitle(CharSequence title) {
        if (mTitleBar != null) {
            ActionBar supportActionBar = getActivity(BaseActivity.class).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(title);
            }
        }
    }

    protected void setToolbarTitle(@StringRes int resId) {
        if (mTitleBar != null) {
            ActionBar supportActionBar = getActivity(BaseActivity.class).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(resId);
            }
        }
    }

    protected void setNavigationIcon(int resId) {
        if (mTitleBar != null) mTitleBar.getToolbar().setNavigationIcon(resId);
    }

    protected void setNavigationOnClickListener(View.OnClickListener listener) {
        if (mTitleBar != null) mTitleBar.getToolbar().setNavigationOnClickListener(listener);
    }

    /**
     * setHasOptionsMenu(true) 表示加载ToolBar菜单
     * 但是Fragment的ToolBar菜单是Activity传递过来的
     * Fragment需要实现自己的ToolBar菜单则需要menu.clear()
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != Unbinder.EMPTY) mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放资源
        if (useEventBus()) EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.onDestroy();
        this.mPresenter = null;
        this.mUnBinder = null;
        this.mActivity = null;
        this.mContext = null;
    }
}
