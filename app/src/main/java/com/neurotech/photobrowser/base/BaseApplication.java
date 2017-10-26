package com.neurotech.photobrowser.base;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by NeuroAndroid on 2017/10/10.
 */

public class BaseApplication extends Application {
    private static Context sContext;

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initOkGo();
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        // log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        // log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        // 添加OkGo默认debug日志
        builder.addInterceptor(loggingInterceptor);

        // 超时时间设置，默认60秒
        // 全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // 全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // 全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        // 自动管理cookie（或者叫session的保持），以下几种任选其一就行
        // 使用sp保持cookie，如果cookie不过期，则一直有效
        // builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        // 使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        // 使用内存保持cookie，app退出后，cookie消失
        // builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        // 必须调用初始化
        OkGo.getInstance().init(this)
                // 建议设置OkHttpClient，不设置会使用默认的
                .setOkHttpClient(builder.build())
                // 全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheMode(CacheMode.NO_CACHE)
                // 全局统一缓存时间，默认永不过期，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                // 全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .setRetryCount(1);
    }
}
