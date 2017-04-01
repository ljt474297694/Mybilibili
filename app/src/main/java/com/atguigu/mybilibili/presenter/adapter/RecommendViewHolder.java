package com.atguigu.mybilibili.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.view.activity.VideoActivity;
import com.atguigu.mybilibili.model.bean.RecommendBean;
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

public class RecommendViewHolder extends BaseViewHodler {
    private final Context mContext;

//    @Override
//    protected BaseViewHodler setViewHolder(ViewGroup parent) {
//        return new ViewHolder(inflater.inflate(R.layout.item_recommend, parent, false),datas);
//    }

    public List<RecommendBean.DataBean> datas;
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

    public  RecommendViewHolder(View view,  List<RecommendBean.DataBean> datas,  Context mContext) {
        super(view);
        this.datas = datas;
        ButterKnife.bind(this, view);
        this.mContext =mContext;

    }

    @Override
    public void setData() {
        RecommendBean.DataBean dataBean = datas.get(getLayoutPosition());
        tvTitle.setText(dataBean.getTitle());
        BitmapUtils.glideToImage(dataBean.getCover(), ivIcon);

        if (dataBean.getDislike_reasons() != null) {
            String tag1 = dataBean.getDislike_reasons().get(1).getReason_name();
            tag1 = tag1.substring(tag1.indexOf(":") + 1);
            String tag2 = dataBean.getDislike_reasons().get(2).getReason_name();
            tag2 = tag2.substring(tag2.indexOf(":") + 1);
            tvTag.setText(tag1 + "·" + tag2);
        }
        int play = dataBean.getPlay();
        if (play > 9999) {
            float playf = ((float) play - (float) play % 1000) / 10000;
            tvPlay.setText(playf + "万");
        } else {
            tvPlay.setText(play + "");
        }
        tvDanmu.setText(dataBean.getDanmaku() + "");

        int duration = dataBean.getDuration() * 1000;
        String hms = getTimeString(duration);
        tvTime.setText(hms);
        itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, datas.get(getLayoutPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext,VideoActivity.class).putExtra("image",datas.get(getLayoutPosition()).getCover()));
            }
        });
    }

    private String getTimeString(int duration) {
        SimpleDateFormat formatter;
        if (duration > 60 * 60 * 1000) {
            formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。

        } else {
            formatter = new SimpleDateFormat("mm:ss");
        }
        return formatter.format(duration);
    }


    public void refresh(List<RecommendBean.DataBean> data) {
        this.datas =data;
    }
}
