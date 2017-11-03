package com.neurotech.photobrowser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.bean.FolderBean;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by NeuroAndroid on 2017/11/3.
 */

public class FolderAdapter extends BaseAdapter {
    private List<FolderBean> mFolderList = new ArrayList<>();
    private final Context mContext;

    public FolderAdapter(@NonNull Context context, List<FolderBean> folderList) {
        mContext = context;
        if (folderList != null) {
            mFolderList = folderList;
        }
    }

    public void setFolderList(ArrayList<FolderBean> folderList) {
        this.mFolderList = folderList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolderList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFolderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder;
        if (contentView == null) {
            holder = new ViewHolder();
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
            holder.ivCover = contentView.findViewById(R.id.iv_cover);
            holder.tvFolderName = contentView.findViewById(R.id.tv_folder_name);
            holder.tvFolderCount = contentView.findViewById(R.id.tv_folder_count);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        FolderBean folderBean = mFolderList.get(position);
        holder.ivCover.displayImage(folderBean.getFileList().get(0).getPath());
        holder.ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.tvFolderName.setText(folderBean.getFolderName());
        holder.tvFolderCount.setText(String.valueOf(folderBean.getFileList().size()));
        return contentView;
    }

    class ViewHolder {
        SketchImageView ivCover;
        TextView tvFolderName, tvFolderCount;
    }
}
