package com.example.waz.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Base.BaseFragment;
import com.example.waz.Mvp.Contract.SecondConstrat;
import com.example.waz.Mvp.Presenter.MySecondPresenter;
import com.example.waz.R;
import com.example.waz.UI.Activity.KnowledgeActivity;
import com.example.waz.UI.Adapter.KnowAdapter;
import com.example.waz.UI.Bean.SecondBean;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeFragment extends BaseFragment implements SecondConstrat.SecondView {

    private static final String TAG = "KnowledgeFragment";
    public static RecyclerView mRlv;
    private SecondConstrat.SecondPresenter mPresenter;
    private KnowAdapter adapter;
    private ArrayList<SecondBean.DataBean> mList=new ArrayList<>();

    public KnowledgeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_knowledge, container, false);
        setPresenter(new MySecondPresenter());
        mPresenter.setSecond();
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mRlv = inflate.findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
         adapter = new KnowAdapter(getContext(), mList);
         mRlv.setAdapter(adapter);
         adapter.onClick(new KnowAdapter.onClick() {
             @Override
             public void onClick(int i) {
                 Intent intent = new Intent(getContext(), KnowledgeActivity.class);
                 SecondBean.DataBean dataBean = mList.get(i);
                 intent.putExtra("bean",dataBean);
                 startActivity(intent);
             }
         });
    }

    @Override
    public void onSuccess(SecondBean datas) {
        if (datas.getData() != null && datas.getData().size() > 0) {
            List<SecondBean.DataBean> data = datas.getData();
           adapter.add(data);
        }
    }

    @Override
    public void onFailSecond(String msg) {
        Log.d(TAG, "onFailSecond:====================" + msg);
    }

    @Override
    public void setPresenter(SecondConstrat.SecondPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }
}
