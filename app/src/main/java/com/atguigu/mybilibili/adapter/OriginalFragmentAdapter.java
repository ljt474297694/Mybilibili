package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.DiscoverOriginalBean;
import com.atguigu.mybilibili.utils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class OriginalFragmentAdapter extends RecyclerView.Adapter<OriginalFragmentAdapter.ViewHolder> {


    private List<DiscoverOriginalBean.DataBean> datas;
    private Context mContext;

    public OriginalFragmentAdapter(Context mContext, List<DiscoverOriginalBean.DataBean> data) {
        this.mContext = mContext;
        this.datas = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_original, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiscoverOriginalBean.DataBean dataBean = datas.get(position);
        BitmapUtils.glideToImage(dataBean.getCover(),holder.ivIcon);
        holder.tvName.setText("up:"+dataBean.getName());
        holder.tvTitle.setText(dataBean.getTitle());
        holder.tvPlay.setText("综合评分:"+dataBean.getPlay());
        holder.tvNumber.setText(position+1+"");

        switch (position) {
            case  0:
                holder.tvNumber.setTextSize(BitmapUtils.dip2px(22));
                holder.tvNumber.setTextColor(Color.parseColor("#fb7299"));
                break;
            case  1:
                holder.tvNumber.setTextSize(BitmapUtils.dip2px(20));
                holder.tvNumber.setTextColor(Color.parseColor("#ddfb7299"));
                break;
            case  2:
                holder.tvNumber.setTextSize(BitmapUtils.dip2px(18));
                holder.tvNumber.setTextColor(Color.parseColor("#bbfb7299"));
                break;
            default:
                holder.tvNumber.setTextSize(BitmapUtils.dip2px(16));
                holder.tvNumber.setTextColor(Color.GRAY);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static  class ViewHolder  extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_play)
        TextView tvPlay;
        @Bind(R.id.tv_number)
        TextView tvNumber;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
