package com.neurotech.photobrowser.mvp.contract;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.base.IPresenter;
import com.neurotech.photobrowser.base.IView;
import com.neurotech.photobrowser.model.response.UserResponse;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public interface ITestContract {
    interface Presenter extends IPresenter {
        void getUser(int id);
    }

    interface View extends IView<Presenter> {
        void showResponse(Response<BaseResponse<UserResponse>> response);
    }
}
