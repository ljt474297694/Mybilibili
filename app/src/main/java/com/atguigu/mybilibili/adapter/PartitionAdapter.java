package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.PartitionBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/22.
 * QQ: 474297694
 * 功能: xxxx
 */

public class PartitionAdapter extends RecyclerView.Adapter<BaseViewHodler> {
    private static final int GRID = 0;
    private static final int DEFAULT = 1;
    private List<PartitionBean.DataBean> data;
    private static LayoutInflater inflater;


    public PartitionAdapter(Context mContext, List<PartitionBean.DataBean> data) {
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? GRID : DEFAULT;
    }

    @Override
    public BaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case GRID:
                return new GridViewHolder(inflater.inflate(R.layout.partition_grid, parent, false));
            case DEFAULT:

                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHodler holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class GridViewHolder extends BaseViewHodler {
        @Bind(R.id.gridview)
        GridView gridview;

        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            gridview.setAdapter(new GridAdapter());
        }

        @Override
        public void setData() {

        }
    }

    static class GridAdapter extends BaseAdapter {
        int[] ids = {R.drawable.ic_category_t1, R.drawable.ic_category_t13, R.drawable.ic_category_t1,
                R.drawable.ic_category_t13, R.drawable.ic_category_t129, R.drawable.ic_category_t4,
                R.drawable.ic_category_t36, R.drawable.ic_category_t160, R.drawable.ic_category_t119,
                R.drawable.ic_category_t155, R.drawable.ic_category_t165, R.drawable.ic_category_t5,
                R.drawable.ic_category_t23, R.drawable.ic_category_t11, R.drawable.ic_category_game_center
        };
        String[] datas = {"直 播", "番 剧", "动 画", "音 乐", "舞 蹈", "游 戏", "科 技", "生 活", "鬼 畜", "时 尚", "广 告", "娱 乐", "电 影", "电视剧", "游戏中心"};

        @Override
        public int getCount() {
            return ids.length;
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_partition_grid, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.ivIcon.setBackgroundResource(ids[position]);
            viewHolder.tvName.setText(datas[position]);
            return convertView;
        }

        static class ViewHolder {
            @Bind(R.id.iv_icon)
            ImageView ivIcon;
            @Bind(R.id.tv_name)
            TextView tvName;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

}
