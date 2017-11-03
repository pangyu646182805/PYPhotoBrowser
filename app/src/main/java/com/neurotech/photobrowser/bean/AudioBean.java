package com.neurotech.photobrowser.bean;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class AudioBean extends FileBean {
    private int albumId;
    private long duration;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "albumId --> " + albumId + "\n" +
                "duration --> " + duration;
    }
}
