package com.example.waz.UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.R;
import com.example.waz.UI.Bean.SecondBean;

import java.util.ArrayList;
import java.util.List;

public class KnowAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<SecondBean.DataBean> mList;
    public KnowAdapter(Context context, ArrayList<SecondBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_know, null);
        return new ViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.title.setText(mList.get(position).getName());
        if (position%6==0){
            holder1.title.setTextColor(Color.parseColor("#AC1DF5"));
        }else if (position%6==1){
            holder1.title.setTextColor(Color.parseColor("#472DD8"));
        }
        else if (position%6==2){
            holder1.title.setTextColor(Color.parseColor("#F653DB"));
        }
        else if (position%6==3){
            holder1.title.setTextColor(Color.parseColor("#F7213A"));
        }
        else if (position%6==4){
            holder1.title.setTextColor(Color.parseColor("#F8FE34"));
        }
        else if (position%6==5){
            holder1.title.setTextColor(Color.parseColor("#29F231"));
        }

        List<SecondBean.DataBean.ChildrenBean> beans = mList.get(position).getChildren();
        StringBuffer buffer = new StringBuffer();

        for (int i=0;i<beans.size();i++){
            buffer.append(beans.get(i).getName()+"  ");
        }
        String s = buffer.toString();
        holder1.tv.setText(s);
           holder1.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (onn!=null){
                          onn.onClick(position);
                      }
                  }
              });
              holder1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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

    public void add(List<SecondBean.DataBean> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;  private final TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            tv = itemView.findViewById(R.id.tv);
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
