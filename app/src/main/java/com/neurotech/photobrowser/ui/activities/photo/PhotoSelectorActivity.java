package com.neurotech.photobrowser.ui.activities.photo;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.flyco.systembar.SystemBarHelper;
import com.neurotech.photobrowser.DataTransferStation;
import com.neurotech.photobrowser.MediaFolderSpinner;
import com.neurotech.photobrowser.MediaLoader;
import com.neurotech.photobrowser.PhotoGallery;
import com.neurotech.photobrowser.PhotoSelectorConfig;
import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.SelectionOptions;
import com.neurotech.photobrowser.adapter.FolderAdapter;
import com.neurotech.photobrowser.adapter.MediaAdapter;
import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.bean.FolderBean;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.SpacesItemDecoration;
import com.neurotech.photobrowser.utils.UIUtils;
import com.neurotech.photobrowser.widget.slidingpanellayout.SlidingUpPanelLayout;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/10/30.
 */

public class PhotoSelectorActivity extends AppCompatActivity implements MediaLoader.MediaCallBack, AdapterView.OnItemClickListener {
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
    private DataTransferStation mDataTransferStation;
    // private List<FileBean> mSelectedItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mOptions = SelectionOptions.getOptions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selector);
        SystemBarHelper.immersiveStatusBar(this);
        initView(savedInstanceState);
        initListener();

        mDataTransferStation = DataTransferStation.getInstance();
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
        mFolderSpinner.setOnItemClickListener(this);

        // mRvList.setLayoutFrozen(true);
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new GridLayoutManager(this, mOptions.gridSize));
        mRvList.addItemDecoration(new SpacesItemDecoration((int) UIUtils.getDimen(R.dimen.x4), mOptions.gridSize));
        mMediaAdapter = new MediaAdapter(this, mRvList);
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
        if (mOptions.canceledOnTouchOutside) {
            mSlidingUpPanelLayout.setFadeOnClickListener(view ->
                    mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED));
        }
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
        mMediaAdapter.setOnItemSelectedListener((viewHolder, position, isSelected, item) -> {
            onItemSelected(isSelected, item);
        });
        mMediaAdapter.setOnItemClickListener((viewHolder, position, fileBean) ->
                preview(mOptions.showHeaderItem ? position - 1 : position));
    }

    /**
     * 预览图片或者视频
     *
     * @param position 等于-1则是预览选择的item
     */
    private void preview(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        PhotoGallery.openPhotoGallery(this, bundle);
    }

    /**
     * item选中/反选
     *
     * @param isSelected 是否被勾选
     * @param item       item实体类
     */
    private void onItemSelected(boolean isSelected, FileBean item) {
        if (isSelected) {
            mDataTransferStation.putSelectedItem(item);
            if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
                mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        } else {
            mDataTransferStation.removeFromSelectedItems(item);
        }
        invalidateOptionsMenu();
        mSlidingUpPanelLayout.setTouchEnabled(mDataTransferStation.getSelectedItems().isEmpty());
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
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == PhotoSelectorConfig.REQUEST_CODE_PREVIEW) {
            if (data != null) {
                ArrayList<FileBean> selectedItems = mDataTransferStation.getSelectedItems();
                if (data.getBooleanExtra(PhotoSelectorConfig.EXTRA_RESULT_APPLY, false)) {
                    // 发送出去
                    onResult(selectedItems);
                } else {
                    mMediaAdapter.notifyDataSetChanged();
                    if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
                        mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    }
                    invalidateOptionsMenu();
                    mSlidingUpPanelLayout.setTouchEnabled(mDataTransferStation.getSelectedItems().isEmpty());
                }
            }
        }
    }

    private void onResult(ArrayList<FileBean> selectedItems) {
        Intent result = new Intent();
        result.putParcelableArrayListExtra(PhotoSelectorConfig.EXTRA_RESULT_SELECTED_ITEMS, selectedItems);
        setResult(RESULT_OK, result);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        L.e("panel state : " + mSlidingUpPanelLayout.getPanelState());
        if (mSlidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.DRAGGING) {
            if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED ||
                    mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                hideAppBarLayout();
            } else {
                L.e("onBackPressed");
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onLoadFinished(ArrayList<FileBean> mediaList, ArrayList<FolderBean> folderList) {
        // 将数据存入临时的数据存储点
        mDataTransferStation.putItems(mediaList);
        mMediaAdapter.setMediaList(mediaList);
        mFolderSpinner.swapFolderList(folderList);
    }

    @Override
    public void onLoaderReset() {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem previewItem = menu.findItem(R.id.action_preview);
        MenuItem doneItem = menu.findItem(R.id.action_done);
        ArrayList<FileBean> selectedImages = mDataTransferStation.getSelectedItems();
        if (selectedImages.isEmpty()) {
            previewItem.setEnabled(false);
            doneItem.setEnabled(false);
            doneItem.setTitle("完成(0/" + mOptions.maxSelectable + ")");
        } else {
            previewItem.setEnabled(true);
            doneItem.setEnabled(true);
            doneItem.setTitle("完成(" + selectedImages.size() + "/" + mOptions.maxSelectable + ")");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_preview:
                preview(-1);
                break;
            case R.id.action_done:
                onResult(mDataTransferStation.getSelectedItems());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mMediaLoader.onDestroy();
        if (mDataTransferStation != null) {
            mDataTransferStation.onDestroy();
            mDataTransferStation = null;
        }
        super.onDestroy();
    }

    /**
     * MediaFolderSpinner的item点击事件回调
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        FolderAdapter folderAdapter = mFolderSpinner.getFolderAdapter();
        if (null != folderAdapter) {
            FolderBean folderBean = folderAdapter.getItem(position);
            ArrayList<FileBean> fileList = folderBean.getFileList();
            if (null != fileList) {
                mMediaAdapter.setMediaList(fileList);
            }
        }
    }
}
