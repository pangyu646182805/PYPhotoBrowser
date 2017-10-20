package com.neurotech.basecore.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.neurotech.basecore.R;
import com.neurotech.basecore.ui.adapter.base.BaseRvAdapter;
import com.neurotech.basecore.utils.L;
import com.neurotech.basecore.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/10/17.
 * 通用的下拉刷新、上拉加载列表
 * 内置SmartRefreshLayout和RecyclerView
 * 支持空页面显示icon、错误信息
 */
public abstract class BaseListActivity<P extends IPresenter, ADAPTER extends BaseRvAdapter,
        LM extends RecyclerView.LayoutManager> extends SupportActivity<P> {
    protected static final int PAGE_SIZE = 15;
    protected int mPage;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private ADAPTER mAdapter;
    private LM mLayoutManager;

    /**
     * 是否已经加载过缓存里面的数据
     * 如果已经加载过则不加载缓存里面的数据
     */
    protected boolean mIsInitCache;

    protected ADAPTER getAdapter() {
        return mAdapter;
    }

    protected LM getLayoutManager() {
        return mLayoutManager;
    }

    protected SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    protected RecyclerView getRecyclerView() {
        return mRvList;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.layout_base_list;
    }

    @Override
    protected void initView() {
        initRefreshLayout();
        initLayoutManager();
        initAdapter();
        setUpRecyclerView();
    }

    @Override
    protected void initData() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                L.e(TAG + " 开始下拉刷新");
                BaseListActivity.this.onRefresh(refreshlayout);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                L.e(TAG + " 开始下拉刷新");
                BaseListActivity.this.onLoadMore(refreshlayout);
            }
        });
    }

    protected void initRefreshLayout() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
    }

    private void initLayoutManager() {
        mLayoutManager = createLayoutManager();
    }

    private void initAdapter() {
        mAdapter = createAdapter();
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkIsEmpty();
            }
        });
    }

    private void checkIsEmpty() {
        if (mStatusLayout != null) {
            if (mAdapter == null || mAdapter.getItemCount() == 0) {
                showError(view ->
                        ToastUtils.error("showError"));
            }
        }
    }

    private void setUpRecyclerView() {
        mRvList.setLayoutManager(mLayoutManager);
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void showTip(String tip) {
        ToastUtils.error(tip);
        finishRefresh();
    }

    /**
     * 结束刷新
     */
    protected void finishRefresh() {
        if (mRefreshLayout != null) {
            if (mRefreshLayout.isRefreshing()) mRefreshLayout.finishRefresh(200);
            if (mRefreshLayout.isLoading()) mRefreshLayout.finishLoadmore(200);
        }
    }

    protected abstract LM createLayoutManager();

    @NonNull
    protected abstract ADAPTER createAdapter();

    protected abstract void onRefresh(RefreshLayout refreshLayout);

    protected abstract void onLoadMore(RefreshLayout refreshLayout);
}
