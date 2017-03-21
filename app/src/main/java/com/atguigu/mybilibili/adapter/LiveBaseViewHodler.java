package com.atguigu.mybilibili.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public abstract class LiveBaseViewHodler extends RecyclerView.ViewHolder {
    public LiveBaseViewHodler(View itemView) {
        super(itemView);
    }
    public  abstract  void setData();
}
