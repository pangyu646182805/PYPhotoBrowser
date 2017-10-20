package com.neurotech.basecore.ui.module.test;

import com.neurotech.basecore.R;
import com.neurotech.basecore.base.SupportFragment;
import com.neurotech.basecore.bean.TabEntity;
import com.neurotech.tablayout.CommonTabLayout;
import com.neurotech.tablayout.listener.CustomTabEntity;
import com.neurotech.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/10/19.
 */

public class TestFragment extends SupportFragment {
    @BindView(R.id.tabs)
    CommonTabLayout mTabs;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private ArrayList<CustomTabEntity> mTabEntities;

    private String[] mTitles = {"药单管理", "我的脑电", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    private int mPreSelectedPosition;

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView() {
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconUnselectIds[i], mIconUnselectIds[i]));
        }
        mTabs.setTabData(mTabEntities);
    }

    @Override
    protected void initData() {
        SupportFragment firstFragment = (SupportFragment) findChildFragment(PrescriptionsFragment.class);
        if (firstFragment == null) {
            mFragments[0] = PrescriptionsFragment.newInstance();
            mFragments[1] = MeFragment.newInstance();
            mFragments[2] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = firstFragment;
            mFragments[1] = (SupportFragment) findChildFragment(MeFragment.class);
            mFragments[2] = (SupportFragment) findChildFragment(MeFragment.class);
        }
    }

    @Override
    protected void initListener() {
        mTabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                // showHideFragment(mFragments[position], mFragments[mPreSelectedPosition]);
                showHideFragment(mFragments[position]);
                mPreSelectedPosition = position;
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
}
