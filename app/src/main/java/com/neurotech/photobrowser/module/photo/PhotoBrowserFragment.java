package com.neurotech.photobrowser.module.photo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.base.SupportFragment;
import com.neurotech.photobrowser.bean.AlbumBean;
import com.neurotech.photobrowser.bean.AlbumCategoryBean;
import com.neurotech.photobrowser.config.Constants;
import com.neurotech.photobrowser.loader.AlbumCategoryComparator;
import com.neurotech.photobrowser.loader.AlbumComparator;
import com.neurotech.photobrowser.loader.PhotoLoader;
import com.neurotech.photobrowser.loader.VideoLoader;
import com.neurotech.photobrowser.loader.WrappedAsyncTaskLoader;
import com.neurotech.photobrowser.ui.adapter.PhotoAdapter;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/10/20.
 */

public class PhotoBrowserFragment extends SupportFragment
        implements LoaderManager.LoaderCallbacks<ArrayList<AlbumCategoryBean>> {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private PhotoAdapter mPhotoAdapter;
    private GridLayoutManager mGridLayoutManager;

    public static PhotoBrowserFragment newInstance() {
        return new PhotoBrowserFragment();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_photo_browser;
    }

    @Override
    protected void initView() {
        super.initView();

        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return PhotoBrowserFragment.this.getSpanSize(position);
            }
        });
        mRvList.setLayoutManager(mGridLayoutManager);
        mPhotoAdapter = new PhotoAdapter(mContext, null, null);
        mRvList.setAdapter(mPhotoAdapter);
    }

    private int getSpanSize(int position) {
        int spanSize;
        AlbumBean item = mPhotoAdapter.getItem(position);
        if (item.getId() == -1) {
            spanSize = 3;
        } else {
            spanSize = 1;
        }
        return spanSize;
    }

    @Override
    protected void initData() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<AlbumCategoryBean>> onCreateLoader(int id, Bundle args) {
        return new AsyncAlbumLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AlbumCategoryBean>> loader, ArrayList<AlbumCategoryBean> data) {
        /*for (PYFileBean pyFileBean : data) {
            L.e(pyFileBean.getTitle() + " -- " + pyFileBean.getPath());
        }*/
        List<AlbumBean> dataList = new ArrayList<>();
        AlbumBean albumBean;
        for (AlbumCategoryBean albumCategoryBean : data) {
            albumBean = new AlbumBean();
            albumBean.setDateTaken(albumCategoryBean.getCategoryTime());
            dataList.add(albumBean);
            dataList.addAll(albumCategoryBean.getAlbumList());
        }
        mPhotoAdapter.replaceAll(dataList);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AlbumCategoryBean>> loader) {

    }

    private static class AsyncAlbumLoader extends WrappedAsyncTaskLoader<ArrayList<AlbumCategoryBean>> {
        AsyncAlbumLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<AlbumCategoryBean> loadInBackground() {
            long start = System.currentTimeMillis();
            ArrayList<AlbumBean> dataList = new ArrayList<>();
            dataList.addAll(PhotoLoader.getAllPhotos(getContext()));
            dataList.addAll(VideoLoader.getAllVideos(getContext()));
            L.e("albums size --> " + dataList.size());
            L.e("getAllAlbums time : " + (System.currentTimeMillis() - start));
            Collections.sort(dataList, new AlbumComparator());

            start = System.currentTimeMillis();
            Map<Long, List<AlbumBean>> map = new TreeMap<>(new AlbumCategoryComparator());
            List<AlbumBean> list;
            for (int i = 0; i < dataList.size(); i++) {
                AlbumBean albumBean = dataList.get(i);
                long categoryTime = TimeUtils.string2Millis(TimeUtils.millis2String(albumBean.getDateTaken(),
                        Constants.YYYY_MM_DD), Constants.YYYY_MM_DD);
                if (!map.containsKey(categoryTime)) {
                    list = new ArrayList<>();
                    list.add(albumBean);
                    map.put(categoryTime, list);
                } else {
                    list = map.get(categoryTime);
                    list.add(albumBean);
                }
            }

            ArrayList<AlbumCategoryBean> albumCategoryList = new ArrayList<>();
            AlbumCategoryBean albumCategoryBean;
            for (Map.Entry<Long, List<AlbumBean>> entry : map.entrySet()) {
                long categoryTime = entry.getKey();
                List<AlbumBean> albumList = entry.getValue();
                albumCategoryBean = new AlbumCategoryBean();
                albumCategoryBean.setAlbumList(albumList);
                albumCategoryBean.setCategoryTime(categoryTime);
                albumCategoryList.add(albumCategoryBean);
                L.e(TimeUtils.millis2String(categoryTime));
            }
            L.e("result list size --> " + albumCategoryList.size());
            L.e("get result time : " + (System.currentTimeMillis() - start));
            return albumCategoryList;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
