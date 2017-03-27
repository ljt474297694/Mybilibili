package com.atguigu.mybilibili.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.dao.MailBean;
import com.atguigu.mybilibili.dao.MailDAO;
import com.atguigu.mybilibili.utils.BitmapUtils;
import com.atguigu.mybilibili.utils.VirtualkeyboardHeight;
import com.atguigu.mybilibili.view.AddSubView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class MailActivity extends BaseActivity {


    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    private MailDAO dao;
    private ArrayList<MailBean> allMail;
    private int number = 1;
    private boolean isFindMail;
    @Override
    protected String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initView() {
        tvName.setText("bilibili周边");

        BitmapUtils.glideToImage("http://i0.hdslb.com/bfs/travel/8a328cfe01408cd975241756b5d9ca23cb002f51.jpg", ivIcon);

        toolBar.setTitle("bilibili - 周边商城");
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData(String json, String error) {
        dao = new MailDAO(this);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mail;
    }


    @OnClick({R.id.bt_cart, R.id.bt_add_cart1, R.id.bt_add_cart2, R.id.bt_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cart:
                Toast.makeText(MailActivity.this, "cart", Toast.LENGTH_SHORT).show();
                startActivity(CartActivity.class);
                break;
            case R.id.bt_add_cart1:
                isFindMail(1);
                showPopwindow(1);
                break;
            case R.id.bt_add_cart2:
                isFindMail(2);
                showPopwindow(2);
                break;
            case R.id.bt_pay:
                Toast.makeText(MailActivity.this, "pay", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //判断商品是否已经添加过 如果添加过记录 记录数量 如果没添加过默认数量1
    private void isFindMail(int id) {
        allMail = dao.getAllMail();
        for (int i = 0; i < allMail.size(); i++) {
            if (allMail.get(i).getId() == id) {
                number = allMail.get(i).getNumber();
                isFindMail = true;
                return;
            }
        }
        isFindMail = false;
        number = 1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow(final int id) {

        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片


        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(100);//库存100

        nas_goodinfo_num.setValue(number);


        nas_goodinfo_num.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {

            @Override
            public void onNumberChenge(int n) {
                number = n;
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();

            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFindMail) {
                    dao.updataMail(new MailBean(id, number));
                }else{
                    dao.addMail(new MailBean(id, number));
                }
                window.dismiss();
                //添加购物车
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(this.findViewById(R.id.ll_bottom),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(this));

    }
}
