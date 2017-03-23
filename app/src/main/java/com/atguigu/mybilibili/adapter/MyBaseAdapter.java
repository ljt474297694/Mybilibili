package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by 李金桐 on 2017/3/22.
 * QQ: 474297694
 * 功能: xxxx
 */

public abstract class MyBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHodler>{
    public List<T> datas;
    protected  LayoutInflater inflater;

    public MyBaseAdapter(Context mContext, List<T> data) {
        this.datas = data;
        inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
    @Override
    public void onBindViewHolder(BaseViewHodler holder, int position) {
        holder.setData();
    }

    @Override
    public BaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(parent);
    }

    protected abstract BaseViewHodler setViewHolder(ViewGroup parent);

}
