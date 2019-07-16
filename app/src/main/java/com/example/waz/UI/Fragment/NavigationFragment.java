package com.example.waz.UI.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.waz.Base.BaseFragment;
import com.example.waz.Mvp.Contract.NavigationConstrat;
import com.example.waz.Mvp.Presenter.MyNavigationPresenter;
import com.example.waz.R;
import com.example.waz.UI.Adapter.VpNavigationAdapter;
import com.example.waz.UI.Bean.NavigationBean;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BaseFragment implements NavigationConstrat.NavigationView {

    private static final String TAG = "NavigationFragment";
    private VerticalTabLayout mTab;
    private ViewPager mVp;
    private NavigationConstrat.NavigationPresenter mPresenter;
    private ArrayList<String> mTitle=new ArrayList<>();
    private ArrayList<NavigationItemFragment> mList=new ArrayList<>();
    private VpNavigationAdapter adapter;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_navigation, container, false);
        setPresenter(new MyNavigationPresenter());
        mPresenter.setNavigation();
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mTab = inflate.findViewById(R.id.tab);
        mVp = inflate.findViewById(R.id.vp);

    }


    @Override
    public void onSuccess(NavigationBean datas) {
        Log.d(TAG, "onSuccess: -----------------"+datas.getData().toString()+"datas");
        if (datas.getData() != null && datas.getData().size() > 0) {

            for (int i = 0; i <datas.getData().size() ; i++) {
                String name = datas.getData().get(i).getName();
                mTitle.add(name);
                NavigationItemFragment fragment = new NavigationItemFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("cid",i);
                fragment.setArguments(bundle);
                mList.add(fragment);
            }
            adapter = new VpNavigationAdapter(getChildFragmentManager(), mTitle, mList);
            mVp.setAdapter(adapter);
            mTab.setupWithViewPager(mVp);
        }
    }

    @Override
    public void onFail(String msg) {
        Log.d(TAG, "onFail: ++++++++++++++++" + msg);
    }

    @Override
    public void setPresenter(NavigationConstrat.NavigationPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }
}
