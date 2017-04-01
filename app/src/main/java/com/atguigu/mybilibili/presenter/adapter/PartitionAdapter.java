package com.atguigu.mybilibili.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.model.bean.PartitionBean;
import com.atguigu.mybilibili.utils.BitmapUtils;
import com.atguigu.mybilibili.view.view.MyGridView;

import java.util.List;
import java.util.Collections;

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
    private static List<PartitionBean.DataBean> data;
    private static LayoutInflater inflater;

    static int[] ids = {R.drawable.ic_category_t1, R.drawable.ic_category_t13, R.drawable.ic_category_t1,
            R.drawable.ic_category_t3, R.drawable.ic_category_t129, R.drawable.ic_category_t4,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160, R.drawable.ic_category_t119,
            R.drawable.ic_category_t155, R.drawable.ic_category_t165, R.drawable.ic_category_t5,
            R.drawable.ic_category_t23, R.drawable.ic_category_t11, R.drawable.ic_category_game_center
    };

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
                return new DefaultViewHolder(inflater.inflate(R.layout.item_partition_default, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHodler holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        int a = 0;
        for (int i = 0; i < data.size(); i++) {
            if (!TextUtils.isEmpty(data.get(i).getTitle())) {
                a++;
            }
        }
        return 1 + a;
    }

    public void refresh(List<PartitionBean.DataBean> bean) {
        data = bean;
    }


    static class DefaultViewHolder extends BaseViewHodler {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.gridview)
        MyGridView gridview;
        @Bind(R.id.tv_more)
        TextView tvMore;
        @Bind(R.id.tv_refresh)
        TextView tvRefresh;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void setData() {
            PartitionBean.DataBean dataBean = data.get(getLayoutPosition());
            final ItemGridViewHolder holder;
            if (!TextUtils.isEmpty(dataBean.getTitle())) {
                ivIcon.setBackgroundResource(getIds(dataBean.getTitle()));
                tvName.setText(dataBean.getTitle());
                holder = new ItemGridViewHolder(dataBean);
                gridview.setAdapter(holder);
            } else {
                while (TextUtils.isEmpty(dataBean.getTitle())) {
                    dataBean = data.get((int) Math.random() * 10);
                }
                ivIcon.setBackgroundResource(getIds(dataBean.getTitle()));
                tvName.setText(dataBean.getTitle());
                holder = new ItemGridViewHolder(dataBean);
                gridview.setAdapter(holder);

            }
            tvRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.refresh();

                }
            });
        }

        private int getIds(String title) {
            switch (title) {
                case "动画区":
                    return ids[2];
                case "音乐区":
                    return ids[3];
                case "舞蹈区":
                    return ids[4];
                case "游戏区":
                    return ids[5];
                case "鬼畜区":
                    return ids[8];
                case "生活区":
                    return ids[7];
                case "科技区":
                    return ids[6];
                case "活动中心":
                    return ids[14];
                case "时尚区":
                    return ids[9];
                case "广告区":
                    return ids[10];
                case "娱乐区":
                    return ids[11];
                case "电视剧区":
                    return ids[13];
                case "电影区":
                    return ids[12];
            }
            return ids[0];
        }
    }

    static class ItemGridViewHolder extends BaseAdapter {

        private final PartitionBean.DataBean datas;

        public ItemGridViewHolder(PartitionBean.DataBean dataBean) {
            this.datas = dataBean;
        }

        @Override
        public int getCount() {
            return 4;
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
                convertView = inflater.inflate(R.layout.item_partition_default_grid, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (!TextUtils.isEmpty(datas.getTitle())) {
                if (position > datas.getBody().size() - 1) {
                    position = datas.getBody().size() - 1;
                }
                BitmapUtils.glideToImage(datas.getBody().get(position).getCover(), viewHolder.ivIcon);
                viewHolder.tvTitle.setText(datas.getBody().get(position).getTitle());
                viewHolder.tvDanmu.setText(datas.getBody().get(position).getDanmaku() + "");
                int play = datas.getBody().get(position).getPlay();
                if (play > 9999) {
                    float playf = ((float) play - (float) play % 1000) / 10000;
                    viewHolder.tvPlay.setText(playf + "万");
                } else {
                    viewHolder.tvPlay.setText(play + "");
                }
            }
            return convertView;
        }

        public void refresh() {
            Collections.shuffle(datas.getBody());
            notifyDataSetChanged();
        }


        static class ViewHolder {
            @Bind(R.id.iv_icon)
            ImageView ivIcon;
            @Bind(R.id.tv_title)
            TextView tvTitle;
            @Bind(R.id.tv_play)
            TextView tvPlay;
            @Bind(R.id.tv_danmu)
            TextView tvDanmu;
            @Bind(R.id.item_live_layout)
            CardView itemLiveLayout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    static class GridViewHolder extends BaseViewHodler {
        @Bind(R.id.gridview)
        GridView gridview;

        public GridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData() {
            gridview.setAdapter(new GridAdapter());
        }
    }

    static class GridAdapter extends BaseAdapter {

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
