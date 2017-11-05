package com.neurotech.photobrowser.ui.activities;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.neurotech.photobrowser.MediaSelector;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.SketchViewHolderCreator;
import com.neurotech.photobrowser.base.SupportActivity;
import com.neurotech.photobrowser.utils.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by NeuroAndroid on 2017/10/31.
 */

public class EntranceActivity extends SupportActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
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
                if (maxSelectable > 2) {
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
                .customViewHolder(new SketchViewHolderCreator())
                .forResult(0);
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
}
