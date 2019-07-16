package com.example.waz.UI.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.waz.Api.ApiServer;
import com.example.waz.R;
import com.example.waz.UI.Adapter.VpKnowAdapter;
import com.example.waz.UI.Bean.SecondBean;
import com.example.waz.UI.Fragment.KnowArticleFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class KnowledgeActivity extends AppCompatActivity {

    private Toolbar mTb;
    private TabLayout mTab;
    private ViewPager mVp;
    private SecondBean.DataBean bean;
    private VpKnowAdapter adapter;
    private ArrayList<String> mTitle=new ArrayList<>();
    private ArrayList<KnowArticleFragment> mList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        Intent intent = getIntent();
        bean = (SecondBean.DataBean) intent.getSerializableExtra("bean");
        initView();
        initData();
    }

    private void initData() {
        final Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiServer.mWanUrl)
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
     apiServer.getSecond().subscribeOn(Schedulers.io())
     .observeOn(AndroidSchedulers.mainThread())
     .subscribe(new Observer<SecondBean>() {
         @Override
         public void onSubscribe(Disposable d) {

         }

         @Override
         public void onNext(SecondBean secondBean) {
             List<SecondBean.DataBean> data = secondBean.getData();
             for (int i = 0; i <data.size() ; i++) {
                 SecondBean.DataBean dataBean = data.get(i);
                 String name = dataBean.getName();
                 mTitle.add(name);
                 KnowArticleFragment fragment = new KnowArticleFragment();
                 Bundle bundle=new Bundle();
                 bundle.putInt("cid",data.get(i).getId());
                 fragment.setArguments(bundle);
                 mList.add(fragment);
             }
             adapter = new VpKnowAdapter(getSupportFragmentManager(), mTitle, mList);
             mVp.setAdapter(adapter);
             mTab.setupWithViewPager(mVp);
         }

         @Override
         public void onError(Throwable e) {

         }

         @Override
         public void onComplete() {

         }
     })
     ;
    }

    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mTab = (TabLayout) findViewById(R.id.tab);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTb.setTitle(bean.getName());
        setSupportActionBar(mTb);
        mTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
