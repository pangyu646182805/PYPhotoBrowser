package com.neurotech.photobrowser.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.ui.adapter.base.BaseRvAdapter;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.NetworkUtils;
import com.neurotech.photobrowser.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/10/18.
 */

public abstract class BaseListFragment<P extends IPresenter, ADAPTER extends BaseRvAdapter,
        LM extends RecyclerView.LayoutManager> extends SupportFragment<P> {
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
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载模式下会回调此方法 只会回调一次
        L.e(TAG + " onLazyInitView supportLazyLoad --> " + supportLazyLoad());
        if (supportLazyLoad()) {
            L.e(TAG + " onLazyInitView --> 懒加载模式");
            initAdapter();
            setUpRecyclerView();
            initBusiness();
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // Fragment入栈动画结束时候的回调 防止动画卡顿
        L.e(TAG + " onEnterAnimationEnd supportLazyLoad --> " + supportLazyLoad());
        if (!supportLazyLoad()) {
            L.e(TAG + " onEnterAnimationEnd --> 非懒加载模式");
            // 如果不支持懒加载模式
            initAdapter();
            setUpRecyclerView();
            initBusiness();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    protected void initRefreshLayout() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
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

    /**
     * 检查是否是空列表
     */
    private void checkIsEmpty() {
        if (mStatusLayout != null) {
            L.e(TAG + " 检查是否是空列表");
            if (mAdapter == null || mAdapter.getItemCount() == 0) {
                L.e(TAG + " 该页面没有数据 显示空视图");
                showError(view -> {
                    if (NetworkUtils.isConnected(mContext)) {
                        hideLoading();
                        mRefreshLayout.autoRefresh();
                    } else {
                        ToastUtils.error(R.string.str_no_network);
                    }
                });
            }
        }
    }

    private void setUpRecyclerView() {
        mRvList.setLayoutManager(mLayoutManager);
        mRvList.setAdapter(mAdapter);
    }

    protected void initBusiness() {
        // 处理一些业务逻辑
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                L.e(TAG + " 开始下拉刷新");
                BaseListFragment.this.onRefresh(refreshlayout);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                L.e(TAG + " 开始下拉刷新");
                BaseListFragment.this.onLoadMore(refreshlayout);
            }
        });
    }

    @Override
    public void showTip(String tip) {
        ToastUtils.error(tip);
        finishRefresh();
        if (mRefreshLayout != null && mAdapter != null && mAdapter.getItemCount() == 0) {
            // 当前是空视图才去刷新
            mRefreshLayout.postDelayed(() -> {
                // 刷新页面看看是否为空页面
                L.e(TAG + " 请求网络失败 刷新结束后检查是否为空视图");
                if (mAdapter != null) mAdapter.notifyDataSetChanged();
            }, 250);
        }
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

    /**
     * 是否支持懒加载
     * 如果是在类似ViewPager模式下则可以实现类似微信的懒加载
     * 如果仅仅是单一列表则不需要支持懒加载模式
     * 此时加载数据在 {@link SupportFragment#onEnterAnimationEnd(Bundle)}
     * <p>
     * 默认支持懒加载模式
     * 如果不需要返回false
     */
    protected boolean supportLazyLoad() {
        return true;
    }

    protected abstract LM createLayoutManager();

    @NonNull
    protected abstract ADAPTER createAdapter();

    protected abstract void onRefresh(RefreshLayout refreshLayout);

    protected abstract void onLoadMore(RefreshLayout refreshLayout);
}
