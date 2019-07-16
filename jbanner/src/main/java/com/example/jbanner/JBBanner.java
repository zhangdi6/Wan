package com.example.jbanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JBBanner extends ConstraintLayout {
    private ViewPager mViewPager;
    private TextView mTitle;
    private int mId = 1;
    private BannerIndicator bannerIndicator;
    private boolean mIsLoop;
    // title 字体大小，单位px
    private int mTitleSize;
    private int mTitleColor;


    private int mIndicatorRadius;

    private int mIndicatorSelectColor;

    private int mIndicatorUnSelectColor;

    private int mMaxTitleSize;
// 自动轮播间隔时间，默认为一秒

    private int mInterval = 1000;

    private List<? extends Object> mDatas = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private JBBannerAdapter mAdapter;
    private static final float MASK_HEIGHT_FACTOR = 2.5f ;
    private ImageView imageView;
    private boolean mIsManualScroll;

    public JBBanner(Context context) {
        super(context);
        init(null,0);
    }

    public JBBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);

    }

    public JBBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }
    private void init(AttributeSet attrs, int defStyleAttr) {
        //规定标题字体最大大小,调用Utils类转换为px
        mMaxTitleSize = SystemFacade.dp2px(getContext(), 16);
        mTitleColor = Color.WHITE;
        //类型数组
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JBanner);
        //默认标题字体 20dp
        mTitleSize = (int) typedArray.getDimension(R.styleable.JBanner_titleSize,
                SystemFacade.dp2px(getContext(), 16));
        // 默认标题颜色白色
        mTitleColor = typedArray.getColor(R.styleable.JBanner_titleColor, Color.WHITE);
        // 默认指示器半径5dp
        mIndicatorRadius = (int) typedArray.getDimension(R.styleable.JBanner_indicatorRadius,
                SystemFacade.dp2px(getContext(), 5));
        // 默认选中指示器颜色为白色
        mIndicatorSelectColor = typedArray.getColor(R.styleable.JBanner_indicatorSelectedColor, Color.WHITE);
        // 默认未选中指示器颜色为灰色
        mIndicatorUnSelectColor = typedArray.getColor(R.styleable.JBanner_indicatorNormalColor, Color.GRAY);
        typedArray.recycle();
        initView();

    }



    public void setAdapter(JBBannerAdapter adapter) {
        mAdapter = adapter;
        mViewPager.setAdapter(new BannerAdapter());

        if (mDatas != null && mDatas.size() > 1) {
            int i = Integer.MAX_VALUE / 2; // 取最大值的中间值
            int j = i % mDatas.size(); // 用中间值取余数
            if (j != 0) { // 如果余数不等于0
                i = (mDatas.size() - j) + i; // 用size - 余数，求出还是多少才能除size 取余等于0 ，
                // 然后再加到中间值，目的是为了让中间值除size 取余等于0
            }
            mViewPager.setCurrentItem(i); // 设置让viewpager 显示中间值，并且这个中间值除以 size 余数 为0
        }


        // 如果自动轮播,发送延时消息定时切换view
        if (mIsLoop) {
            mViewPager.postDelayed(mLooper, 3000);
        }

    }

    private Runnable mLooper = new Runnable() {
        @Override
        public void run() {
            int cr = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++cr); // 每次切换一页page
            mViewPager.postDelayed(this, 3000); //继续下一次轮播
        }
    };
    public void setData(List<?> data, List<String> titles) {
        if (data != null && titles != null) {
            //在两个list 都有值的情况下，如果他们的size 不相等，那么就抛异常。因为图片个数和title 个数对不上。
            if (data.size() != titles.size()) {
                throw new IllegalArgumentException(" data size not equals title size");
            }
        }
        //设置指示器的个数
        bannerIndicator.setIndicatorCount(data == null ? 0 : data.size());
        mDatas = data;
        mTitles = titles;
    }
    public void setloop(boolean loop) {
        mIsLoop = loop;
    }

    public void setTitleSize(int unit, int size) {
        int maxSize = SystemFacade.dp2px(getContext(), 20);
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX: {
                mTitleSize = Math.min(size, maxSize);
                break;
            }
            case TypedValue.COMPLEX_UNIT_DIP: {
                mTitleSize = Math.min(SystemFacade.dp2px(getContext(), size), maxSize);
                break;
            }

            case TypedValue.COMPLEX_UNIT_SP: {
                mTitleSize = Math.min(SystemFacade.sp2px(getContext(), size), maxSize);
                break;
            }
            default: {
                mTitleSize = Math.min(size, maxSize);
                break;
            }
        }

        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);

    }

    public void settTitleColor(int color) {
        mTitleColor = color;
        mTitle.setTextColor(mTitleColor);
    }

    public void setIndicatorRadius(int unit, int radius) {
        if (unit == TypedValue.COMPLEX_UNIT_DIP) {
            radius = SystemFacade.dp2px(getContext(), radius);
        }
        bannerIndicator.setRadius(radius);
    }


    public void setIndicatorSelectColor(int color) {
        bannerIndicator.setSelectedColor(color);
    }


    public void setIndicatorUnSelectColor(int color) {
        bannerIndicator.setUnSelectedColor(color);
    }


    int getIndicatorRadius() {
        return mIndicatorRadius;
    }

    int getIndicatorSelectColor() {
        return mIndicatorSelectColor;
    }

    int getIndicatorUnSelectColor() {
        return mIndicatorUnSelectColor;
    }

    int getMaskMaxHeight() {
        return (int) (mMaxTitleSize * MASK_HEIGHT_FACTOR);
    }
    private String getTitleByPosition(int position) {
        if (mTitles == null || mTitles.size() == 0) {
            return "";
        }
        return mTitles.get(position);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mViewPager = new ViewPager(getContext());
        mViewPager.setId(mId++);
        setViewPagerScroller();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bannerIndicator.setCurrentIndex(position % mDatas.size());
                mTitle.setText(getTitleByPosition(position % mDatas.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(mIsLoop){
                            //如果有用户滑动banner操作,就移除自动发送延时消息的操作
                            getHandler().removeCallbacks(mLooper);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 只有有滑动操作，就认为是手动滑动
                        mIsManualScroll = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mIsLoop){
                            //当UP事件触发后发送延时消息
                            getHandler().postDelayed(mLooper, 1000);
                        }
                        break;
                }
                return false;
            }
        });


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.constrainHeight(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainWidth(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        addView(mViewPager);

         imageView = new ImageView(getContext());
        imageView.setId(mId++);
        imageView.setBackgroundColor(Color.parseColor("#40000000"));
        constraintSet.connect(imageView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(imageView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainWidth(imageView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(imageView.getId(), (int)(MASK_HEIGHT_FACTOR*mTitleSize));
        addView(imageView);

        bannerIndicator = new BannerIndicator(getContext(),this);
        bannerIndicator.setId(mId++);
        constraintSet.connect(bannerIndicator.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,SystemFacade.dp2px(getContext(), 16));
        constraintSet.connect(bannerIndicator.getId(), ConstraintSet.BOTTOM, imageView.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(bannerIndicator.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.TOP);
        constraintSet.constrainWidth(bannerIndicator.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(bannerIndicator.getId(), ConstraintSet.WRAP_CONTENT);
        addView(bannerIndicator);


      /*  constraintSet.connect(mViewPager.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(mViewPager.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        addView(mViewPager);*/





        mTitle = new TextView(getContext());
        mTitle.setId(mId++);
        mTitle.setMaxLines(1);
        mTitle.setGravity(Gravity.CENTER_VERTICAL);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setTextColor(mTitleColor);;


        constraintSet.connect(mTitle.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT,SystemFacade.dp2px(getContext(), 16));
        constraintSet.connect(mTitle.getId(), ConstraintSet.BOTTOM, imageView.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(mTitle.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.TOP);
        constraintSet.connect(mTitle.getId(), ConstraintSet.RIGHT, bannerIndicator.getId(), ConstraintSet.LEFT);
        constraintSet.constrainWidth(mTitle.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainWidth(mTitle.getId(), ConstraintSet.WRAP_CONTENT);
        this.addView(mTitle);

        constraintSet.applyTo(this);
    }
    /**
     * 通过反射修改 viewpager 自动切换下一页的时间，因为默认的时间只有200ms,太短
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            Scroller scroller = new Scroller(getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    int newDuration;
                    // 如果这次是手滑，那么剩下的那一部分滑动的时间我们不修改。就用默认的。
                    if (mIsManualScroll) {
                        newDuration = duration;
                    } else {
                        // 自动切换时，修改默认切换的时间
                        newDuration = mInterval;
                    }
                    super.startScroll(startX, startY, dx, dy, newDuration);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

        public class BannerAdapter extends PagerAdapter {


            @Override
            public int getCount() {
                return mDatas == null ? 0 : Integer.MAX_VALUE;
            }



            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                ImageView imageView = new ImageView(container.getContext());
                imageView.setLayoutParams(new ViewPager.LayoutParams());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (mAdapter != null) {
                    mAdapter.fillData(JBBanner.this, imageView, mDatas.get(position % mDatas.size()), position % mDatas.size());
                }

                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        }
    }

