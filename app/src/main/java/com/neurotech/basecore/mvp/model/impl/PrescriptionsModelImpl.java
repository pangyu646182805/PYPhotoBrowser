package com.neurotech.basecore.mvp.model.impl;

import com.lzy.okgo.model.Response;
import com.neurotech.basecore.base.BaseModel;
import com.neurotech.basecore.base.BaseResponse;
import com.neurotech.basecore.model.response.PrescriptionsResponse;
import com.neurotech.basecore.mvp.model.IPrescriptionsModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class PrescriptionsModelImpl extends BaseModel implements IPrescriptionsModel {
    @Override
    public Observable<Response<BaseResponse<PrescriptionsResponse>>> getPatientPrescriptionList(Integer patientId, Integer page, Integer pageSize) {
        return mServerApi.getPatientPrescriptionList(patientId, page, pageSize);
    }
}
