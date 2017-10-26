package com.neurotech.photobrowser.module.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.base.BaseListFragment;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.bean.MenuItem;
import com.neurotech.photobrowser.bean.PrescriptionDTO;
import com.neurotech.photobrowser.config.Constants;
import com.neurotech.photobrowser.model.response.PrescriptionsResponse;
import com.neurotech.photobrowser.mvp.contract.IPrescriptionsContract;
import com.neurotech.photobrowser.mvp.presenter.PrescriptionsPresenter;
import com.neurotech.photobrowser.ui.adapter.PrescriptionsAdapter;
import com.neurotech.photobrowser.ui.widget.dialog.MenuListDialog;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class PrescriptionsFragment extends BaseListFragment<IPrescriptionsContract.Presenter,
        PrescriptionsAdapter, LinearLayoutManager> implements IPrescriptionsContract.View {
    public static PrescriptionsFragment newInstance() {
        return new PrescriptionsFragment();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PrescriptionsPresenter(this);
    }

    @Override
    protected LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @NonNull
    @Override
    protected PrescriptionsAdapter createAdapter() {
        return new PrescriptionsAdapter(mContext, null, null);
    }

    @Override
    protected void initBusiness() {
        super.initBusiness();
        getAdapter().setOnItemClickListener((holder, position, item) -> {
            TestFragment testFragment = (TestFragment) getParentFragment();
            if (testFragment != null) {
                testFragment.startBrotherFragment(PrescriptionsFragment.newInstance());
            } else {
                findFragment(TestFragment.class).startBrotherFragment(PrescriptionsFragment.newInstance());
            }
        });
        getAdapter().setOnItemLongClickListener((holder, position, item) -> {
            new MenuListDialog(mContext)
                    .addMenuItem(new MenuItem("拍照"))
                    .addMenuItem(new MenuItem("相册"))
                    .addMenuItem(new MenuItem("Toast"))
                    .setOnItemClickListener((h, p, i) -> {
                        ToastUtils.showLong(position + i.getText());
                    })
                    .showDialog();
        });
    }

    @Override
    protected void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getPrescriptionList(100000161, 0, PAGE_SIZE);
    }

    @Override
    protected void onLoadMore(RefreshLayout refreshLayout) {
        mPresenter.getLoadMorePrescriptionList(100000161, mPage, PAGE_SIZE);
    }

    @Override
    public void showResponse(Response<BaseResponse<PrescriptionsResponse>> response) {
        finishRefresh();
        boolean fromCache = response.isFromCache();
        L.e("fromCache : " + fromCache);
        BaseResponse<PrescriptionsResponse> prescriptionsResponse = response.body();
        if (prescriptionsResponse.getCode() == Constants.RESPONSE_CODE_OK) {
            List<PrescriptionDTO> dataList = prescriptionsResponse.getData().getData();
            mPage = 1;
            if (fromCache) {
                if (!mIsInitCache) {
                    getAdapter().replaceAll(dataList);
                    mIsInitCache = true;
                }
            } else {
                getAdapter().replaceAll(dataList);
            }
        } else if (prescriptionsResponse.getCode() == Constants.RESPONSE_CODE_NO_CONTENT) {
            // 第一次刷新没有内容显示空视图
        } else {
            showTip(prescriptionsResponse.getMsg());
        }
    }

    @Override
    public void showLoadMoreResponse(Response<BaseResponse<PrescriptionsResponse>> response) {
        finishRefresh();
        boolean fromCache = response.isFromCache();
        L.e("showLoadMoreResponse fromCache : " + fromCache);
        BaseResponse<PrescriptionsResponse> prescriptionsResponse = response.body();
        if (prescriptionsResponse.getCode() == Constants.RESPONSE_CODE_OK) {
            mPage++;
            List<PrescriptionDTO> dataList = prescriptionsResponse.getData().getData();
            getAdapter().addAll(dataList);
        } else if (prescriptionsResponse.getCode() == Constants.RESPONSE_CODE_NO_CONTENT) {
            ToastUtils.info("没有更多");
        } else {
            showTip(prescriptionsResponse.getMsg());
        }
    }

    @Override
    protected boolean supportLazyLoad() {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
