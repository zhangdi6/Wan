package com.example.waz.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Api.Utils;
import com.example.waz.Base.BaseFragment;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.Mvp.Contract.WanConstrat;
import com.example.waz.Mvp.Presenter.MyWanPresenter;
import com.example.waz.R;
import com.example.waz.UI.Activity.LoginActivity;
import com.example.waz.UI.Activity.MainActivity;
import com.example.waz.UI.Activity.WebActivity;
import com.example.waz.UI.Adapter.WanAdapter;
import com.example.waz.UI.Bean.MyBean;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */

public class WanFragment extends BaseFragment implements WanConstrat.WanView {

    public static RecyclerView mRlvWan;
    private SmartRefreshLayout mSmart;
    private WanConstrat.WanPresenter mPresenter;
    private static final String TAG = "WanFragment";
    public static WanAdapter adapter;
    private ArrayList<WanBannerBean.DataBean> mBanner = new ArrayList<>();

    private int page = 0;
    private MyBean bean;

    public WanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_wan, container, false);
        initData();
        initView(inflate);
        return inflate;
    }

    public void initData() {
        setPresenter(new MyWanPresenter());
        mPresenter.setArticle(page);
        mPresenter.setBanner();
    }

    private void initView(View inflate) {
        mRlvWan = inflate.findViewById(R.id.rlv_wan);
        mSmart = inflate.findViewById(R.id.smart);
        mRlvWan.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlvWan.addItemDecoration(new DividerItemDecoration(getContext()
                , DividerItemDecoration.VERTICAL));
        adapter = new WanAdapter(getContext());

        mRlvWan.setAdapter(adapter);
        mSmart.setRefreshHeader(new StoreHouseHeader(getContext()));
        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                adapter.mList.clear();
                mPresenter.setArticle(page);
                adapter.notifyDataSetChanged();
                mSmart.finishRefresh();
            }
        });
        mSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.setArticle(page);
                mSmart.finishLoadMore();
            }
        });
        adapter.onClick(new WanAdapter.onClick() {
            @Override
            public void onClick(int i) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("title", adapter.mList.get(i).getTitle());
                intent.putExtra("url", adapter.mList.get(i).getLink());
                getActivity().startActivity(intent);
            }
        });
        adapter.onCheckClick(new WanAdapter.onCheckClick() {
            @Override
            public void onCheckClick(int i) {
                if (!TextUtils.isEmpty(MainActivity.name2.getText())) {
                    WanArticleBean.DataBean.DatasBean datasBean = adapter.mList.get(i);
                    boolean isChecked = datasBean.getIsChecked();
                    bean = new MyBean();
                    if (isChecked) {
                        bean.setIsChecked(datasBean.getIsChecked());
                        bean.setTitle(datasBean.getTitle());
                        bean.setSuperName(datasBean.getSuperChapterName());
                        bean.setNiceDate(datasBean.getNiceDate());
                        bean.setImg(datasBean.getEnvelopePic());
                        bean.setDesc(datasBean.getDesc());
                        bean.setAuthorName(datasBean.getAuthor());
                        bean.setLink(datasBean.getLink());
                        long insert = Utils.insert(bean);
                        if (insert > 0) {
                            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent,11);
                }
            }
        });
    }

    @Override
    public void setPresenter(WanConstrat.WanPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }

    @Override
    public void onSuccessBanner(WanBannerBean datas) {
        Log.d(TAG, "onSuccessBanner: " + datas.getData().toString());
        if (datas != null) {
            ArrayList<WanBannerBean.DataBean> data = (ArrayList<WanBannerBean.DataBean>) datas.getData();
            mBanner = data;
            adapter.addBanner(mBanner);
        }

    }

    @Override
    public void onFailBanner(String msg) {
        Log.d(TAG, "onFailBanner: " + "-----------------" + msg);
    }

    @Override
    public void onSuccessArticle(WanArticleBean datas) {
        if (datas != null) {
            WanArticleBean.DataBean data = datas.getData();
            ArrayList<WanArticleBean.DataBean.DatasBean> datas1 = (ArrayList<WanArticleBean.DataBean.DatasBean>) data.getDatas();
            for (int i = 0; i < datas1.size(); i++) {
                WanArticleBean.DataBean.DatasBean datasBean = datas1.get(i);
                MyBean bean2 = new MyBean();
                bean2.setIsChecked(datasBean.getIsChecked());
                bean2.setTitle(datasBean.getTitle());
                bean2.setSuperName(datasBean.getSuperChapterName());
                bean2.setNiceDate(datasBean.getNiceDate());
                bean2.setImg(datasBean.getEnvelopePic());
                bean2.setDesc(datasBean.getDesc());
                bean2.setAuthorName(datasBean.getAuthor());
                bean2.setLink(datasBean.getLink());
                boolean exists = Utils.isExists(bean2);
                if (exists){
                    datas1.get(i).setChecked(true);
                }else{
                    datas1.get(i).setChecked(false);
                }
            }
            adapter.addArticle(datas1);
        }
    }

    @Override
    public void onFail(String msg) {
        Log.d(TAG, "onFail: " + "---------" + msg);
    }
}
