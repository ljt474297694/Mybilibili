package com.atguigu.mybilibili.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: BaseViewHodler
 */

public abstract class BaseViewHodler extends RecyclerView.ViewHolder {
    public BaseViewHodler(View itemView) {
        super(itemView);
    }
    public  abstract  void setData();
}
