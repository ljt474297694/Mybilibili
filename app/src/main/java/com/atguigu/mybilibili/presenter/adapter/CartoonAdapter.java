package com.atguigu.mybilibili.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.model.bean.CartoonBean;
import com.atguigu.mybilibili.utils.BitmapUtils;
import com.atguigu.mybilibili.view.view.MyGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/22.
 * QQ: 474297694
 * 功能: xxxx
 */

public class CartoonAdapter extends RecyclerView.Adapter<BaseViewHodler> {
    private static List<CartoonBean.ResultBean.PreviousBean.ListBean> datas;
    private static LayoutInflater inflater;

    public CartoonAdapter(Context mContext, List<CartoonBean.ResultBean.PreviousBean.ListBean> list) {
        inflater = LayoutInflater.from(mContext);
        this.datas = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public BaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new DefaultViewHolder(inflater.inflate(R.layout.item_cartoon_head, parent, false));
            case 1:
                return new MoreViewHolder(inflater.inflate(R.layout.item_cartoon_more, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHodler holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void refresh(List<CartoonBean.ResultBean.PreviousBean.ListBean> list) {
        datas = list;
    }

    static class MoreViewHolder extends BaseViewHodler {
        @Bind(R.id.gridview)
        MyGridView gridview;

        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData() {
            gridview.setAdapter(new GirdAdapter());
        }
    }

    static class GirdAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GirdViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_cartoon_gird, null);
                viewHolder = new GirdViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (GirdViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(datas.get(position).getTitle());

            BitmapUtils.glideToImage(datas.get(position).getCover(),viewHolder.ivIcon);
            return convertView;
        }

    }

    static class GirdViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.cardview)
        CardView cardview;

        public GirdViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class DefaultViewHolder extends BaseViewHodler {
        public DefaultViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData() {

        }
    }

}
