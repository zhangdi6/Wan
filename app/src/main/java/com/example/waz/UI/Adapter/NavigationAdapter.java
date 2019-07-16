package com.example.waz.UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.R;
import com.example.waz.UI.Bean.NavigationBean;

import java.util.ArrayList;
import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter {
    private ArrayList<NavigationBean.DataBean.ArticlesBean>mList=new ArrayList<>();
    private Context context;

    public NavigationAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.navigation_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
       ViewHolder holder1 = (ViewHolder) holder;
       holder1.tv1.setText(mList.get(position).getTitle());
        if (position%6==0){
            holder1.tv1.setTextColor(Color.parseColor("#A36442"));
        }else if (position%6==1){
            holder1.tv1.setTextColor(Color.parseColor("#617C28"));
        }
        else if (position%6==2){
            holder1.tv1.setTextColor(Color.parseColor("#617C28"));
        }
        else if (position%6==3){
            holder1.tv1.setTextColor(Color.parseColor("#C817C6"));
        }
        else if (position%6==4){
            holder1.tv1.setTextColor(Color.parseColor("#3569D1"));
        }
        else if (position%6==5){
            holder1.tv1.setTextColor(Color.parseColor("#E95E20"));
        }
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

    public void add(List<NavigationBean.DataBean.ArticlesBean> articles) {
        mList.addAll(articles);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


             private final TextView tv1;


             public ViewHolder(View itemView) {
                 super(itemView);

                 tv1 = itemView.findViewById(R.id.title);

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
