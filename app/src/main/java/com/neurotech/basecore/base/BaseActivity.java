package com.neurotech.basecore.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.MenuItem;

import com.flyco.systembar.SystemBarHelper;
import com.neurotech.basecore.R;
import com.neurotech.basecore.ui.widget.MultipleStatusLayout;
import com.neurotech.basecore.ui.widget.TitleBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by NeuroAndroid on 2017/10/10.
 */

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IView<P> {
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
    private Unbinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onActivityCreate(savedInstanceState);

        setContentView(attachLayoutRes());
        if (useEventBus()) EventBus.getDefault().register(this);
        mBinder = ButterKnife.bind(this);
        if (supportImmersive()) {
            SystemBarHelper.tintStatusBar(this, getResources().getColor(R.color.colorPrimary));
            if (supportDarkStatusBar()) {
                SystemBarHelper.setStatusBarDarkMode(this);
            }
        } else {
            SystemBarHelper.tintStatusBar(this, Color.BLACK);
        }
        initPresenter();
        if (mTitleBar != null) {
            setSupportActionBar(mTitleBar.getToolbar());
            initTitleBar();
        }
        initView();
        initData();
        initListener();
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 是否使用EventBus(默认不适用)
     * 如果需要使用子类实现此方法并且返回true
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 是否支持沉浸式状态栏(默认支持)
     */
    protected boolean supportImmersive() {
        return true;
    }

    /**
     * 是否支持暗色状态栏
     * 默认是亮色
     */
    protected boolean supportDarkStatusBar() {
        return false;
    }

    protected void initPresenter() {
    }

    /**
     * 设置返回按钮
     */
    protected void setDisplayHomeAsUpEnabled() {
        if (mTitleBar != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected void setToolbarTitle(CharSequence title) {
        if (mTitleBar != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    protected void setToolbarTitle(@StringRes int resId) {
        if (mTitleBar != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(resId);
            }
        }
    }

    protected void setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        if (mTitleBar != null) {
            mTitleBar.setContentInsetStartWithNavigation(insetStartWithNavigation);
        }
    }

    protected void onActivityCreate(Bundle savedInstanceState) {
    }

    protected void initTitleBar() {
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListener() {
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
        return this;
    }

    @Override
    public void showTip(String tip) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        if (mPresenter != null) mPresenter.onDestroy();
        if (mBinder != Unbinder.EMPTY) mBinder.unbind();
        if (useEventBus()) EventBus.getDefault().unregister(this);
        this.mBinder = null;
        this.mPresenter = null;
    }
}
