package com.neurotech.photobrowser.ui.adapter;

import android.content.Context;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.bean.AlbumBean;
import com.neurotech.photobrowser.config.Constants;
import com.neurotech.photobrowser.ui.adapter.base.BaseSelectAdapter;
import com.neurotech.photobrowser.ui.adapter.base.BaseViewHolder;
import com.neurotech.photobrowser.ui.adapter.base.IMultiItemViewType;
import com.neurotech.photobrowser.utils.TimeUtils;

import java.util.List;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by NeuroAndroid on 2017/10/23.
 */

public class PhotoAdapter extends BaseSelectAdapter<AlbumBean> {
    private static final int VIEW_TYPE_TIME = 0;
    private static final int VIEW_TYPE_ALBUM = 1;

    public PhotoAdapter(Context context, List<AlbumBean> dataList, IMultiItemViewType<AlbumBean> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, AlbumBean item, int position, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TIME:
                holder.setText(R.id.tv_time, TimeUtils.millis2String(item.getDateTaken(), Constants.M_M_D_D));
                break;
            case VIEW_TYPE_ALBUM:
                SketchImageView ivImg = holder.getView(R.id.iv_img);
                // ivImg.getLayoutParams().width = (int) (UIUtils.getDimen(R.dimen.x720) / 3);
                // ivImg.getLayoutParams().height = (int) (UIUtils.getDimen(R.dimen.x720) / 3);
                ivImg.displayImage(item.getPath());
                // ivImg.setOptions(PhotoOptions.getPhotoOptions(3));
                break;
        }
    }

    @Override
    protected IMultiItemViewType<AlbumBean> provideMultiItemViewType() {
        return new IMultiItemViewType<AlbumBean>() {
            @Override
            public int getItemViewType(int position, AlbumBean albumBean) {
                return albumBean.getId() == -1 ? VIEW_TYPE_TIME : VIEW_TYPE_ALBUM;
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == VIEW_TYPE_TIME) {
                    return R.layout.item_photo_time;
                } else {
                    return R.layout.item_photo;
                }
            }
        };
    }
}
