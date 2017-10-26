package com.neurotech.photobrowser.mvp.model.impl;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BaseModel;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.model.response.UserResponse;
import com.neurotech.photobrowser.mvp.model.ITestModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class TestModelImpl extends BaseModel implements ITestModel {
    @Override
    public Observable<Response<BaseResponse<UserResponse>>> getUser(int id) {
        return mServerApi.getUser(id);
    }
}
