package com.neurotech.photobrowser.mvp.model;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.model.response.UserResponse;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public interface ITestModel {
    Observable<Response<BaseResponse<UserResponse>>> getUser(int id);
}
