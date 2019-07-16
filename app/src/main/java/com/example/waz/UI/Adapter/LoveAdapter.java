package com.example.waz.UI.Adapter;

import android.content.Context;
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
import com.example.waz.Api.Utils;
import com.example.waz.R;
import com.example.waz.UI.Bean.MyBean;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;

import java.util.ArrayList;
import java.util.List;

public class LoveAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public ArrayList<MyBean> mList = new ArrayList<>();

    public LoveAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_wanarticle, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder2 = (ViewHolder) holder;
        MyBean datasBean = mList.get(position);
        holder2.wan.setText("项目");
        holder2.authorname.setText(datasBean.getAuthorName());
        holder2.time.setText(datasBean.getNiceDate());
        if (datasBean.getImg() != null) {
            holder2.iv.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(datasBean.getImg()).into(holder2.iv);
        } else {
            holder2.iv.setVisibility(View.GONE);
        }
        holder2.title.setText(datasBean.getTitle());
        holder2.supername.setText(datasBean.getSuperName() + "/" + datasBean.getAuthorName());
        holder2.love.setText(null);
        if (mList.get(position).getIsChecked()) {
            holder2.love.setChecked(true);
        } else {
            holder2.love.setChecked(false);
        }
        holder2.love.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mList.get(position).setIsChecked(true);
                } else {
                    mList.get(position).setIsChecked(false);
                }
            }
        });
           holder2.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (onn!=null){
                          onn.onClick(position);
                      }
                  }
              });
             holder2.love.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (on!=null){
                         on.onLong(position);
                     }
                 }
             });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(List<MyBean> myBeans) {
        mList.addAll(myBeans);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView wan;
        private final TextView authorname;
        private final TextView time;
        private final TextView title;
        private final ImageView iv;
        private final CheckBox love;
        private final TextView supername;

        public ViewHolder(@NonNull View itemView) {
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
      private onLong on;
          private onClick onn;
          public interface onLong{
              void onLong(int i);
          }
          public void onLong(onLong ono){
              on=ono;
          }
          public interface onClick{
              void onClick(int i);
          }
          public void onClick(onClick ono){
              onn=ono;
          }
}
