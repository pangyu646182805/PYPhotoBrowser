package com.neurotech.basecore.model.api;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.neurotech.basecore.base.BaseResponse;
import com.neurotech.basecore.config.Constants;
import com.neurotech.basecore.model.response.PrescriptionsResponse;
import com.neurotech.basecore.model.response.UserResponse;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class ServerApi {
    private static ServerApi sInstance;

    public static ServerApi getInstance() {
        if (sInstance == null) {
            synchronized (ServerApi.class) {
                if (sInstance == null) {
                    sInstance = new ServerApi();
                }
            }
        }
        return sInstance;
    }

    /**
     * 默认的缓存模式 不需要缓存
     * NO_CACHE cacheTime和cacheKey均失效
     */
    private CacheMode mCacheMode = CacheMode.NO_CACHE;

    /**
     * 默认为null
     * 如果CacheMode为NO_CACHE则必须指定cacheKey
     */
    private String mCacheKey = "";

    /**
     * 默认为一小时 ms为单位
     * 表示永不过期 在NO_CACHE模式下面无效
     */
    private long mCacheTime = 1000 * 60 * 60;

    /**
     * 设置缓存模式
     * {{@link CacheMode}}
     */
    public void setCacheMode(CacheMode cacheMode) {
        mCacheMode = cacheMode;
    }

    /**
     * 设置缓存的key
     */
    public void setCacheKey(String cacheKey) {
        mCacheKey = cacheKey;
    }

    /**
     * 设置缓存时间
     */
    public void setCacheTime(long cacheTime) {
        mCacheTime = cacheTime;
    }

    public Observable<Response<BaseResponse<UserResponse>>> getUser(int id) {
        Type type = new TypeToken<BaseResponse<UserResponse>>() {
        }.getType();
        return OkGo.<BaseResponse<UserResponse>>get(Constants.BASE_URL + "patients/" + id)
                .converter(new JsonConvert<>(type))
                .cacheMode(mCacheMode)
                .cacheTime(mCacheTime)
                .cacheKey(mCacheKey)
                .adapt(new ObservableResponse<>());
    }

    /**
     * 患者分页获取药单列表
     */
    public Observable<Response<BaseResponse<PrescriptionsResponse>>> getPatientPrescriptionList(Integer patientId,
                                                                                                Integer page,
                                                                                                Integer pageSize) {
        Type type = new TypeToken<BaseResponse<PrescriptionsResponse>>() {
        }.getType();
        return OkGo.<BaseResponse<PrescriptionsResponse>>get(Constants.BASE_URL + "prescriptions/patient/list")
                .params("patientId", patientId)
                .params("page", page)
                .params("pageSize", pageSize)
                .converter(new JsonConvert<>(type))
                .cacheMode(mCacheMode)
                .cacheTime(mCacheTime)
                .cacheKey(mCacheKey)
                .adapt(new ObservableResponse<>());
    }
}
