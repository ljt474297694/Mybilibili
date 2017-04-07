package com.atguigu.mybilibili.view.activity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.UserBean;
import com.atguigu.mybilibili.utils.RetrofitUtils;
import com.atguigu.mybilibili.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et1)
    EditText et1;
    @Bind(R.id.et2)
    EditText et2;
    @Bind(R.id.btn1)
    Button btn1;
    @Bind(R.id.tv_content)
    TextView tvContent;
    private String baseUrl = "http://47.93.118.241:8081/";

    @Override
    public String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(String json, String error) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 账号 123 密码 123 电话123123123 可以登录
     */
    public void login() {
        String username = et1.getText().toString().trim();
        String password = et2.getText().toString().trim();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)//baseUrl 请求根连接 此处为 "http://47.93.118.241:8081/"
//                .addConverterFactory(GsonConverterFactory.create()) //Gson解析工厂 自动解析Bean
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava回调工厂 表示支持返回Observable
//                .build();
//
//        RequestServes requestServes = retrofit.create(RequestServes.class);

        //调用工具类 创建业务接口实例
        RequestServes retrofitServes = new RetrofitUtils<RequestServes>()
                .createRetrofitServes(baseUrl, RequestServes.class);


        retrofitServes.login(username, password, "123123123")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(UserBean user) throws Exception {
                        tvContent.setText(user.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG", "LoginActivity accept()" + throwable.getMessage());
                    }
                });
    }

    //业务接口
    public interface RequestServes {
        //使用@Query("字段名")   Retrofit会自动拼接字段 发送请求
        //拼接后续连接
        @FormUrlEncoded
        @POST("android/user/login")
        Observable<UserBean> login(@Field("username") String username,
                                   @Field("password") String password,
                                   @Field("phone") String phone);
    }

    @OnClick(R.id.btn1)
    public void onClick() {
        login();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
