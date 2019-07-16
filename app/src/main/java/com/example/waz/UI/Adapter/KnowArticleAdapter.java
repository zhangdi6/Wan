package com.example.waz.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.waz.R;
import com.example.waz.UI.Bean.KnowArticleBean;
import com.example.waz.UI.Bean.WanArticleBean;


import java.util.ArrayList;

public class KnowArticleAdapter extends RecyclerView.Adapter {
    private Context context;
    public ArrayList<WanArticleBean.DataBean.DatasBean> mList = new ArrayList<>();
    public KnowArticleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_wanarticle, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder2 = (ViewHolder) holder;
        WanArticleBean.DataBean.DatasBean datasBean = mList.get(position);

            holder2.wan.setVisibility(View.GONE);
        holder2.authorname.setText(datasBean.getAuthor());
        holder2.time.setText(datasBean.getNiceDate());
        if (datasBean.getEnvelopePic() != null) {
            holder2.iv.setVisibility(View.VISIBLE);
            Glide.with(context).load(datasBean.getEnvelopePic()).into(holder2.iv);
        } else {
            holder2.iv.setVisibility(View.GONE);
        }
        holder2.title.setText(datasBean.getTitle());
        holder2.supername.setText(datasBean.getSuperChapterName() + "/" + datasBean.getChapterName());
        holder2.love.setText(null);
           holder2.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (onn!=null){
                          onn.onClick(position);
                      }
                  }
              });
              holder2.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {
                      if (on!=null){
                          on.onLong(position);
                      }
                      return true;
                  }
              });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(ArrayList<WanArticleBean.DataBean.DatasBean> datas) {

        mList.addAll(datas);
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
