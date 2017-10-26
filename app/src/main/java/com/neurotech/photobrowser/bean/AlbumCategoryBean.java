package com.neurotech.photobrowser.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/22.
 * 将AlbumBean按照时间进行分类
 */
public class AlbumCategoryBean {
    private Long categoryTime;  // 所属的分类时间
    private List<AlbumBean> albumList;  // 该时间分类下面的album

    public Long getCategoryTime() {
        return categoryTime;
    }

    public void setCategoryTime(Long categoryTime) {
        this.categoryTime = categoryTime;
    }

    public List<AlbumBean> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<AlbumBean> albumList) {
        this.albumList = albumList;
    }
}
