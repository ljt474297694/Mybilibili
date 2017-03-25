package com.atguigu.mybilibili.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //让mPasswordEdit获取输入焦点
                    String str = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(str)) {
                        Toast.makeText(SearchActivity.this, "内容为空无法搜索", Toast.LENGTH_SHORT).show();
                    } else {
                        search = str;
                        refresh();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("search"))) {
            search = getIntent().getStringExtra("search");
        } else {
            search = "";
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etSearch,
                            0);
                }
            }, 200);

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
