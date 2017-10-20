package com.neurotech.basecore.mvp.contract;

import com.lzy.okgo.model.Response;
import com.neurotech.basecore.base.BaseResponse;
import com.neurotech.basecore.base.IPresenter;
import com.neurotech.basecore.base.IView;
import com.neurotech.basecore.model.response.PrescriptionsResponse;

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
