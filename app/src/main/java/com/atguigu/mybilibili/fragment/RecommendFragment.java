package com.atguigu.mybilibili.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.RecommendAdapter;
import com.atguigu.mybilibili.bean.RecommendBean;
import com.atguigu.mybilibili.utils.AppNetConfig;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 推荐
 */

public class RecommendFragment extends BaseFragment {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    private RecommendAdapter adapter;
    private boolean isLoadMore;

    @Override
    protected void initListener() {
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                refresh();
            }
        });
    }

    @Override
    protected void showLoad() {
        super.showLoad();
        swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary);
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    protected int setLayoutId() {

        return R.layout.fragment_recommend;
    }

    @Override
    protected String setUrl() {
        return AppNetConfig.RECOMMEND;
    }

    @Override
    protected void initData(String json, String error) {
        swiperefreshlayout.setRefreshing(false);
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "RecommendFragment initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, RecommendBean.class).getData());
            } else {
                Log.e("TAG", "RecommendFragment initData()联网失败");
            }
        }
    }

    private void setAdapter(final List<RecommendBean.DataBean> data) {
        if (!isLoadMore) {
            adapter = new RecommendAdapter(mContext, data);
            recyclerview.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //当显示最后一条数据的时候会回调多次 所以刷新过一次后才进行下次刷新
                    //刷新后isLoadMore才会=false
                    if (position >= data.size() - 1) {
                        if(!isLoadMore) {
                            isLoadMore = true;
                            refresh();
                        }
                    }
                    return 1;
                }
            });
            recyclerview.setLayoutManager(manager);
        } else {
            int size = adapter.datas.size();
            adapter.datas.addAll(data);
            adapter.notifyItemRangeChanged(size - 1, adapter.datas.size() - size);
            //上啦更多完成后 赋值 以便继续上啦
            isLoadMore = false;
        }
    }


}
