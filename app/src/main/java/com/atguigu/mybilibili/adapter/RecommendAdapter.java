package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.RecommendBean;
import com.atguigu.mybilibili.utils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/22.
 * QQ: 474297694
 * 功能: xxxx
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {


    public List<RecommendBean.DataBean> datas;
    private final LayoutInflater inflater;

    public RecommendAdapter(Context mContext, List<RecommendBean.DataBean> data) {
        this.datas = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecommendBean.DataBean dataBean = datas.get(position);
        holder.tvTitle.setText(dataBean.getTitle());
        BitmapUtils.glideToImage(dataBean.getCover(), holder.ivIcon);

        if (dataBean.getDislike_reasons() != null) {
            String tag1 = dataBean.getDislike_reasons().get(1).getReason_name();
            tag1 = tag1.substring(tag1.indexOf(":") + 1);
            String tag2 = dataBean.getDislike_reasons().get(2).getReason_name();
            tag2 = tag2.substring(tag2.indexOf(":") + 1);
            holder.tvTag.setText(tag1 + "·" + tag2);
        }
        int play = dataBean.getPlay();
        if (play > 9999) {
           float playf = ((float) play - (float)play % 1000) / 10000;
            holder.tvPlay.setText(playf+"万");
        } else {
            holder.tvPlay.setText(play+"");
        }
        holder.tvDanmu.setText(dataBean.getDanmaku()+"");

        int duration = dataBean.getDuration()*1000;
        String hms = getTimeString(duration);
        holder.tvTime.setText(hms);
    }

    private String getTimeString(int duration) {
        SimpleDateFormat formatter;
        if(duration>60*60*1000) {
             formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。

        }else{
             formatter = new SimpleDateFormat("mm:ss");
        }
        return formatter.format(duration);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_play)
        TextView tvPlay;
        @Bind(R.id.tv_danmu)
        TextView tvDanmu;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_tag)
        TextView tvTag;
        @Bind(R.id.item_live_layout)
        CardView itemLiveLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
