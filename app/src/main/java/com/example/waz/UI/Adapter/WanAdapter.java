package com.example.waz.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.waz.R;
import com.example.waz.UI.Activity.MainActivity;
import com.example.waz.UI.Activity.WebActivity;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.example.waz.UI.Fragment.WanFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class WanAdapter extends RecyclerView.Adapter {
    private Context mcontext;
    private ArrayList<WanBannerBean.DataBean> mBanner = new ArrayList<>();
    public ArrayList<WanArticleBean.DataBean.DatasBean> mList = new ArrayList<>();

    public WanAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wanbanner, null);
            return new ViewHolder1(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wanarticle, null);
            return new ViewHolder2(inflate);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == 1) {
            ViewHolder1 holder1 = (ViewHolder1) holder;
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < mBanner.size(); i++) {
                WanBannerBean.DataBean dataBean = mBanner.get(i);
                String title = dataBean.getTitle();
                list.add(title);
            }
            holder1.ban.setBannerAnimation(Transformer.Tablet)
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    .setBannerTitles(list)
                    .setImages(mBanner)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            WanBannerBean.DataBean dataBean = (WanBannerBean.DataBean) path;
                            Glide.with(context).load(dataBean.getImagePath()).into(imageView);
                        }
                    }).setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    WanBannerBean.DataBean dataBean = mBanner.get(position);
                    String url = dataBean.getUrl();
                    String title = dataBean.getTitle();
                    Intent intent = new Intent(mcontext, WebActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", title);
                    mcontext.startActivity(intent);
                }
            }).start();


        } else {
            int newposition = position;
            if (mBanner.size() > 0 && mBanner != null) {
                newposition = position - 1;
            }
            ViewHolder2 holder2 = (ViewHolder2) holder;
            WanArticleBean.DataBean.DatasBean datasBean = mList.get(newposition);
            if (newposition==0) {
                holder2.wan.setText("置顶");
            }else if (newposition%3==2){
                holder2.wan.setText("公众号");
            }else{
                holder2.wan.setText("项目");
            }
            holder2.authorname.setText(datasBean.getAuthor());
            holder2.time.setText(datasBean.getNiceDate());
            if (mList.get(newposition).getIsChecked()) {
                holder2.love.setChecked(true);
            } else {
                holder2.love.setChecked(false);
            }
            if (datasBean.getEnvelopePic() != null) {
                holder2.iv.setVisibility(View.VISIBLE);
                Glide.with(mcontext).load(datasBean.getEnvelopePic()).into(holder2.iv);
            } else {
                holder2.iv.setVisibility(View.GONE);
            }
            holder2.title.setText(datasBean.getTitle());
            holder2.supername.setText(datasBean.getSuperChapterName() + "/" + datasBean.getChapterName());
            holder2.love.setText(null);

            final int finalNewposition = newposition;
            holder2.love.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mList.get(finalNewposition).setChecked(true);
                    } else {
                        mList.get(finalNewposition).setChecked(false);
                    }
                }
            });
            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onn != null) {
                        onn.onClick(finalNewposition);
                    }
                }
            });
            holder2.love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (on!=null){
                        on.onCheckClick(finalNewposition);
                    }
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        if (mBanner != null && mBanner.size() > 0) {
            return mList.size();
        } else {
            return mList.size() + 1;
        }
    }


    public void addBanner(ArrayList<WanBannerBean.DataBean> Banner) {
        mBanner.addAll(Banner);
        notifyDataSetChanged();
    }

    public void addArticle(ArrayList<WanArticleBean.DataBean.DatasBean> List) {
        mList.addAll(List);
        notifyDataSetChanged();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        private final Banner ban;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            ban = itemView.findViewById(R.id.banner_wan);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        private final TextView wan;
        private final TextView authorname;
        private final TextView time;
        private final TextView title;
        private final ImageView iv;
        private final CheckBox love;
        private final TextView supername;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            wan = itemView.findViewById(R.id.wan);
            authorname = itemView.findViewById(R.id.wan_authorname);
            time = itemView.findViewById(R.id.wan_time);
            title = itemView.findViewById(R.id.wan_title);
            iv = itemView.findViewById(R.id.wan_iv);
            love = itemView.findViewById(R.id.wan_love);
            supername = itemView.findViewById(R.id.wan_supername);
        }
    }

    private onClick onn;
    private onCheckClick on;

    public interface onClick {
        void onClick(int i);

    }  public interface onCheckClick {
        void onCheckClick(int i);

    }

    public void onClick(onClick ono) {
        onn = ono;
    }
    public void onCheckClick(onCheckClick ono) {
        on = ono;
    }
}
