package com.neurotech.photobrowser.bean;

import android.text.format.Formatter;

import com.neurotech.photobrowser.ui.adapter.base.ISelect;
import com.neurotech.photobrowser.utils.TimeUtils;
import com.neurotech.photobrowser.utils.UIUtils;

/**
 * Created by Administrator on 2017/10/21.
 */

public class AlbumBean implements ISelect {
    public static final int TYPE_PHOTO = 0;
    public static final int TYPE_VIDEO = 1;

    private int id = -1;
    private String title;  // 文件名称
    private String path;  // 文件路径
    private long size;  // 文件大小
    private Long dateTaken;  // 文件添加时间
    private long dateAdded;  // 文件添加时间
    private long dateModified;  // 文件修改时间
    private int classifyFlag;  // 类型 0 照片  1 视频
    private String mimeType;  // mime类型 image/jpeg
    private int width;  // 尺寸信息
    private int height;  // 尺寸信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public int getClassifyFlag() {
        return classifyFlag;
    }

    public void setClassifyFlag(int classifyFlag) {
        this.classifyFlag = classifyFlag;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "id --> " + id + "\n" +
                "title --> " + title + "\n" +
                "path --> " + path + "\n" +
                "size --> " + Formatter.formatFileSize(UIUtils.getContext(), size) + "\n" +
                "dateTaken --> " + TimeUtils.millis2String(dateTaken) + "\n" +
                "添加时间 --> " + TimeUtils.millis2String(dateAdded) + "\n" +
                "修改时间 --> " + TimeUtils.millis2String(dateModified) + "\n" +
                "mimeType --> " + mimeType + "\n" +
                "尺寸 --> " + width + "x" + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AlbumBean albumBean = (AlbumBean) o;
        return title.equals(albumBean.title) &&
                path.equals(albumBean.path) &&
                size == albumBean.size &&
                dateAdded == albumBean.dateAdded &&
                dateModified == albumBean.dateModified;
    }

    private boolean isSelected;

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
