package com.atguigu.mybilibili.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.model.bean.SearchBean;
import com.atguigu.mybilibili.presenter.adapter.SearchFragmentAdapter;
import com.atguigu.mybilibili.view.BaseFragment;

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
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String setUrl() {
        return null;
    }

    @Override
    protected void initData(String jaon, String error) {
        if (adapter == null) {
            adapter = new SearchFragmentAdapter(mContext, data);
        } else {
            adapter.refresh(data);
            adapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

}
