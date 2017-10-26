package com.neurotech.photobrowser.mvp.contract;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.base.IPresenter;
import com.neurotech.photobrowser.base.IView;
import com.neurotech.photobrowser.model.response.PrescriptionsResponse;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public interface IPrescriptionsContract {
    interface Presenter extends IPresenter {
        void getPrescriptionList(Integer patientId,
                                 Integer page,
                                 Integer pageSize);

        void getLoadMorePrescriptionList(Integer patientId,
                                         Integer page,
                                         Integer pageSize);
    }

    interface View extends IView<Presenter> {
        void showResponse(Response<BaseResponse<PrescriptionsResponse>> response);

        void showLoadMoreResponse(Response<BaseResponse<PrescriptionsResponse>> response);
    }
}
