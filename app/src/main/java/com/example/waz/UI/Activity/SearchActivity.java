package com.example.waz.UI.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Api.ApiServer;
import com.example.waz.R;
import com.example.waz.UI.Adapter.HistoryAdapter;
import com.example.waz.UI.Bean.SearchBean;
import com.example.waz.Weight.FlowLayout;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchActivity extends AppCompatActivity {

    private Toolbar mSearchTb;
    private EditText mSearchEt;
    private FlowLayout mFlow;
    /**
     * 清空
     */
    private TextView mClear;
    private RecyclerView mRlv;
    private ArrayList<SearchBean.DataBean> mList = new ArrayList<>();
    private ArrayList<String> mHList = new ArrayList<>();
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();

    }


    private void initData() {
        Retrofit build =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(ApiServer.mWanUrl)
                        .build();
        ApiServer apiServer = build.create(ApiServer.class);
        apiServer.getSearch().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchBean searchBean) {
                        List<SearchBean.DataBean> data = searchBean.getData();
                        mList.addAll(data);
                        for (int i = 0; i < mList.size(); i++) {
                            //获取视图,视图可以自定义,可以添加自己想要的效果
                            TextView label = (TextView) View.inflate(SearchActivity.this, R.layout.item_label, null);
                            //获取数据
                            final String data1 = mList.get(i).getName();
                            final String link = mList.get(i).getLink();

                            //绑定数据
                            label.setText(data1);
                            label.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mHList.add(data1);
                                    adapter.notifyDataSetChanged();
                                    Intent intent = new Intent(SearchActivity.this, SearchArticleActivity.class);
                                    intent.putExtra("title", data1);
                                    startActivity(intent);
                                }
                            });
                            mFlow.addView(label);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tbmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_menu:
                String s = mSearchEt.getText().toString();
                if (!s.equals("")) {
                    mHList.add(s);
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(SearchActivity.this, SearchArticleActivity.class);
                    intent.putExtra("title", s);
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mSearchTb = (Toolbar) findViewById(R.id.search_tb);
        mSearchTb.setTitle("");
        setSupportActionBar(mSearchTb);
        mSearchTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchEt = (EditText) findViewById(R.id.search_et);
        mFlow = (FlowLayout) findViewById(R.id.flow);
        mClear = (TextView) findViewById(R.id.clear);
        mRlv = (RecyclerView) findViewById(R.id.rlv);

        mRlv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(this, mHList);
        mRlv.setAdapter(adapter);
        adapter.onClick(new HistoryAdapter.onClick() {
            @Override
            public void onClick(int i) {
                adapter.mHList.remove(i);
                adapter.notifyItemRemoved(i);
            }
        });
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.mHList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
