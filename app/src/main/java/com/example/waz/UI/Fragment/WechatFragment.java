package com.example.waz.UI.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.waz.Base.BaseFragment;
import com.example.waz.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WechatFragment extends BaseFragment {


    private TextView mTv;
    private ProgressBar mPro;

    public WechatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_wechat, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mTv = inflate.findViewById(R.id.tv);
        mPro = inflate.findViewById(R.id.pro);
        mPro.setVisibility(View.VISIBLE);
        mTv.setVisibility(View.GONE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPro.setVisibility(View.GONE);
                            mTv.setText("This Fragment has nothing,Please go to the next one or the last one");
                            mTv.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }





}
