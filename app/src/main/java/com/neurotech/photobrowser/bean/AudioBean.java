package com.neurotech.photobrowser.bean;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class AudioBean extends FileBean {
    private int albumId;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "albumId --> " + albumId;
    }
}
