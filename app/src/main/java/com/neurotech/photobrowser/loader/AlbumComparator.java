package com.neurotech.photobrowser.loader;

import com.neurotech.photobrowser.bean.FileBean;
import com.neurotech.photobrowser.bean.PhotoAndVideoBean;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/10/22.
 */

public class AlbumComparator implements Comparator<FileBean> {
    @Override
    public int compare(FileBean fileBean, FileBean other) {
        Long time, compareTime;
        time = getTime(fileBean);
        compareTime = getTime(other);
        int flag = compareTime.compareTo(time);
        if (flag == 0) {
            // 如果同出一个时间 按照title排序
            return other.getTitle().compareTo(fileBean.getTitle());
        } else {
            return flag;
        }
    }

    private long getTime(FileBean fileBean) {
        if (fileBean instanceof PhotoAndVideoBean) {
            return ((PhotoAndVideoBean) fileBean).getDateTaken();
        } else {
            return fileBean.getDateModified();
        }
    }
}
