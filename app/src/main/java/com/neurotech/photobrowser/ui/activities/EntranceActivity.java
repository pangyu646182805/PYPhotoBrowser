package com.neurotech.photobrowser.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.neurotech.photobrowser.MediaSelector;
import com.neurotech.photobrowser.PhotoSelectorConfig;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.SketchViewHolderCreator;
import com.neurotech.photobrowser.base.SupportActivity;
import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.MimeType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by NeuroAndroid on 2017/10/31.
 */

public class EntranceActivity extends SupportActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final int REQUEST_CODE_CHOOSE = 11;

    @BindView(R.id.rg_type)
    RadioGroup mRgGroup;
    @BindView(R.id.rb_all)
    RadioButton mRbAll;
    @BindView(R.id.rb_photo)
    RadioButton mRbPhoto;
    @BindView(R.id.rb_video)
    RadioButton mRbVideo;
    @BindView(R.id.rb_audio)
    RadioButton mRbAudio;

    @BindView(R.id.btn_span_count_cut)
    Button mBtnSpanCountCut;
    @BindView(R.id.tv_span_count)
    TextView mTvSpanCount;
    @BindView(R.id.btn_span_count_add)
    Button mBtnSpanCountAdd;

    @BindView(R.id.btn_max_selectable_cut)
    Button mBtnMaxSelectableCut;
    @BindView(R.id.tv_max_selectable)
    TextView mTvMaxSelectable;
    @BindView(R.id.btn_max_selectable_add)
    Button mBtnMaxSelectableAdd;

    @BindView(R.id.cb_single_mode)
    CheckBox mCbSingleMode;

    @BindView(R.id.cb_show_gif)
    CheckBox mCbShowGif;
    @BindView(R.id.cb_show_gif_flag)
    CheckBox mCbShowGifFlag;
    @BindView(R.id.cb_show_header_item)
    CheckBox mCbShowHeaderItem;
    @BindView(R.id.cb_canceled_touch_outside)
    CheckBox mCbCanceledOnTouchOutside;

    private int mChooseMode = MimeType.ALL;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_entrance;
    }

    @Override
    protected void initView() {
        getTitleBar().setPadding(0, SystemBarHelper.getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initListener() {
        mBtnMaxSelectableAdd.setOnClickListener(this);
        mBtnMaxSelectableCut.setOnClickListener(this);
        mBtnSpanCountAdd.setOnClickListener(this);
        mBtnSpanCountCut.setOnClickListener(this);
        mRgGroup.setOnCheckedChangeListener(this);

        mCbSingleMode.setOnCheckedChangeListener(this);
        mCbShowGif.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        int maxSelectable, spanCount;
        switch (view.getId()) {
            case R.id.btn_max_selectable_add:
                maxSelectable = getMaxSelectable();
                if (maxSelectable < 15) {
                    mTvMaxSelectable.setText(String.valueOf(maxSelectable + 1));
                }
                break;
            case R.id.btn_max_selectable_cut:
                maxSelectable = getMaxSelectable();
                if (maxSelectable > 1) {
                    mTvMaxSelectable.setText(String.valueOf(maxSelectable - 1));
                }
                break;
            case R.id.btn_span_count_add:
                spanCount = getSpanCount();
                if (spanCount < 8) {
                    mTvSpanCount.setText(String.valueOf(spanCount + 1));
                }
                break;
            case R.id.btn_span_count_cut:
                spanCount = getSpanCount();
                if (spanCount > 2) {
                    mTvSpanCount.setText(String.valueOf(spanCount - 1));
                }
                break;
        }
    }

    private int getMaxSelectable() {
        return Integer.parseInt(mTvMaxSelectable.getText().toString());
    }

    private int getSpanCount() {
        return Integer.parseInt(mTvSpanCount.getText().toString());
    }

    @OnClick(R.id.btn_start)
    public void start() {
        // startActivity(new Intent(this, PhotoSelectorActivity.class));
        MediaSelector.from(this)
                .choose(mChooseMode)
                .maxSelectable(getMaxSelectable())
                .gridSize(getSpanCount())
                .showGif(mCbShowGif.isChecked())
                .showGifFlag(mCbShowGifFlag.isChecked() && mCbShowGifFlag.isEnabled())
                // .setGifFlagResId(R.drawable.ic_gif_custom)
                .setBackgroundColor(Color.WHITE)
                .showHeaderItem(mCbShowHeaderItem.isChecked())
                .setCanceledOnTouchOutside(mCbCanceledOnTouchOutside.isChecked())
                .customViewHolder(new SketchViewHolderCreator())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_all:
                mChooseMode = MimeType.ALL;
                break;
            case R.id.rb_photo:
                mChooseMode = MimeType.PHOTO;
                break;
            case R.id.rb_video:
                mChooseMode = MimeType.VIDEO;
                break;
            case R.id.rb_audio:
                mChooseMode = MimeType.AUDIO;
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.cb_show_gif:
                mCbShowGifFlag.setEnabled(isChecked);
                break;
            case R.id.cb_single_mode:
                mBtnMaxSelectableCut.setEnabled(!isChecked);
                mBtnMaxSelectableAdd.setEnabled(!isChecked);
                mTvMaxSelectable.setText(isChecked ? "1" : mTvMaxSelectable.getText());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<FileBean> selectedItems = data.getParcelableArrayListExtra(PhotoSelectorConfig.EXTRA_RESULT_SELECTED_ITEMS);
            for (FileBean item : selectedItems) {
                L.e("selected item path : " + item.getPath());
            }
        }
    }
}
