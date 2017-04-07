package com.atguigu.mybilibili.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.model.dao.MailBean;
import com.atguigu.mybilibili.model.dao.MailDAO;
import com.atguigu.mybilibili.presenter.CartDaoPresenter;
import com.atguigu.mybilibili.presenter.adapter.CartAdapter;
import com.atguigu.mybilibili.view.ICartDaoView;
import com.atguigu.mybilibili.view.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class CartActivity extends BaseActivity implements ICartDaoView {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.bt_pay)
    Button btPay;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.tv_bianji)
    TextView tvBianji;
    private MailDAO dao;
    private ArrayList<MailBean> allMail;
    private CartDaoPresenter presenter;

    @Override
    public String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new CartDaoPresenter(this);
        presenter.getAllData();
    }

    @Override
    protected void initData(String json, String error) {
        if (allMail == null || allMail.size() == 0) {
            Toast.makeText(CartActivity.this, "还没有数据", Toast.LENGTH_SHORT).show();
        } else {
            CartAdapter adapter = new CartAdapter(this, allMail, tvBianji, checkbox,tvPrice);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.tv_bianji, R.id.bt_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bianji:
                Toast.makeText(CartActivity.this, "编辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_pay:
                Toast.makeText(CartActivity.this, "pay", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void getAll(ArrayList<MailBean> data) {
        this.allMail = data;
    }
}
