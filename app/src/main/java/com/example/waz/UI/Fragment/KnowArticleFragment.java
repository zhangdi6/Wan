package com.example.waz.UI.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Api.ApiServer;
import com.example.waz.R;
import com.example.waz.UI.Activity.WebActivity;
import com.example.waz.UI.Adapter.KnowArticleAdapter;
import com.example.waz.UI.Bean.KnowArticleBean;
import com.example.waz.UI.Bean.ProjectArticleBean;
import com.example.waz.UI.Bean.SecondBean;
import com.example.waz.UI.Bean.WanArticleBean;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class KnowArticleFragment extends Fragment {


    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private int cid;
    private int page = 0;
    private KnowArticleAdapter adapter;

    public KnowArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_knowarticle, container, false);
        Bundle arguments = getArguments();
        cid = arguments.getInt("cid");
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(ApiServer.mWanUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
        Observable<WanArticleBean> article = apiServer.getWanArticle(page);

        article.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanArticleBean secondBean) {
                        ArrayList<WanArticleBean.DataBean.DatasBean> datas = (ArrayList<WanArticleBean.DataBean.DatasBean>) secondBean.getData().getDatas();
                        if (datas!=null){
                             adapter.add(datas);
                         }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(final View inflate) {

        mRlv = inflate.findViewById(R.id.rlv);
        mSmart = inflate.findViewById(R.id.smart);
        mSmart.setRefreshHeader(new BezierCircleHeader(getContext()));
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new KnowArticleAdapter(getContext());
        mRlv.setAdapter(adapter);
        adapter.onClick(new KnowArticleAdapter.onClick() {
            @Override
            public void onClick(int i) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("title",adapter.mList.get(i).getTitle());
                intent.putExtra("url",adapter.mList.get(i).getLink());
                startActivity(intent);
            }
        });
    }

}
