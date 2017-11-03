package com.neurotech.photobrowser.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neurotech.photobrowser.bean.PhotoAndVideoBean;
import com.neurotech.photobrowser.utils.MimeType;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/5/25.
 */

public class VideoLoader {
    @NonNull
    public static ArrayList<PhotoAndVideoBean> getAllVideos(@NonNull Context context) {
        Cursor cursor = makePhotoCursor(context, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        return getVideos(cursor);
    }

    @NonNull
    private static ArrayList<PhotoAndVideoBean> getVideos(@Nullable final Cursor cursor) {
        ArrayList<PhotoAndVideoBean> songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getVideoFromCursorImpl(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();
        return songs;
    }

    private static PhotoAndVideoBean getVideoFromCursorImpl(Cursor cursor) {
        PhotoAndVideoBean videoBean = new PhotoAndVideoBean();
        videoBean.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        videoBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)));
        videoBean.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)));
        videoBean.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE)));
        videoBean.setMediaMimeType(MimeType.VIDEO);
        videoBean.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
        videoBean.setWidth(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.WIDTH)));
        videoBean.setHeight(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.HEIGHT)));
        videoBean.setDateTaken(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_TAKEN)));
        videoBean.setDateAdded(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_ADDED)));
        videoBean.setDateModified(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_MODIFIED)));
        return videoBean;
    }

    @Nullable
    private static Cursor makePhotoCursor(@NonNull final Context context, @Nullable final String selection, final String[] selectionValues, final String sortOrder) {
        try {
            return context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            BaseColumns._ID,
                            MediaStore.Video.VideoColumns.TITLE,
                            MediaStore.Video.VideoColumns.DATA,
                            MediaStore.Video.VideoColumns.DATE_TAKEN,
                            MediaStore.Video.VideoColumns.DATE_ADDED,
                            MediaStore.Video.VideoColumns.DATE_MODIFIED,
                            MediaStore.Video.VideoColumns.SIZE,
                            MediaStore.Video.VideoColumns.MIME_TYPE,
                            MediaStore.Video.VideoColumns.WIDTH,
                            MediaStore.Video.VideoColumns.HEIGHT
                    }, selection, selectionValues, sortOrder);
        } catch (SecurityException e) {
            return null;
        }
    }
}
