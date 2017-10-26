package com.neurotech.photobrowser.mvp.presenter;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.base.BasePresenter;
import com.neurotech.photobrowser.base.BaseResponse;
import com.neurotech.photobrowser.base.BaseResponseObserver;
import com.neurotech.photobrowser.model.response.UserResponse;
import com.neurotech.photobrowser.mvp.contract.ITestContract;
import com.neurotech.photobrowser.mvp.model.impl.TestModelImpl;
import com.neurotech.photobrowser.net.ModelFilteredFactory;
import com.neurotech.photobrowser.utils.RxLifecycleUtils;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public class TestPresenter extends BasePresenter<TestModelImpl, ITestContract.View>
        implements ITestContract.Presenter {
    public TestPresenter(ITestContract.View view) {
        super(view);
        mModel = new TestModelImpl();
        mView.setPresenter(this);
    }

    @Override
    public void getUser(int id) {
        mModel.setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        mModel.setCacheKey("test");
        mModel.setCacheTime(300000);
        new ModelFilteredFactory<Response<BaseResponse<UserResponse>>>().compose(mModel.getUser(id))
                .compose(RxLifecycleUtils.bindToLifecycle(mView))
                .subscribe(new BaseResponseObserver<BaseResponse<UserResponse>>() {
                    @Override
                    protected void onHandleResponse(Response<BaseResponse<UserResponse>> response) {
                        mView.showResponse(response);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
