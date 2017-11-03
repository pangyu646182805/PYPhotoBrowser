package com.neurotech.photobrowser.ui.activities.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.flyco.systembar.SystemBarHelper;
import com.neurotech.photobrowser.MediaFolderSpinner;
import com.neurotech.photobrowser.MediaLoader;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.SelectionOptions;
import com.neurotech.photobrowser.adapter.FolderAdapter;
import com.neurotech.photobrowser.adapter.MediaAdapter;
import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.bean.FolderBean;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.SpacesItemDecoration;
import com.neurotech.photobrowser.utils.ToastUtils;
import com.neurotech.photobrowser.utils.UIUtils;
import com.neurotech.photobrowser.widget.slidingpanellayout.SlidingUpPanelLayout;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/10/30.
 */

public class PhotoSelectorActivity extends AppCompatActivity implements MediaLoader.MediaCallBack {
    private SelectionOptions mOptions;
    private final MediaLoader mMediaLoader = new MediaLoader();

    // private boolean mLoaderFlag;

    private View mStatusBar;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;
    private LinearLayout mLlDragView;
    private RecyclerView mRvList;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private MediaAdapter mMediaAdapter;
    private MediaFolderSpinner mFolderSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mOptions = SelectionOptions.getOptions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selector);
        SystemBarHelper.immersiveStatusBar(this);
        initView(savedInstanceState);
        initListener();

        mMediaLoader.onCreate(this, this);
        mMediaLoader.loadMedia();
    }

    private void setToolbarTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void setToolbarTitle(@StringRes int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }

    private void setDisplayHomeAsUpEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView(Bundle savedInstanceState) {
        mSlidingUpPanelLayout = findViewById(R.id.sliding_layout);
        mLlDragView = findViewById(R.id.ll_drag_view);
        mRvList = findViewById(R.id.rv_list);
        mAppBarLayout = findViewById(R.id.app_bar);
        mStatusBar = findViewById(R.id.status_bar);
        mToolbar = findViewById(R.id.tool_bar);

        mStatusBar.getLayoutParams().height = SystemBarHelper.getStatusBarHeight(this);
        // mStatusBar.requestLayout();

        setSupportActionBar(mToolbar);
        setDisplayHomeAsUpEnabled();
        setToolbarTitle("");

        mFolderSpinner = new MediaFolderSpinner(this);
        mFolderSpinner.setAdapter(new FolderAdapter(this, null));
        mFolderSpinner.setSelectedTextView(findViewById(R.id.tv_selected_folder));
        mFolderSpinner.setPopupAnchorView(mToolbar);

        // mRvList.setLayoutFrozen(true);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new GridLayoutManager(this, 4));
        mRvList.addItemDecoration(new SpacesItemDecoration((int) UIUtils.getDimen(R.dimen.x4), 4));
        mMediaAdapter = new MediaAdapter(this);
        mRvList.setAdapter(mMediaAdapter);

        mSlidingUpPanelLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSlidingUpPanelLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int appBarHeight = mAppBarLayout.getHeight();
                SlidingUpPanelLayout.LayoutParams params = (SlidingUpPanelLayout.LayoutParams) mLlDragView.getLayoutParams();
                params.topMargin = appBarHeight;
                // mLlDragView.setLayoutParams(params);
                mAppBarLayout.setTranslationY(-appBarHeight);
                float anchorPoint = 1 - appBarHeight * 1.0f / getWindowManager().getDefaultDisplay().getHeight();
                mSlidingUpPanelLayout.setAnchorPoint(anchorPoint);
                mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
            }
        });
    }

    private void initListener() {
        mSlidingUpPanelLayout.setFadeOnClickListener(view -> {
            ToastUtils.info("click fade area");
            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        });
        mSlidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                // L.e("previousState : " + previousState + " newState : " + newState);
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    onBackPressed();
                } /*else if (newState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                    if (!mLoaderFlag) {
                        L.e("面板展开之后加载数据");
                        // mMediaLoader.loadMedia();
                        mLoaderFlag = true;
                    }
                }*/
                if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING &&
                        newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    // 显示AppBar
                    showAppBarLayout();
                } else if (previousState == SlidingUpPanelLayout.PanelState.EXPANDED &&
                        newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    // 隐藏AppBar
                    hideAppBarLayout();
                }
            }
        });
    }

    private void showAppBarLayout() {
        ViewCompat.animate(mAppBarLayout)
                .translationY(0)
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void hideAppBarLayout() {
        ViewCompat.animate(mAppBarLayout)
                .translationY(-mAppBarLayout.getHeight())
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    @Override
    public void onBackPressed() {
        L.e("panel state : " + mSlidingUpPanelLayout.getPanelState());
        if (mSlidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.DRAGGING) {
            if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED ||
                    mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            } else {
                L.e("onBackPressed");
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onLoadFinished(ArrayList<FileBean> mediaList, ArrayList<FolderBean> folderList) {
        mMediaAdapter.setMediaList(mediaList);
        mFolderSpinner.swapFolderList(folderList);
    }

    @Override
    public void onLoaderReset() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        mMediaLoader.onDestroy();
        super.onDestroy();
    }
}
