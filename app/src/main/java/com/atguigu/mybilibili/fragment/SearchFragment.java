package com.atguigu.mybilibili.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.SearchFragmentAdapter;
import com.atguigu.mybilibili.bean.SearchBean;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class SearchFragment extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private SearchBean.DataBean data;
    private SearchFragmentAdapter adapter;

    public SearchFragment(SearchBean.DataBean data) {
        super();
        this.data = data;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected String setUrl() {
        return null;
    }
    @Override
    protected void initData(String jaon, String error) {
        adapter = new SearchFragmentAdapter(mContext, data);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

}
