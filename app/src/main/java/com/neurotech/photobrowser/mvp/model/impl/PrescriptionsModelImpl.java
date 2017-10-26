package com.neurotech.photobrowser.mvp.model.impl;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BaseModel;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.model.response.PrescriptionsResponse;
import com.neurotech.photobrowser.mvp.model.IPrescriptionsModel;

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
