package com.example.waz.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waz.R;
import com.example.waz.UI.Activity.SearchActivity;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter {
    private final SearchActivity searchActivity;
    public final ArrayList<String> mHList;

    public HistoryAdapter(SearchActivity searchActivity, ArrayList<String> mHList) {

        this.searchActivity = searchActivity;
        this.mHList = mHList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tv.setText(mHList.get(position));
        holder1.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onn!=null){
                    onn.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHList.size();
    }
     class ViewHolder extends RecyclerView.ViewHolder{

             private final ImageView iv;
             private final TextView tv;


             public ViewHolder(View itemView) {
                 super(itemView);
                 iv = itemView.findViewById(R.id.delete);
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
