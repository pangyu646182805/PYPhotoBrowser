package com.neurotech.basecore.ui.module.photo;

import com.neurotech.basecore.R;
import com.neurotech.basecore.base.SupportActivity;
import com.neurotech.basecore.ui.module.test.TestFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by NeuroAndroid on 2017/10/20.
 */

public class MainActivity extends SupportActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        if (findFragment(TestFragment.class) == null) {
            loadRootFragment(R.id.fl_container, PhotoBrowserFragment.newInstance());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
