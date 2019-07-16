package com.example.waz.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Api.ApiServer;
import com.example.waz.R;
import com.example.waz.UI.Adapter.ProjectArticleAdapter;
import com.example.waz.UI.Adapter.WanAdapter;
import com.example.waz.UI.Bean.ProjectArticleBean;
import com.example.waz.UI.Bean.SearchBean;
import com.example.waz.UI.Bean.WanArticleBean;

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

public class SearchArticleActivity extends AppCompatActivity {

    private Toolbar mTb;
    private RecyclerView mRlv;
    private String title;
    private ProjectArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcharticle);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        initView();
        initData();
    }
    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.mWanUrl)
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
        Observable<ProjectArticleBean> search = apiServer.postSearch(0, title);
        search.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProjectArticleBean wanArticleBean) {
                        ArrayList<ProjectArticleBean.DataBean.DatasBean> datas = (ArrayList<ProjectArticleBean.DataBean.DatasBean>) wanArticleBean.getData().getDatas();
                        if (datas!=null){
                         adapter.add(datas);

                     }else {
                         Toast.makeText(SearchArticleActivity.this, "报告主人...什么也搜不到", Toast.LENGTH_SHORT).show();
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

    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mTb.setTitle(title);
        setSupportActionBar(mTb);
        mTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
         adapter = new ProjectArticleAdapter(this);
        mRlv.setAdapter(this.adapter);

    }
}
