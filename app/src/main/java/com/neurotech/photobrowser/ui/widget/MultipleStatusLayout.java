package com.neurotech.photobrowser.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neurotech.photobrowser.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by NeuroAndroid on 2017/10/11.
 */

public class MultipleStatusLayout extends FrameLayout implements View.OnClickListener {
    public static final int STATUS_HIDE = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_ERROR = 2;

    private Context mContext;
    private int mCurrentStatus = STATUS_HIDE;
    private int mImgRes;
    private String mErrorText, mReloadBtnText;
    private int mImgWidth;
    private int mImgHeight;

    private LinearLayout mLlError;
    private LinearLayout mLlLoading;
    private ImageView mIvError;
    private NoPaddingTextView mTvError;
    private NoPaddingTextView mBtnReload;

    public MultipleStatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public MultipleStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MultipleStatusLayout);
            mImgRes = typedArray.getResourceId(R.styleable.MultipleStatusLayout_msv_img_res, -1);
            mErrorText = typedArray.getString(R.styleable.MultipleStatusLayout_msv_error_text);
            mReloadBtnText = typedArray.getString(R.styleable.MultipleStatusLayout_msv_reload_text);
            mImgWidth = (int) typedArray.getDimension(R.styleable.MultipleStatusLayout_msv_img_width, -1);
            mImgHeight = (int) typedArray.getDimension(R.styleable.MultipleStatusLayout_msv_img_height, -1);
            mCurrentStatus = typedArray.getInt(R.styleable.MultipleStatusLayout_msv_status, mCurrentStatus);
            typedArray.recycle();
        }
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_multiple_status, this, true);
        mLlError = findViewById(R.id.ll_error);
        mLlLoading = findViewById(R.id.ll_loading);
        mIvError = findViewById(R.id.iv_error);
        mTvError = findViewById(R.id.tv_error);
        mBtnReload = findViewById(R.id.btn_reload);
        switchView();
        mBtnReload.setOnClickListener(this);
        if (mImgRes != -1) {
            setImageResource(mImgRes);
        }
        if (!TextUtils.isEmpty(mErrorText)) {
            setErrorText(mErrorText);
        }
        if (!TextUtils.isEmpty(mReloadBtnText)) {
            setReloadBtnText(mReloadBtnText);
        }
        if (mImgWidth != -1) {
            mIvError.getLayoutParams().width = mImgWidth;
        }
        if (mImgHeight != -1) {
            mIvError.getLayoutParams().height = mImgHeight;
        }
        mIvError.requestLayout();
    }

    /**
     * 切换不同状态
     */
    private void switchView() {
        switch (mCurrentStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mLlError.setVisibility(GONE);
                mLlLoading.setAlpha(0);
                mLlLoading.setVisibility(View.VISIBLE);
                ViewCompat.animate(mLlLoading).alpha(1f).setDuration(300).start();
                setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case STATUS_ERROR:
                setVisibility(VISIBLE);
                mLlError.setAlpha(0);
                mLlError.setVisibility(VISIBLE);
                ViewCompat.animate(mLlError).alpha(1f).setDuration(300).start();
                mLlLoading.setVisibility(View.GONE);
                setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
        }
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mCurrentStatus = STATUS_HIDE;
        switchView();
    }

    public MultipleStatusLayout setErrorImgLayoutParams(float width, float height) {
        mIvError.getLayoutParams().width = (int) width;
        mIvError.getLayoutParams().height = (int) height;
        mIvError.requestLayout();
        return this;
    }

    /**
     * 设置状态
     */
    public void setStatus(@LoadingState int status) {
        mCurrentStatus = status;
        switchView();
    }

    /**
     * 设置图片资源
     */
    public MultipleStatusLayout setImageResource(int resId) {
        mIvError.setImageResource(resId);
        return this;
    }

    /**
     * 设置提示文本
     */
    public MultipleStatusLayout setErrorText(String errorText) {
        mTvError.setText(errorText);
        return this;
    }

    /**
     * 设置重新加载文本
     */
    public MultipleStatusLayout setReloadBtnText(String reloadBtnText) {
        mBtnReload.setText(reloadBtnText);
        return this;
    }

    /**
     * 获取状态
     */
    public int getStatus() {
        return mCurrentStatus;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reload:
                if (mOnRetryListener != null) {
                    mOnRetryListener.onRetry(view);
                }
                break;
        }
    }

    private OnRetryListener mOnRetryListener;

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry(View view);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_HIDE, STATUS_LOADING, STATUS_ERROR})
    public @interface LoadingState {
    }
}
