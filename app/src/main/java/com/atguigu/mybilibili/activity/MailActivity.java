package com.atguigu.mybilibili.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import com.alipay.sdk.app.PayTask;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.dao.MailBean;
import com.atguigu.mybilibili.dao.MailDAO;
import com.atguigu.mybilibili.utils.BitmapUtils;
import com.atguigu.mybilibili.utils.VirtualkeyboardHeight;
import com.atguigu.mybilibili.utils.pay.PayKeys;
import com.atguigu.mybilibili.utils.pay.PayResult;
import com.atguigu.mybilibili.utils.pay.SignUtils;
import com.atguigu.mybilibili.view.AddSubView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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


    //---------------------支付

    //商户PID
    public static final String PARTNER = PayKeys.DEFAULT_PARTNER;
    //商户收款账号
    public static final String SELLER = PayKeys.DEFAULT_SELLER;
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = PayKeys.PRIVATE;
    //支付宝公钥
    public static final String RSA_PUBLIC = PayKeys.PUBLIC;


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MailActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MailActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MailActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(MailActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };
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
                pay();
                break;
        }
    }


    //判断商品是否已经添加过 如果添加过记录 记录数量 如果没添加过默认数量1
    private void isFindMail(int id) {
        allMail = dao.getAllMail();
        if(allMail==null) {
            return;
        }
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



    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay() {
        // 订单
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(MailActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }


    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
