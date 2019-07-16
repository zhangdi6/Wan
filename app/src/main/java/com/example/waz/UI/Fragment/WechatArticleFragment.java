package com.example.waz.UI.Fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.waz.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WechatArticleFragment extends Fragment {


    public WechatArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wechatarticle, container, false);
    }

}
