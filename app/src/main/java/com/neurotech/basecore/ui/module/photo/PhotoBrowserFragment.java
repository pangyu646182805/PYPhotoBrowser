package com.neurotech.basecore.ui.module.photo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import com.neurotech.basecore.R;
import com.neurotech.basecore.base.SupportFragment;
import com.neurotech.basecore.bean.AlbumBean;
import com.neurotech.basecore.bean.AlbumCategoryBean;
import com.neurotech.basecore.config.Constants;
import com.neurotech.basecore.loader.AlbumCategoryComparator;
import com.neurotech.basecore.loader.AlbumComparator;
import com.neurotech.basecore.loader.PhotoLoader;
import com.neurotech.basecore.loader.VideoLoader;
import com.neurotech.basecore.loader.WrappedAsyncTaskLoader;
import com.neurotech.basecore.ui.adapter.base.BaseRvAdapter;
import com.neurotech.basecore.ui.adapter.base.BaseViewHolder;
import com.neurotech.basecore.utils.DividerUtils;
import com.neurotech.basecore.utils.L;
import com.neurotech.basecore.utils.TimeUtils;

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
        implements LoaderManager.LoaderCallbacks<ArrayList<AlbumBean>> {
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

    @Override
    protected void initData() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<AlbumBean>> onCreateLoader(int id, Bundle args) {
        return new AsyncAlbumLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AlbumBean>> loader, ArrayList<AlbumBean> data) {
        /*for (PYFileBean pyFileBean : data) {
            L.e(pyFileBean.getTitle() + " -- " + pyFileBean.getPath());
        }*/
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AlbumBean>> loader) {

    }

    private static class AsyncAlbumLoader extends WrappedAsyncTaskLoader<ArrayList<AlbumBean>> {
        AsyncAlbumLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<AlbumBean> loadInBackground() {
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

            List<AlbumCategoryBean> albumCategoryList = new ArrayList<>();
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
            return dataList;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
