package com.atguigu.mybilibili.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.SearchAdapter;
import com.atguigu.mybilibili.bean.SearchBean;
import com.atguigu.mybilibili.fragment.SearchFragment;
import com.atguigu.mybilibili.utils.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private String search;
    List<SearchFragment> fragments;
    private SearchAdapter adapter;
    private SearchBean.DataBean data;

    @Override
    protected String setUrl() {
        return AppNetConfig.search(search);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("search"))) {
            search = getIntent().getStringExtra("search");
        } else {
            search = "bilibili";
        }
        etSearch.setText(search);
        etSearch.setSelection(search.length());

    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "SearchActivity initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, SearchBean.class).getData());
            } else {
                Log.e("TAG", "SearchActivity initData()联网失败");
            }
        }
    }

    private void setAdapter(SearchBean.DataBean data) {

        fragments = new ArrayList<>();
        fragments.add(new SearchFragment(data));
        fragments.add(new SearchFragment(data));
        fragments.add(new SearchFragment(data));
        fragments.add(new SearchFragment(data));
        Log.e("TAG", "SearchActivity setAdapter()");
        adapter = new SearchAdapter(getSupportFragmentManager(), fragments, data.getNav().get(1).getTotal());
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }


    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
//                Toast.makeText(SearchActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                search();
                break;
        }
    }

    private void search() {
        String str = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(SearchActivity.this, "内容为空无法搜索", Toast.LENGTH_SHORT).show();
        } else {
            search = str;
            refresh();
        }
    }

}
