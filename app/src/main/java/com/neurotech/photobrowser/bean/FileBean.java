package com.neurotech.photobrowser.bean;

import android.text.format.Formatter;

import com.neurotech.photobrowser.utils.MimeType;
import com.neurotech.photobrowser.utils.TimeUtils;
import com.neurotech.photobrowser.utils.UIUtils;

import java.io.Serializable;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class FileBean implements Serializable {
    private int id = -1;
    private String title;  // 文件名称
    private String path;  // 文件路径
    private long size;  // 文件大小
    private long dateAdded;  // 文件添加时间
    private long dateModified;  // 文件修改时间
    @MimeType.MediaMimeType
    private int mediaMimeType;
    private String mimeType;  // mime类型 image/jpeg
    private boolean isSelected;
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

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

    public int getMediaMimeType() {
        return mediaMimeType;
    }

    public void setMediaMimeType(int mediaMimeType) {
        this.mediaMimeType = mediaMimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "id --> " + id + "\n" +
                "title --> " + title + "\n" +
                "path --> " + path + "\n" +
                "size --> " + Formatter.formatFileSize(UIUtils.getContext(), size) + "\n" +
                "添加时间 --> " + TimeUtils.millis2String(dateAdded) + "\n" +
                "修改时间 --> " + TimeUtils.millis2String(dateModified) + "\n" +
                "mimeType --> " + mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FileBean albumBean = (FileBean) o;
        return title.equals(albumBean.title) &&
                path.equals(albumBean.path) &&
                size == albumBean.size &&
                dateAdded == albumBean.dateAdded &&
                dateModified == albumBean.dateModified;
    }
}
