package com.neurotech.basecore.base;

import com.lzy.okgo.cache.CacheMode;
import com.neurotech.basecore.model.api.ServerApi;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class BaseModel implements IModel {
    protected ServerApi mServerApi = ServerApi.getInstance();

    /**
     * 设置缓存模式
     * {{@link CacheMode}}
     */
    public void setCacheMode(CacheMode cacheMode) {
        mServerApi.setCacheMode(cacheMode);
    }

    /**
     * 设置缓存的key
     */
    public void setCacheKey(String cacheKey) {
        mServerApi.setCacheKey(cacheKey);
    }

    /**
     * 设置缓存时间
     */
    public void setCacheTime(long cacheTime) {
        mServerApi.setCacheTime(cacheTime);
    }

    @Override
    public void onDestroy() {

    }
}
