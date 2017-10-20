package com.neurotech.basecore.mvp.model.impl;

import com.lzy.okgo.model.Response;
import com.neurotech.basecore.base.BaseModel;
import com.neurotech.basecore.base.BaseResponse;
import com.neurotech.basecore.model.response.UserResponse;
import com.neurotech.basecore.mvp.model.ITestModel;

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
