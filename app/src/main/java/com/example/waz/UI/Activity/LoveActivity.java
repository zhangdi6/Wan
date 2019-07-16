package com.example.waz.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.Api.Utils;
import com.example.waz.R;
import com.example.waz.UI.Adapter.LoveAdapter;
import com.example.waz.UI.Bean.MyBean;
import com.example.waz.UI.Fragment.WanFragment;

import java.util.List;

public class LoveActivity extends AppCompatActivity {

    private Toolbar mTb;
    private RecyclerView mRlv;
    private CoordinatorLayout mCc;
    private LoveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        initView();
    }

    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mCc = (CoordinatorLayout) findViewById(R.id.cc);
        mTb.setTitle("收藏");
        setSupportActionBar(mTb);
        mTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        adapter = new LoveAdapter(this);
        mRlv.setAdapter(adapter);
        List<MyBean> myBeans = Utils.queryAll();
        if (myBeans != null && myBeans.size() > 0) {
            adapter.add(myBeans);
        }
        adapter.onClick(new LoveAdapter.onClick() {
            @Override
            public void onClick(int i) {
                Intent intent = new Intent(LoveActivity.this, WebActivity.class);
                intent.putExtra("title",adapter.mList.get(i).getTitle());
                intent.putExtra("url",adapter.mList.get(i).getLink());
                startActivity(intent);
            }
        });
        adapter.onLong(new LoveAdapter.onLong() {
            @Override
            public void onLong(int i) {
                if (adapter.mList.get(i).getIsChecked()){
                    if (Utils.isExists(adapter.mList.get(i))){
                        Toast.makeText(LoveActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Utils.deleteItem(adapter.mList.get(i));
                    adapter.mList.get(i).setIsChecked(false);
                    adapter.mList.remove(i);
                    Toast.makeText(LoveActivity.this, "删除收藏", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    WanFragment.adapter.mList.clear();
                }
            }
        });
    }
}
