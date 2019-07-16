package com.example.waz.UI.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.waz.R;

public class WebActivity extends AppCompatActivity {

    private Toolbar mTb;
    private WebView mWeb;
    private CoordinatorLayout mCc;
    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        initView();

    }

    private void initView() {
        mTb = (Toolbar) findViewById(R.id.tb);
        mWeb = (WebView) findViewById(R.id.web);
        mCc = (CoordinatorLayout) findViewById(R.id.cc);
        mTb.setTitle(title);
        setSupportActionBar(mTb);
        mTb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"分享");
        menu.add(1,2,2,"在浏览器打开");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_SUBJECT,"share");
                intent.putExtra(Intent.EXTRA_TEXT,url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent,getTitle()));
                break;
            case 2:
                Intent intent1= new Intent();
                intent1.setAction("android.intent.action.VIEW");
                Uri u = Uri.parse(url);
                intent1.setData(u);
                startActivity(intent1);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
