package com.neurotech.photobrowser.mvp.model;

import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.model.response.PrescriptionsResponse;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public interface IPrescriptionsModel {
    Observable<Response<BaseResponse<PrescriptionsResponse>>> getPatientPrescriptionList(Integer patientId,
                                                                                         Integer page,
                                                                                         Integer pageSize);
}
