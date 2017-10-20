package com.neurotech.basecore.mvp.contract;

import com.lzy.okgo.model.Response;
import com.neurotech.basecore.base.BaseResponse;
import com.neurotech.basecore.base.IPresenter;
import com.neurotech.basecore.base.IView;
import com.neurotech.basecore.model.response.UserResponse;

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
