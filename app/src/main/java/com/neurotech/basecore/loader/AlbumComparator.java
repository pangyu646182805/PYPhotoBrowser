package com.neurotech.basecore.loader;

import com.neurotech.basecore.bean.AlbumBean;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/10/22.
 */

public class AlbumComparator implements Comparator<AlbumBean> {
    @Override
    public int compare(AlbumBean albumBean, AlbumBean other) {
        int flag = other.getDateTaken().compareTo(albumBean.getDateTaken());
        if (flag == 0) {
            // 如果同出一个时间 按照title排序
            return other.getTitle().compareTo(albumBean.getTitle());
        } else {
            return flag;
        }
    }
}
