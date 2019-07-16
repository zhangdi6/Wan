package com.example.waz.UI.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.waz.UI.Fragment.KnowArticleFragment;

import java.util.ArrayList;

public class VpKnowAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<String> mTitle;
    private final ArrayList<KnowArticleFragment> mList;

    public VpKnowAdapter(FragmentManager fm, ArrayList<String> mTitle, ArrayList<KnowArticleFragment> mList) {
        super(fm);
        this.mTitle = mTitle;
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
