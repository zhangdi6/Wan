package com.example.jbanner;

import android.widget.ImageView;

public interface JBBannerAdapter<M> {
   void fillData(JBBanner jbBanner, ImageView imageView, M mode, int position);
}
