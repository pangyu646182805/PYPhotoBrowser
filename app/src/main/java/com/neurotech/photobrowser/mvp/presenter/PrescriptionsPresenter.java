package com.neurotech.photobrowser.mvp.presenter;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BasePresenter;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.base.BaseResponseObserver;
import com.neurotech.photobrowser.model.response.PrescriptionsResponse;
import com.neurotech.photobrowser.mvp.contract.IPrescriptionsContract;
import com.neurotech.photobrowser.mvp.model.impl.PrescriptionsModelImpl;
import com.neurotech.photobrowser.net.ModelFilteredFactory;
import com.neurotech.photobrowser.utils.RxLifecycleUtils;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class PrescriptionsPresenter extends BasePresenter<PrescriptionsModelImpl, IPrescriptionsContract.View>
        implements IPrescriptionsContract.Presenter {
    public PrescriptionsPresenter(IPrescriptionsContract.View view) {
        super(view);
        mModel = new PrescriptionsModelImpl();
        mView.setPresenter(this);
    }

    @Override
    public void getPrescriptionList(Integer patientId, Integer page, Integer pageSize) {
        mModel.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        mModel.setCacheTime(300000);
        mModel.setCacheKey("getPrescriptionList");
        new ModelFilteredFactory<Response<BaseResponse<PrescriptionsResponse>>>()
                .compose(mModel.getPatientPrescriptionList(patientId, page, pageSize))
                .compose(RxLifecycleUtils.bindToLifecycle(mView))
                .subscribe(new BaseResponseObserver<BaseResponse<PrescriptionsResponse>>() {
                    @Override
                    protected void onHandleResponse(Response<BaseResponse<PrescriptionsResponse>> response) {
                        mView.showResponse(response);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void getLoadMorePrescriptionList(Integer patientId, Integer page, Integer pageSize) {
        // 上拉加载不需要缓存
        mModel.setCacheMode(CacheMode.NO_CACHE);
        new ModelFilteredFactory<Response<BaseResponse<PrescriptionsResponse>>>()
                .compose(mModel.getPatientPrescriptionList(patientId, page, pageSize))
                .compose(RxLifecycleUtils.bindToLifecycle(mView))
                .subscribe(new BaseResponseObserver<BaseResponse<PrescriptionsResponse>>() {
                    @Override
                    protected void onHandleResponse(Response<BaseResponse<PrescriptionsResponse>> response) {
                        mView.showLoadMoreResponse(response);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
