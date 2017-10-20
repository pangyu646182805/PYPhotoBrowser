package com.neurotech.basecore.ui.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.neurotech.basecore.R;
import com.neurotech.basecore.bean.MenuItem;
import com.neurotech.basecore.interfaces.OnItemClickListener;
import com.neurotech.basecore.ui.adapter.base.BaseRvAdapter;
import com.neurotech.basecore.ui.adapter.base.BaseViewHolder;
import com.neurotech.basecore.ui.widget.dialog.base.BaseDialog;
import com.neurotech.basecore.utils.DividerUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class MenuListDialog extends BaseDialog<MenuListDialog> {
    private MenuListAdapter mMenuListAdapter;
    private boolean showIcon;

    public MenuListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_menu_list_dialog;
    }

    @Override
    protected void initView() {
        RecyclerView rvList = getView(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));

        mMenuListAdapter = new MenuListAdapter(mContext, null, R.layout.item_menu);
        rvList.setAdapter(mMenuListAdapter);
    }

    public MenuListDialog setOnItemClickListener(OnItemClickListener<MenuItem> listener) {
        if (mMenuListAdapter != null) {
            mMenuListAdapter.setOnItemClickListener(listener);
        }
        return this;
    }

    /**
     * 是否显示菜单图标
     */
    public MenuListDialog showIcon(boolean show) {
        this.showIcon = show;
        return this;
    }

    /**
     * 添加单个菜单
     */
    public MenuListDialog addMenuItem(MenuItem item) {
        if (mMenuListAdapter != null) mMenuListAdapter.add(item);
        return this;
    }

    /**
     * 添加多个菜单
     */
    public MenuListDialog addMenuList(List<MenuItem> list) {
        if (mMenuListAdapter != null) mMenuListAdapter.addAll(list);
        return this;
    }

    private class MenuListAdapter extends BaseRvAdapter<MenuItem> {
        MenuListAdapter(Context context, List<MenuItem> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, MenuItem item, int position, int viewType) {
            if (showIcon) {
                holder.setVisibility(R.id.iv_menu_icon, View.VISIBLE)
                        .setImageResource(R.id.iv_menu_icon, item.getIcon());
            } else {
                holder.setVisibility(R.id.iv_menu_icon, View.GONE);
            }
            holder.setText(R.id.tv_menu_text, item.getText())
                    .setTextSize(R.id.tv_menu_text, 16);
        }
    }
}
