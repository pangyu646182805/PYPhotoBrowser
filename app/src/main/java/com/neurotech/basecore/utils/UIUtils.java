package com.neurotech.basecore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurotech.basecore.base.BaseApplication;

/**
 * Created by NeuroAndroid on 2017/2/8.
 */

public class UIUtils {
    public static Context getContext() {
        return BaseApplication.getAppContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static void toLayout(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    /**
     * 得到String.xml中的字符串,带占位符
     */
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    public static float getDimen(int dimen) {
        return getResources().getDimension(dimen);
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 得到String.xml中的字符串数组
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * 得到应用程序的包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static String getEditTextStr(EditText et) {
        return et.getText().toString();
    }

    public static String getTextViewStr(TextView tv) {
        return tv.getText().toString();
    }

    /**
     * @param tv
     * @param str      要设置的文本
     * @param emptyStr 要设置的文本为空时需要设置的文本
     */
    public static void setText(TextView tv, String str, String emptyStr) {
        tv.setText(isEmpty(str) ? emptyStr : str);
    }

    public static void setImage(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }

    /**
     * 获取文本边框
     *
     * @param paint
     * @param text
     * @param rect
     */
    public static void getTextBounds(Paint paint, String text, Rect rect) {
        paint.getTextBounds(text, 0, text.length(), rect);
    }

    public static float getRawSize(Context context, int unit, float size) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
    }

    /**
     * @param enable true : 全屏
     */
    public static void fullScreen(Activity activity, boolean enable) {
        Window window = activity.getWindow();
        if (enable) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(lp);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = window.getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(attr);
        }
    }

    /**
     * 设置当前界面的屏幕亮度
     *
     * @param brightness 0-255
     */
    public static void setScreenBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获得当前屏幕亮度值 0--255
     */
    public static int getScreenBrightness(Context context) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception localException) {

        }
        return screenBrightness;
    }

    public static Bitmap zoomImage(Bitmap bm, float newWidth, float newHeight) {
        // 获取这个图片的宽和高
        float width = bm.getWidth();
        float height = bm.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static int getScrollYDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        if (position == 0) {
            View firstVisibleChildView = layoutManager.findViewByPosition(position);
            int itemHeight = firstVisibleChildView.getHeight();
            return (position) * itemHeight - firstVisibleChildView.getTop();
        }
        return -1;
    }

    public static int getScrollXDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        if (position == 0) {
            View firstVisibleChildView = layoutManager.findViewByPosition(position);
            int itemWidth = firstVisibleChildView.getWidth();
            return (position) * itemWidth - firstVisibleChildView.getLeft();
        }
        return -1;
    }
}
