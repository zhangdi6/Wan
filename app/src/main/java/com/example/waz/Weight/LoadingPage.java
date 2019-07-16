package com.example.waz.Weight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.waz.R;



public class LoadingPage extends ConstraintLayout {
    public static final int MODE_ONLY_SHOWLOADING=1;
    public static final int MODE_SHOW_BUTTON_AND_TEXT=1;

    private ProgressBar mPbLoading;
    private Button mBtnAction;
    private TextView mTvMsg;
    private onReLoadCallBack mOnReloadCallBack;
    public LoadingPage(Context context) {
        super(context);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPbLoading=findViewById(R.id.loading_pb);
        mBtnAction=findViewById(R.id.loading_btn);
        mTvMsg=findViewById(R.id.loading_tv);
        mBtnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnReloadCallBack!=null){
                    mOnReloadCallBack.reLoad();
                    startLoading(MODE_SHOW_BUTTON_AND_TEXT);
                }
            }
        });
        startLoading();
    }

   public void startLoading() {
        startLoading(MODE_ONLY_SHOWLOADING);
    }
    public void startLoading(int mode){
        if (mode==MODE_ONLY_SHOWLOADING){
            mBtnAction.setVisibility(GONE);
            mTvMsg.setVisibility(GONE);
            mPbLoading.setVisibility(VISIBLE);
            setBackgroundColor(Color.TRANSPARENT);
        }else if (mode==MODE_SHOW_BUTTON_AND_TEXT){
            mBtnAction.setVisibility(VISIBLE);
            mPbLoading.setVisibility(GONE);
            mTvMsg.setVisibility(VISIBLE);
            mTvMsg.setText(R.string.loading_failed);
            setBackgroundColor(Color.WHITE);
        }
    }
    public void onError(){
        onError(getContext().getString(R.string.loading_error));
    }

    public void onError(String string) {
        onError(null,string);
    }
    public void onError(onReLoadCallBack callBack,String res){
        mTvMsg.setText(res);
        mTvMsg.setVisibility(VISIBLE);
        mBtnAction.setVisibility(VISIBLE);
        mPbLoading.clearAnimation();
        mPbLoading.setVisibility(GONE);
        mOnReloadCallBack=callBack;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPbLoading.clearAnimation();
    }

    public interface onReLoadCallBack {
           void reLoad();
       }
}
