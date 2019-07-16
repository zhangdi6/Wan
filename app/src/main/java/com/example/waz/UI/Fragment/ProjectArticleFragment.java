package com.example.waz.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Base.BaseFragment;
import com.example.waz.Mvp.Contract.ProjectArticleConstrat;
import com.example.waz.Mvp.Contract.ProjectConstrat;
import com.example.waz.Mvp.Presenter.MyProjectArticlePresenter;
import com.example.waz.R;
import com.example.waz.UI.Activity.WebActivity;
import com.example.waz.UI.Adapter.ProjectArticleAdapter;
import com.example.waz.UI.Bean.ProjectArticleBean;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectArticleFragment extends BaseFragment implements ProjectArticleConstrat.ProjectArticleView {


    public static RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private int cid;
    private int page = 0;
    private ProjectArticleAdapter adapter;
    private ProjectArticleConstrat.ProjectArticlePresenter mPresenter;
    private static final String TAG = "ProjectArticleFragment";

    public ProjectArticleFragment() {
        Log.d(TAG, "new : ++++++++++++++++++++++" + hashCode());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreateView: ++++++++++++++++++++++走走走走" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_projectarticle, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            cid = arguments.getInt("cid");
        }
        setPresenter(new MyProjectArticlePresenter());
        mPresenter.setProjectArticle(page, cid);

        Log.d(TAG, "onCreateView: ++++++++++++++++++++++" + cid + "---" + hashCode());
        initView(inflate);
        return inflate;
    }


    private void initView(View inflate) {
        mRlv = inflate.findViewById(R.id.rlv);
        mSmart = inflate.findViewById(R.id.smart);
        mSmart.setRefreshHeader(new WaterDropHeader(getContext()));
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.addItemDecoration(new DividerItemDecoration(getContext()
                , DividerItemDecoration.VERTICAL));
        adapter = new ProjectArticleAdapter(getContext());
        mRlv.setAdapter(adapter);
        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                adapter.mList.clear();
                mPresenter.setProjectArticle(page, cid);
                mSmart.finishRefresh();

            }
        });
        mSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.setProjectArticle(page, cid);
                mSmart.finishLoadMore();
            }
        });


        adapter.onClickListener(new ProjectArticleAdapter.onClickListener() {
            @Override
            public void onClickListener(int i) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("title", adapter.mList.get(i).getTitle());
                intent.putExtra("url", adapter.mList.get(i).getLink());
                startActivity(intent);

            }
        });
        /*adapter.onCheck(new ProjectArticleAdapter.onCheck() {
            @Override
            public void onCheck(int i) {
                Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onSuccess(ProjectArticleBean datas) {
        if (datas != null && datas.getData() != null && datas.getData().getDatas()
                != null && datas.getData().getDatas().size() > 0) {
            ArrayList<ProjectArticleBean.DataBean.DatasBean> datas1
                    = (ArrayList<ProjectArticleBean.DataBean.DatasBean>) datas.getData().getDatas();

            adapter.add(datas1);

        }
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ProjectArticleConstrat.ProjectArticlePresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }
}
