package com.neurotech.basecore.ui.module.photo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neurotech.basecore.R;
import com.neurotech.basecore.base.SupportFragment;
import com.neurotech.basecore.ui.adapter.base.BaseRvAdapter;
import com.neurotech.basecore.ui.adapter.base.BaseViewHolder;
import com.neurotech.basecore.utils.DividerUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/10/20.
 */

public class PhotoBrowserFragment extends SupportFragment {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    public static PhotoBrowserFragment newInstance() {
        return new PhotoBrowserFragment();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_photo_browser;
    }

    @Override
    protected void initTitleBar() {
        setDisplayHomeAsUpEnabled();
        setContentInsetStartWithNavigation(0);
    }

    @Override
    protected void initView() {
        super.initView();

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("py --> " + i);
        }

        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mRvList.setAdapter(new BaseRvAdapter<String>(mContext, data, R.layout.item_menu) {
            @Override
            public void convert(BaseViewHolder holder, String item, int position, int viewType) {
                holder.setText(R.id.tv_menu_text, item);
            }
        });
    }
}
