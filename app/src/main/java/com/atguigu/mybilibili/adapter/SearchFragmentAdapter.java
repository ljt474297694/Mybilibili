package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.SearchBean;
import com.atguigu.mybilibili.utils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class SearchFragmentAdapter extends RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder> {


    private  List<SearchBean.DataBean.ItemsBean.ArchiveBean> data;
    private Context mContext;

    public SearchFragmentAdapter(Context mContext, SearchBean.DataBean data) {
        this.mContext = mContext;
        this.data = data.getItems().getArchive();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchBean.DataBean.ItemsBean.ArchiveBean archiveBean = data.get(position);
        holder.tvName.setText(archiveBean.getAuthor());
        holder.tvTitle.setText(archiveBean.getTitle());
        holder.tvPlay.setText("播放:"+archiveBean.getPlay());
        BitmapUtils.glideToImage(archiveBean.getCover(),holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_play)
        TextView tvPlay;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
