package com.atguigu.mybilibili.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.view.activity.WebActivity;
import com.atguigu.mybilibili.model.bean.DiscoverHuatiBean;
import com.atguigu.mybilibili.utils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class HuatiAdapter extends RecyclerView.Adapter<HuatiAdapter.ViewHolder> {


    private List<DiscoverHuatiBean.ListBean> datas;
    private Context mContext;

    public HuatiAdapter(Context mContext, List<DiscoverHuatiBean.ListBean> list) {
        this.mContext = mContext;
        this.datas = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_huati, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvContent.setText(datas.get(position).getTitle());
        BitmapUtils.glideToImage(datas.get(position).getCover(),holder.ivIcon);
        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = datas.get(position).getTitle();
                String link = datas.get(position).getLink();
                mContext.startActivity(new Intent(mContext, WebActivity.class)
                        .putExtra("link",link)
                        .putExtra("title",title));
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static  class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
