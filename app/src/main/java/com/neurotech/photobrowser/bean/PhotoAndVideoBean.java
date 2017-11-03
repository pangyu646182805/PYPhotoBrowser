package com.neurotech.photobrowser.bean;

import com.neurotech.photobrowser.utils.TimeUtils;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class PhotoAndVideoBean extends FileBean {
    private Long dateTaken;  // 文件添加时间
    private int width;  // 尺寸信息
    private int height;  // 尺寸信息

    public Long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Long dateTaken) {
        this.dateTaken = dateTaken;
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
        return super.toString() + "\n" +
                "dateTaken --> " + TimeUtils.millis2String(dateTaken) + "\n" +
                "尺寸 --> " + width + "x" + height;
    }
}
