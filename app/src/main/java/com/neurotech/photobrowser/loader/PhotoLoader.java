package com.neurotech.photobrowser.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neurotech.photobrowser.bean.AlbumBean;
import com.neurotech.photobrowser.bean.AlbumCategoryBean;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/5/25.
 */

public class PhotoLoader {
    private static ArrayList<AlbumCategoryBean> sAlbumCategoryList = new ArrayList<>();

    @NonNull
    public static ArrayList<AlbumBean> getAllPhotos(@NonNull Context context) {
        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=?";
        String[] selectionValues = new String[]{
                "image/jpeg", "image/png", "image/jpg", "image/gif",
                "image/x-ms-bmp", "image/webp"};
        Cursor cursor = makePhotoCursor(context, selection, selectionValues, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        return getPhotos(cursor);
    }

    @NonNull
    private static ArrayList<AlbumBean> getPhotos(@Nullable final Cursor cursor) {
        ArrayList<AlbumBean> songs = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getPhotoFromCursorImpl(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();
        return songs;
    }

    private static AlbumBean getPhotoFromCursorImpl(Cursor cursor) {
        AlbumBean albumBean = new AlbumBean();
        albumBean.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        albumBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.TITLE)));
        albumBean.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
        albumBean.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)));
        albumBean.setClassifyFlag(AlbumBean.TYPE_PHOTO);
        albumBean.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE)));
        albumBean.setWidth(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)));
        albumBean.setHeight(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT)));
        albumBean.setDateTaken(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)));
        albumBean.setDateAdded(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)) * 1000);
        albumBean.setDateModified(cursor.getLong(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)) * 1000);
        return albumBean;
    }

    @Nullable
    private static Cursor makePhotoCursor(@NonNull final Context context, @Nullable final String selection, final String[] selectionValues, final String sortOrder) {
        try {
            return context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            BaseColumns._ID,
                            MediaStore.Images.ImageColumns.TITLE,
                            MediaStore.Images.ImageColumns.DATA,
                            MediaStore.Images.ImageColumns.DATE_TAKEN,
                            MediaStore.Images.ImageColumns.DATE_ADDED,
                            MediaStore.Images.ImageColumns.DATE_MODIFIED,
                            MediaStore.Images.ImageColumns.SIZE,
                            MediaStore.Images.ImageColumns.MIME_TYPE,
                            MediaStore.Images.ImageColumns.WIDTH,
                            MediaStore.Images.ImageColumns.HEIGHT
                    }, selection, selectionValues, sortOrder);
        } catch (SecurityException e) {
            return null;
        }
    }
}
