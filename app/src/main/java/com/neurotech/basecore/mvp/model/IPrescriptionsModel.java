package com.neurotech.basecore.mvp.model;

import com.lzy.okgo.model.Response;
import com.neurotech.basecore.base.BaseResponse;
import com.neurotech.basecore.model.response.PrescriptionsResponse;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public interface IPrescriptionsModel {
    Observable<Response<BaseResponse<PrescriptionsResponse>>> getPatientPrescriptionList(Integer patientId,
                                                                                         Integer page,
                                                                                         Integer pageSize);
}
