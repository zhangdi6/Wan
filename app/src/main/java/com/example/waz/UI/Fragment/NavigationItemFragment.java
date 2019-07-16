package com.example.waz.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Base.BaseFragment;
import com.example.waz.Mvp.Contract.NavigationArticleConstrat;
import com.example.waz.Mvp.Presenter.MyNavigationArticlePresenter;
import com.example.waz.R;
import com.example.waz.UI.Activity.WebActivity;
import com.example.waz.UI.Adapter.NavigationAdapter;
import com.example.waz.UI.Bean.NavigationBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationItemFragment extends BaseFragment implements NavigationArticleConstrat.NavigationArticleView {


    public static RecyclerView mRlv;
    private NavigationArticleConstrat.NavigationArticlePresenter mPresenter;
    private int cid;
    private NavigationAdapter adapter;
    private static final String TAG = "NavigationItemFragment";
    private List<NavigationBean.DataBean.ArticlesBean> list;

    public NavigationItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.navigation_frag, container, false);
        setPresenter(new MyNavigationArticlePresenter());
        Bundle arguments = getArguments();
         cid = arguments.getInt("cid");
        mPresenter.setNavigationArticle();
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mRlv = inflate.findViewById(R.id.rlv);
        mRlv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new NavigationAdapter(getContext());
        mRlv.setAdapter(adapter);
        adapter.onClick(new NavigationAdapter.onClick() {
            @Override
            public void onClick(int i) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("title",list.get(i).getTitle());
                intent.putExtra("url",list.get(i).getLink());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccess(NavigationBean datas) {
        if (datas.getData().get(cid).getArticles() != null && datas.getData().get(cid).getArticles().size() > 0) {
            list = datas.getData().get(cid).getArticles();
            adapter.add(datas.getData().get(cid).getArticles());
        }
    }

    @Override
    public void onFail(String msg) {
        Log.d(TAG, "onFail: ++++++++++++"+msg);
    }

    @Override
    public void setPresenter(NavigationArticleConstrat.NavigationArticlePresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }
}
