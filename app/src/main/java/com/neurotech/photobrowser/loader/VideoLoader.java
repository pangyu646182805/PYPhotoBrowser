package com.neurotech.photobrowser.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neurotech.photobrowser.bean.AlbumBean;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/5/25.
 */

public class VideoLoader {
    @NonNull
    public static ArrayList<AlbumBean> getAllVideos(@NonNull Context context) {
        Cursor cursor = makePhotoCursor(context, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        return getVideos(cursor);
    }

    @NonNull
    private static ArrayList<AlbumBean> getVideos(@Nullable final Cursor cursor) {
        ArrayList<AlbumBean> songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getVideoFromCursorImpl(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();
        return songs;
    }

    private static AlbumBean getVideoFromCursorImpl(Cursor cursor) {
        AlbumBean albumBean = new AlbumBean();
        albumBean.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        albumBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)));
        albumBean.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)));
        albumBean.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE)));
        albumBean.setClassifyFlag(AlbumBean.TYPE_VIDEO);
        albumBean.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
        albumBean.setWidth(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.WIDTH)));
        albumBean.setHeight(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.HEIGHT)));
        albumBean.setDateTaken(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_TAKEN)));
        albumBean.setDateAdded(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_ADDED)));
        albumBean.setDateModified(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_MODIFIED)));
        return albumBean;
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
