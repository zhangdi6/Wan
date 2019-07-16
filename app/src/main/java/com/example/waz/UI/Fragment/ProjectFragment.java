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
import com.example.waz.Mvp.Contract.ProjectConstrat;
import com.example.waz.Mvp.Presenter.MyNavigationPresenter;
import com.example.waz.Mvp.Presenter.MyProjectPresenter;
import com.example.waz.R;
import com.example.waz.UI.Adapter.VpNavigationAdapter;
import com.example.waz.UI.Adapter.VpProjectAapter;
import com.example.waz.UI.Bean.ProjectBean;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment implements ProjectConstrat.ProjectView {

    private static final String TAG = "ProjectFragment";
    private TabLayout mTab;
    private ViewPager mVp;
    private ProjectConstrat.ProjectPresenter mPresenter;
    private ArrayList<String> mTitle=new ArrayList<>();
    private ArrayList<ProjectArticleFragment> mList=new ArrayList<>();
    private VpProjectAapter adapter;

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_project, container, false);
        setPresenter(new MyProjectPresenter());
        mPresenter.setProject();
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mTab = inflate.findViewById(R.id.tab);
        mVp = inflate.findViewById(R.id.vp);

    }

    @Override
    public void onSuccess(ProjectBean datas) {
        Log.d(TAG, "onSuccess: -----------------" + datas.getData().toString() + "datas");
        List<ProjectBean.DataBean> data = datas.getData();
        if (datas.getData() != null && datas.getData().size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                String name = data.get(i).getName();
                mTitle.add(name);
                ProjectArticleFragment fragment = new ProjectArticleFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("cid", data.get(i).getId());
                fragment.setArguments(bundle);
                mList.add(fragment);
            }
            adapter = new VpProjectAapter(getChildFragmentManager(), mTitle, mList);
            mVp.setAdapter(adapter);
            mTab.setupWithViewPager(mVp);
        }
    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void setPresenter(ProjectConstrat.ProjectPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }
}
