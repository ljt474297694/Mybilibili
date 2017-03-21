package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.LiveBean;
import com.atguigu.mybilibili.utils.BitmapUtils;
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

public class LiveAdapter extends RecyclerView.Adapter<LiveBaseViewHodler> {
    private static LiveBean.DataBean datas;
    private  Context mContext;
    private static final int BANNER = 0;
    private final LayoutInflater inflater;

    public LiveAdapter(Context mContext, LiveBean.DataBean liveBean) {
        this.mContext = mContext;
        this.datas = liveBean;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public LiveBaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BANNER:
                return new BannerViewHodler(inflater.inflate(R.layout.item_live_banner, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(LiveBaseViewHodler holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class BannerViewHodler extends LiveBaseViewHodler {
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
                    BitmapUtils.glideToImage((String)path,imageView);
                }
            });

            List<String> images = new ArrayList<>();
            for(int i = 0; i < datas.getBanner().size(); i++) {
                images.add(datas.getBanner().get(i).getImg());
            }
            banner.setImages(images);
            banner.start();
        }
    }

}
