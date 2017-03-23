package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static final int DEFAULT = 2;
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
        } else if (position == 1) {
            return 1;
        } else {
            return DEFAULT;
        }
    }

    @Override
    public BaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                return new BannerViewHodler(inflater.inflate(R.layout.item_live_banner, parent, false));

            case 1:
                return new BannerBottomViewHodler(inflater.inflate(R.layout.item_live_banner_bottom, parent, false));
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
        @Bind(R.id.recyclerview)
        RecyclerView recyclerView;
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
            LiveBean.DataBean.PartitionsBean partitionsBean = datas.getPartitions().get(getLayoutPosition() - 2);

            String src = partitionsBean.getPartition().getSub_icon().getSrc();

            BitmapUtils.glideToImage(src, ivIcon);
            tvNumber.setText(partitionsBean.getPartition().getCount() + "");
            recyclerView.setAdapter(new MyBaseAdapter<LiveBean.DataBean.PartitionsBean.LivesBean>(mContext,partitionsBean.getLives()) {
                @Override
                protected BaseViewHodler setViewHolder(ViewGroup parent) {
                    return new ViewHolder(inflater.inflate(R.layout.item_live_grid,parent,false),datas);
                }
                @Override
                protected int setItemCount() {
                    return 4;
                }
            });
            recyclerView.setLayoutManager(new GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false));
            tvName.setText(partitionsBean.getPartition().getName());
        }
    }
        static class ViewHolder extends BaseViewHodler{
            private  List<LiveBean.DataBean.PartitionsBean.LivesBean> datas;
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

            ViewHolder(View view, List<LiveBean.DataBean.PartitionsBean.LivesBean> datas) {
                super(view);
                ButterKnife.bind(this, view);
                this.datas = datas;
            }
            @Override
            public void setData() {
                LiveBean.DataBean.PartitionsBean.LivesBean livesBean = datas.get(getLayoutPosition());
                String src = livesBean.getCover().getSrc();
                String face = livesBean.getOwner().getFace();
                BitmapUtils.glideToImage(src, itemLiveCover);
                BitmapUtils.glideToImage(face, itemLiveUserCover);
                itemLiveTitle.setText(livesBean.getTitle());
               itemLiveCount.setText(livesBean.getOnline() + "");
                itemLiveUser.setText(livesBean.getOwner().getName());
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

            banner.setImages(images);
            banner.start();
        }
    }

    static class BannerBottomViewHodler extends BaseViewHodler {
        public BannerBottomViewHodler(View view) {
            super(view);
        }

        @Override
        public void setData() {

        }
    }
}
