package com.example.waz.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.waz.R;
import com.example.waz.Weight.LoadingPage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

public abstract class BaseActivity extends RxAppCompatActivity {
    private LoadingPage mLoadingPage;

    protected void addFragment(FragmentManager manager,
      Class<? extends BaseFragment> aClass, int containerId, Bundle arg) {
        String tag = aClass.getName();
        /**
         * 先查询fragmentManager 所在的activitiy 中是否已经添加了这个fragment
         *  第一步 先从一个mAdded 的一个ArrayList遍历查找，如果找不到再从 一个 叫 mActive 的 SparseArray的一个map
         *  里面查找。
         *
         *
         * 注意：
         * 1.一个 fragment 被 remove 掉后，只会从 mAdded 里面删除，不会从 mActive 里面删除，只有当这个fragment 所在的 transaction
         * 从回退栈里面移除后才会 从mActive 删除
         * 2. 当我们add 一个fragment时 会把我们的fragment 添加到 mAdded 里面，不会添加到 mActive。
         * 3. 只有当我们把 transaction 添加到回退栈的时候，才会把我们的 fragment 添加到 mActive 里面。
         *
         *
         * 所以我们通过 findFragmentByTag 方法查找出来的 fragment 不一定是被添加到我们的 activity 中。
         */
        Fragment fragment = manager.findFragmentByTag(tag);
        //开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment == null) {
            try {
                // 通过反射 new 出一个 fragment 的实例
                fragment = aClass.newInstance();
                //强转成BaseFragment
                BaseFragment baseFragment = (BaseFragment) fragment;
                transaction.setCustomAnimations(baseFragment.enter(),baseFragment.exit());
                transaction.add(containerId, fragment);
                if (baseFragment.isNeedToAddBackStack()) {
                   /* transaction.addToBackStack(tag);*/
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment);
            }
        }
        if (fragment != null) {
            fragment.setArguments(arg);
            hideBoforeFragment(manager, transaction, fragment);
            transaction.commit();
        }
    }
//除当前 fragment 以外的所有 fragment 进行隐藏
    protected void hideBoforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentfragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != currentfragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }

    protected void showLoadingPage(int type) {
        showLoadingPage(android.R.id.content, type);
    }

    protected void showLoadingPage(int containId, int type) {
        showLoadingPage((ViewGroup) findViewById(containId), type);
    }

    protected void showLoadingPage(ViewGroup group, int type) {
        if (mLoadingPage == null) {
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.loadingpage, null, false);
        }
        group.addView(mLoadingPage);
    }

    protected void dismiss() {
        ViewParent parent = mLoadingPage.getParent();
        if (parent != null) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.removeView(mLoadingPage);
        }
    }

    protected void onError(LoadingPage.onReLoadCallBack callBack, String res) {
        if (mLoadingPage != null) {
            mLoadingPage.onError(callBack, res);
        }
    }

    protected void onError() {
        if (mLoadingPage != null) {
            mLoadingPage.onError();
        }
    }

    protected void showToast(String res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }
}

































