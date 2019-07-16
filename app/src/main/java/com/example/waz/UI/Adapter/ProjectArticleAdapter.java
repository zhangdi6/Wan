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
import com.example.waz.R;
import com.example.waz.UI.Bean.ProjectArticleBean;
import java.util.ArrayList;
public class ProjectArticleAdapter extends RecyclerView.Adapter {
    private final Context context;
    private onClickListener on;
    public ArrayList<ProjectArticleBean.DataBean.DatasBean> mList=new ArrayList<>();
    public ProjectArticleAdapter(Context searchArticleActivity) {
        this.context = searchArticleActivity;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_project, null);
        return new ViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.author.setText(mList.get(position).getAuthor());
        holder1.desc.setText(mList.get(position).getDesc());
        holder1.niceData.setText(mList.get(position).getNiceDate());
        holder1.title.setText(mList.get(position).getTitle());
        if (mList.get(position).getEnvelopePic()!=null){
            Glide.with(context).load(mList.get(position).getEnvelopePic()).into(holder1.iv);
        }else{
            holder1.iv.setVisibility(View.GONE);
        }
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (on != null) {
                    on.onClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void add(ArrayList<ProjectArticleBean.DataBean.DatasBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView title;
        private final TextView desc;
        private final TextView niceData;
        private final TextView author;
        private final CheckBox mcb;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            niceData = itemView.findViewById(R.id.niceData);
            author = itemView.findViewById(R.id.author);
            mcb = itemView.findViewById(R.id.cb);
        }
    }

    public interface onClickListener {
        void onClickListener(int i);
    }

    public void onClickListener(onClickListener ono) {
        on = ono;
    }


}
