package com.example.waz.Base;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.waz.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends RxFragment {
    private BaseActivity mBaseActivity;
    private String mTag;

    public int enter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.page_scare_with_alpha;
    }

    public int exit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.page_romate_with_translation;
    }

    public boolean isNeedAnimation() {
        return true;
    }

    protected boolean isNeedToAddBackStack() {
        return true;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }


    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        if (mBaseActivity != null) {
            mBaseActivity.addFragment(manager, aClass, containerId, args);
        }
    }


    protected String getTAG(){

        if(TextUtils.isEmpty(mTag)){
            mTag = getClass().getSimpleName();
        }

        return mTag;
    }

}
