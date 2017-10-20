package com.neurotech.basecore.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewStub;

import com.neurotech.basecore.R;

/**
 * Created by NeuroAndroid on 2017/10/11.
 */

public class TitleBar extends AppBarLayout {
    private static final int NULL_RES_ID = -1;

    private Context mContext;
    @LayoutRes
    private int mCenterLayoutResId = NULL_RES_ID;
    private String mTitle = "";
    private Toolbar mToolbar;
    private int mComposeLayoutResId = NULL_RES_ID;

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            mCenterLayoutResId = typedArray.getResourceId(R.styleable.TitleBar_center_layout, NULL_RES_ID);
            mComposeLayoutResId = typedArray.getResourceId(R.styleable.TitleBar_tb_compose_layout, NULL_RES_ID);
            mTitle = typedArray.getString(R.styleable.TitleBar_tb_title);
            typedArray.recycle();
        }
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_toolbar, this);
        mToolbar = findViewById(R.id.tool_bar);
        initViewStub();
    }

    private void initViewStub() {
        if (!TextUtils.isEmpty(mTitle)) {
            // 如果有title
            mToolbar.setTitle(mTitle);
        } else {
            // 加载自定义layout
            if (mCenterLayoutResId != NULL_RES_ID) {
                ViewStub stub = findViewById(R.id.view_stub);
                stub.setLayoutResource(mCenterLayoutResId);
                stub.setInflatedId(R.id.tb_center_layout);
                stub.inflate();
            }
        }
        if (mComposeLayoutResId != NULL_RES_ID) {
            ViewStub stub = findViewById(R.id.compose_view_stub);
            stub.setLayoutResource(mComposeLayoutResId);
            stub.setInflatedId(R.id.tb_compose_layout);
            stub.inflate();
        }
    }

    /**
     * 设置Title和Icon的距离
     */
    public void setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        if (mToolbar != null) mToolbar.setContentInsetStartWithNavigation(insetStartWithNavigation);
    }
}
