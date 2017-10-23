package com.neurotech.basecore.module.photo;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.neurotech.basecore.R;
import com.neurotech.basecore.base.SupportActivity;
import com.neurotech.basecore.permission.DangerousPermissions;
import com.neurotech.basecore.permission.PermissionsHelper;
import com.neurotech.basecore.module.test.TestFragment;
import com.neurotech.basecore.utils.ToastUtils;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by NeuroAndroid on 2017/10/20.
 */

public class MainActivity extends SupportActivity {
    /**
     * 需要动态申请的权限
     */
    private static final String[] PERMISSIONS = new String[]{
            DangerousPermissions.STORAGE
    };

    private PermissionsHelper mPermissionsHelper;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPermissionsHelper = new PermissionsHelper(this, PERMISSIONS);
        checkPermission();
    }

    private void checkPermission() {
        if (mPermissionsHelper.checkAllPermissions(PERMISSIONS)) {
            initUI();
            mPermissionsHelper.onDestroy();
        } else {
            // 申请权限
            mPermissionsHelper.startRequestNeedPermissions();
        }
        mPermissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {
            @Override
            public void onAllNeedPermissionsGranted() {
                ToastUtils.info("权限申请成功");
                initUI();
            }

            @Override
            public void onPermissionsDenied() {
                ToastUtils.error("权限申请失败");
                finish();
            }
        });
    }

    private void initUI() {
        if (findFragment(TestFragment.class) == null) {
            loadRootFragment(R.id.fl_container, PhotoBrowserFragment.newInstance());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionsHelper.onActivityResult(requestCode, resultCode, data);
    }
}
