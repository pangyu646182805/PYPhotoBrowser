package com.neurotech.photobrowser.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.SelectionOptions;
import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.utils.MimeType;
import com.neurotech.photobrowser.utils.UIUtils;

import java.util.ArrayList;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MEDIA_HEADER = 0;
    private static final int VIEW_TYPE_MEDIA_CONTENT = 1;

    private final Context mContext;
    private ArrayList<FileBean> mMediaList = new ArrayList<>();
    private SelectionOptions mOptions;

    public MediaAdapter(Context context) {
        mContext = context;
        mOptions = SelectionOptions.getOptions();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MEDIA_HEADER) {
            return new MediaHeaderViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media_header, parent, false));
        } else {
            /*return new MediaContentViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media_content, parent, false));*/
            return mOptions.viewHolderCreator.createViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_MEDIA_HEADER) {
            MediaHeaderViewHolder mediaHeaderViewHolder = (MediaHeaderViewHolder) holder;
        } else {
            // MediaContentViewHolder mediaContentViewHolder = (MediaContentViewHolder) holder;
            FileBean fileBean = mMediaList.get(position - 1);
            if (null != fileBean && fileBean.getMediaMimeType() == MimeType.PHOTO) {
                /*Sketch.with(mContext)
                        .display(fileBean.getPath(), mediaContentViewHolder.mIvImg)
                        .maxSize((int) UIUtils.getDimen(R.dimen.x720) / 4, (int) UIUtils.getDimen(R.dimen.x720) / 4)
                        .resize((int) UIUtils.getDimen(R.dimen.x720) / 4, (int) UIUtils.getDimen(R.dimen.x720) / 4)
                        .cacheProcessedImageInDisk()
                        .loadingImage(R.drawable.img_loading)
                        .displayer(new TransitionImageDisplayer())
                        .thumbnailMode()
                        .commit();*/
                mOptions.viewHolderCreator.onBindViewHolder(holder, fileBean);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1 + mMediaList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_MEDIA_HEADER;
        } else {
            return VIEW_TYPE_MEDIA_CONTENT;
        }
    }

    public void setMediaList(ArrayList<FileBean> data) {
        this.mMediaList = data;
        notifyDataSetChanged();
    }

    public class MediaHeaderViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView ivHeader;
        private TextView tvTitle;

        MediaHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MediaContentViewHolder extends RecyclerView.ViewHolder {
        SketchImageView mIvImg;

        MediaContentViewHolder(View itemView) {
            super(itemView);
            mIvImg = itemView.findViewById(R.id.iv_img);
            ViewGroup.LayoutParams layoutParams = mIvImg.getLayoutParams();
            if (null != layoutParams) {
                layoutParams.width = (int) UIUtils.getDimen(R.dimen.x720) / 4;
                layoutParams.height = (int) UIUtils.getDimen(R.dimen.x720) / 4;
                mIvImg.setLayoutParams(layoutParams);
            }
        }
    }
}
