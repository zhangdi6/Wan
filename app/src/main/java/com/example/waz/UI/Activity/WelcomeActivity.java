package com.example.waz.UI.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.waz.Base.BaseActivity;
import com.example.waz.R;


public class WelcomeActivity extends BaseActivity {

    private TextView mWelTv1;
    private TextView mWelTv2;
    private TextView mWelTv3;
    private TextView mWelTv4;
    private TextView mWelTv5;
    private TextView mWelTv;
    private TranslateAnimation animation1;
    private TranslateAnimation animation2;
    private TranslateAnimation animation3;
    private TranslateAnimation animation4;
    private ScaleAnimation animation5;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int i = 5;
    private RotateAnimation rotateAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initAnimation();
        initData();

    }

    private void initData() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (i == 0) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    rotateAnimation.cancel();
                    animation5.cancel();
                    handler.removeCallbacks(runnable);
                    finish();
                } else if (i == 5) {
                    i--;
                    mWelTv.setText("倒计时:5");
                    mWelTv1.startAnimation(animation1);
                    initData();
                } else if (i == 4) {
                    i--;
                    mWelTv.setText("倒计时:4");
                    mWelTv2.startAnimation(animation2);
                    initData();
                } else if (i == 3) {
                    i--;
                    mWelTv.setText("倒计时:3");
                    mWelTv3.startAnimation(animation3);
                    initData();
                } else if (i == 2) {
                    i--;
                    mWelTv.setText("倒计时:2");
                    mWelTv4.startAnimation(animation4);
                    initData();
                } else if (i == 1) {
                    i--;
                    mWelTv.setText("倒计时:1");
                    mWelTv1.setVisibility(View.GONE);
                    mWelTv2.setVisibility(View.GONE);
                    mWelTv3.setVisibility(View.GONE);
                    mWelTv5.startAnimation(animation5);
                    mWelTv5.startAnimation(rotateAnimation);
                    mWelTv4.setVisibility(View.GONE);
                    initData();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void initAnimation() {
        animation1 = new TranslateAnimation(0, 250, 0, 0);
        animation1.setDuration(1000);
        animation1.setInterpolator(new OvershootInterpolator());
        animation2 = new TranslateAnimation(0, 0, 0, 600);
        animation2.setDuration(1000);
        animation2.setInterpolator(new AnticipateInterpolator());
        animation3 = new TranslateAnimation(0, -250, 0, 0);
        animation3.setDuration(1000);
        animation3.setInterpolator(new OvershootInterpolator());
        animation4 = new TranslateAnimation(0, 0, 0, -600);
        animation4.setDuration(1000);
        animation4.setInterpolator(new AnticipateInterpolator());
        animation5 = new ScaleAnimation(0, 2.0f, 0, 2.0f, 0.5f, 0.5f);
        rotateAnimation = new RotateAnimation(0, 360);
        animation5.setDuration(1000);
        rotateAnimation.setDuration(1000);

    }

    private void initView() {
        mWelTv1 = (TextView) findViewById(R.id.wel_tv1);
        mWelTv2 = (TextView) findViewById(R.id.wel_tv2);
        mWelTv3 = (TextView) findViewById(R.id.wel_tv3);
        mWelTv4 = (TextView) findViewById(R.id.wel_tv4);
        mWelTv5 = (TextView) findViewById(R.id.wel_tv5);
        mWelTv = (TextView) findViewById(R.id.wel_tv);
        initAnimation2();
    }

    private void initAnimation2() {
        QQ qq = new QQ();
        qq.setDuration(1000);
        qq.setRepeatCount(-1);
        /* mWelTv.startAnimation(qq);*/
        mWelTv1.startAnimation(qq);
        mWelTv2.startAnimation(qq);
        mWelTv3.startAnimation(qq);
        mWelTv4.startAnimation(qq);
        mWelTv5.startAnimation(qq);
    }

    class QQ extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            t.getMatrix().setTranslate((float) Math.sin(interpolatedTime * 40) * 150, (float) Math.sin(interpolatedTime * 40) * 150);

            super.applyTransformation(interpolatedTime, t);
        }
    }
}
