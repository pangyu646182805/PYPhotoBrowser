package com.neurotech.photobrowser.module.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.base.SupportFragment;
import com.neurotech.photobrowser.utils.L;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class MeFragment extends SupportFragment {
    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        L.e("MeFragment onLazyInitView");
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        L.e("MeFragment onSupportVisible");
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        L.e("MeFragment onSupportInvisible");
    }
}
