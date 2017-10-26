package com.neurotech.photobrowser.module.test;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.base.SupportActivity;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class TestActivity extends SupportActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        if (findFragment(TestFragment.class) == null) {
            loadRootFragment(R.id.fl_container, TestFragment.newInstance());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
