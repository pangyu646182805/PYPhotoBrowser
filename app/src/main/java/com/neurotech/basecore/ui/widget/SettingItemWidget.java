package com.neurotech.basecore.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.neurotech.basecore.R;
import com.neurotech.basecore.utils.UIUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class SettingItemWidget extends RelativeLayout {
    private Context mContext;
    private String mSettingItemText;
    private String mSettingItemSubTitle;
    private String mSettingItemContent;
    private boolean mSettingItemIconVisibility;
    private boolean mSettingItemArrowVisibility;
    private int mSettingItemLeftIcon = -1;
    private int mSettingItemRightIcon = -1;
    private NoPaddingTextView mTvSettingItemText;
    private AppCompatImageView mIvSettingItemIcon;
    private AppCompatImageView mIvSettingItemArrow;
    private NoPaddingTextView mTvContent;
    private int mSettingItemTextColor;
    private NoPaddingTextView mTvSubTitle;
    private int mSettingItemSubTextColor;
    private int mSettingItemContentTextColor;
    private float mSettingItemTextSize;
    private float mSettingItemSubTextSize;
    private float mSettingItemContentTextSize;
    private boolean mSettingItemDisplay;
    private int mSettingItemRightIconWidth;
    private int mSettingItemRightIconHeight;
    private CircleImageView mIvRightImg;
    private boolean mRightIconShowCircle;

    public SettingItemWidget(Context context) {
        this(context, null);
    }

    public SettingItemWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItemWidget, 0, 0);
            mSettingItemText = ta.getString(R.styleable.SettingItemWidget_setting_item_text);
            mSettingItemSubTitle = ta.getString(R.styleable.SettingItemWidget_setting_item_sub_title);
            mSettingItemContent = ta.getString(R.styleable.SettingItemWidget_setting_item_content);
            mSettingItemIconVisibility = ta.getBoolean(R.styleable.SettingItemWidget_setting_item_icon_visibility, false);
            mSettingItemArrowVisibility = ta.getBoolean(R.styleable.SettingItemWidget_setting_item_arrow_visibility, true);
            mSettingItemDisplay = ta.getBoolean(R.styleable.SettingItemWidget_setting_item_display, false);
            mSettingItemLeftIcon = ta.getResourceId(R.styleable.SettingItemWidget_setting_item_left_icon, -1);

            mSettingItemRightIcon = ta.getResourceId(R.styleable.SettingItemWidget_setting_item_right_icon, -1);
            mSettingItemRightIconWidth = (int) ta.getDimension(R.styleable.SettingItemWidget_setting_item_right_icon_width, LayoutParams.WRAP_CONTENT);
            mSettingItemRightIconHeight = (int) ta.getDimension(R.styleable.SettingItemWidget_setting_item_right_icon_height, LayoutParams.WRAP_CONTENT);

            mSettingItemTextColor = ta.getColor(R.styleable.SettingItemWidget_setting_item_text_color, getResources().getColor(R.color.colorGray333));
            mRightIconShowCircle = ta.getBoolean(R.styleable.SettingItemWidget_setting_item_right_icon_show_circle, false);
            mSettingItemSubTextColor = ta.getColor(R.styleable.SettingItemWidget_setting_item_sub_text_color, getResources().getColor(R.color.colorGray666));
            mSettingItemContentTextColor = ta.getColor(R.styleable.SettingItemWidget_setting_item_content_text_color, getResources().getColor(R.color.colorGray666));

            mSettingItemTextSize = ta.getDimension(R.styleable.SettingItemWidget_setting_item_text_size, UIUtils.getRawSize(context, TypedValue.COMPLEX_UNIT_SP, 14f));
            mSettingItemSubTextSize = ta.getDimension(R.styleable.SettingItemWidget_setting_item_sub_text_size, UIUtils.getRawSize(context, TypedValue.COMPLEX_UNIT_SP, 12f));
            mSettingItemContentTextSize = ta.getDimension(R.styleable.SettingItemWidget_setting_item_content_text_size, UIUtils.getRawSize(context, TypedValue.COMPLEX_UNIT_SP, 13f));
            ta.recycle();
        }
        setClickable(true);
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.widget_setting_item, this);
        mTvSettingItemText = findViewById(R.id.tv_setting_text);
        mTvSubTitle = findViewById(R.id.tv_sub_title);
        mIvSettingItemIcon = findViewById(R.id.iv_setting_icon);
        mIvSettingItemArrow = findViewById(R.id.iv_arrow);
        mTvContent = findViewById(R.id.tv_content);
        mIvRightImg = findViewById(R.id.iv_img);

        mTvSettingItemText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSettingItemTextSize);
        mTvSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSettingItemSubTextSize);
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSettingItemContentTextSize);

        mTvSubTitle.setTextColor(mSettingItemSubTextColor);
        mTvSettingItemText.setTextColor(mSettingItemTextColor);
        mTvContent.setTextColor(mSettingItemContentTextColor);

        if (UIUtils.isEmpty(mSettingItemSubTitle)) {
            mTvSubTitle.setVisibility(View.GONE);
        } else {
            mTvSubTitle.setText(mSettingItemSubTitle);
            mTvSubTitle.setVisibility(View.VISIBLE);
        }
        if (!UIUtils.isEmpty(mSettingItemText)) {
            mTvSettingItemText.setText(mSettingItemText);
        } else {
            mTvSettingItemText.setText("");
        }
        if (UIUtils.isEmpty(mSettingItemContent)) {
            mTvContent.setVisibility(View.GONE);
            mTvContent.setText("");
        } else {
            mTvContent.setVisibility(View.VISIBLE);
            mTvContent.setText(mSettingItemContent);
        }
        if (mSettingItemIconVisibility) {
            mIvSettingItemIcon.setVisibility(View.VISIBLE);
            if (mSettingItemLeftIcon != -1) {
                mIvSettingItemIcon.setImageResource(mSettingItemLeftIcon);
            }
        } else {
            mIvSettingItemIcon.setVisibility(View.GONE);
        }
        if (mSettingItemRightIcon != -1) {
            mTvContent.setVisibility(View.GONE);
            mTvContent.setText("");
            mIvRightImg.setVisibility(View.VISIBLE);
            if (mRightIconShowCircle) {
                // 右侧icon显示圆形
                mIvRightImg.setImageResource(mSettingItemRightIcon);
            } else {
                mIvRightImg.setBackgroundResource(mSettingItemRightIcon);
            }
            if (mSettingItemRightIconWidth != LayoutParams.WRAP_CONTENT) {
                mIvRightImg.getLayoutParams().width = mSettingItemRightIconWidth;
            }
            if (mSettingItemRightIconHeight != LayoutParams.WRAP_CONTENT) {
                mIvRightImg.getLayoutParams().width = mSettingItemRightIconHeight;
            }
            mIvRightImg.requestLayout();
        }
        mIvSettingItemArrow.setVisibility(mSettingItemArrowVisibility ? View.VISIBLE : View.GONE);
    }

    public SettingItemWidget setSettingItemText(String text) {
        mTvSettingItemText.setText(text);
        return this;
    }

    public SettingItemWidget setSettingItemLeftIcon(int resId) {
        mIvSettingItemIcon.setImageResource(resId);
        return this;
    }

    public String getSettingItemText() {
        return mTvSettingItemText.getText().toString();
    }

    public SettingItemWidget setSettingItemIconVisibility(boolean settingItemIconVisibility) {
        mIvSettingItemIcon.setVisibility(settingItemIconVisibility ? View.VISIBLE : View.GONE);
        return this;
    }

    public SettingItemWidget setSettingItemArrowVisibility(boolean settingItemArrowVisibility) {
        mIvSettingItemArrow.setVisibility(settingItemArrowVisibility ? View.VISIBLE : View.GONE);
        return this;
    }

    public SettingItemWidget setSettingItemContent(String content) {
        mTvContent.setVisibility(View.VISIBLE);
        mTvContent.setText(content);
        return this;
    }

    public String getContent() {
        return mTvContent.getText().toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHandler(widthMeasureSpec, (int) getResources().getDimension(R.dimen.x720));
        measureHandler(heightMeasureSpec, (int) getResources().getDimension(R.dimen.y88));
    }

    private int measureHandler(int measureSpec, int defaultSize) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mSettingItemDisplay) return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSettingItemDisplay) return false;
        return super.onTouchEvent(event);
    }
}
