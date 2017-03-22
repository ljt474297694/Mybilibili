package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.LiveBean;
import com.atguigu.mybilibili.utils.BitmapUtils;
import com.atguigu.mybilibili.view.CircleImageView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class LiveAdapter extends RecyclerView.Adapter<BaseViewHodler> {
    private static final int DEFAULT = 1;
    private static LiveBean.DataBean datas;
    private static Context mContext;
    private static final int BANNER = 0;
    private final LayoutInflater inflater;

    public LiveAdapter(Context mContext, LiveBean.DataBean liveBean) {
        this.mContext = mContext;
        this.datas = liveBean;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            return BANNER;
        } else {

            return DEFAULT;
        }
    }

    @Override
    public BaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                return new BannerViewHodler(inflater.inflate(R.layout.item_live_banner, parent, false));
            case DEFAULT:
                return new DefaultViewHolder(inflater.inflate(R.layout.item_live_default, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHodler holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return 1 + datas.getPartitions().size();
    }

    static class DefaultViewHolder extends BaseViewHodler {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_number)
        TextView tvNumber;
        @Bind(R.id.gridview)
        GridView gridview;
        @Bind(R.id.tv_more)
        TextView tvMore;
        @Bind(R.id.tv_refresh)
        TextView tvRefresh;
        @Bind(R.id.tv_name)
        TextView tvName;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void setData() {
            LiveBean.DataBean.PartitionsBean partitionsBean = datas.getPartitions().get(getLayoutPosition() - 1);

            String src = partitionsBean.getPartition().getSub_icon().getSrc();

            BitmapUtils.glideToImage(src, ivIcon);
            tvNumber.setText(partitionsBean.getPartition().getCount() + "");
            gridview.setAdapter(new GridViewHolder(partitionsBean.getLives()));
            tvName.setText(partitionsBean.getPartition().getName());
        }
    }

    static class GridViewHolder extends BaseAdapter {

        private List<LiveBean.DataBean.PartitionsBean.LivesBean> datas;

        public GridViewHolder(List<LiveBean.DataBean.PartitionsBean.LivesBean> lives) {
            this.datas = lives;
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
                convertView = View.inflate(mContext, R.layout.item_live_grid, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LiveBean.DataBean.PartitionsBean.LivesBean livesBean = datas.get(position);
            String src = livesBean.getCover().getSrc();
            String face = livesBean.getOwner().getFace();
            BitmapUtils.glideToImage(src,viewHolder.itemLiveCover);
            BitmapUtils.glideToImage(face,viewHolder.itemLiveUserCover);
            viewHolder.itemLiveTitle.setText(livesBean.getTitle());
            viewHolder.itemLiveCount.setText(livesBean.getOnline()+"");
            viewHolder.itemLiveUser.setText(livesBean.getOwner().getName());
            return convertView;
        }



        static  class ViewHolder {
            @Bind(R.id.item_live_cover)
            ImageView itemLiveCover;
            @Bind(R.id.item_live_user_cover)
            CircleImageView itemLiveUserCover;
            @Bind(R.id.item_live_title)
            TextView itemLiveTitle;
            @Bind(R.id.item_live_user)
            TextView itemLiveUser;
            @Bind(R.id.item_live_count)
            TextView itemLiveCount;
            @Bind(R.id.item_live_layout)
            CardView itemLiveLayout;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    static class BannerViewHodler extends BaseViewHodler {
        @Bind(R.id.banner)
        Banner banner;

        public BannerViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData() {
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    BitmapUtils.glideToImage((String) path, imageView);
                }
            });

            List<String> images = new ArrayList<>();
            for (int i = 0; i < datas.getBanner().size(); i++) {
                images.add(datas.getBanner().get(i).getImg());
            }

            if(datas.getBanner().size()==1 ){
                images.add(datas.getBanner().get(0).getImg());
            }
            banner.setImages(images);
            banner.start();
        }
    }

}
