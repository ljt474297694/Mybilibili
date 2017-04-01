package com.atguigu.mybilibili.presenter;

import com.atguigu.mybilibili.model.CartDaoModel;
import com.atguigu.mybilibili.model.dao.MailBean;
import com.atguigu.mybilibili.view.ICartDaoView;

import java.util.ArrayList;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public class CartDaoPresenter implements ICartDaoPresenter {

    private ICartDaoView mICartDaoView;
    private final CartDaoModel cartDaoModel;

    public CartDaoPresenter(ICartDaoView iCartDaoView) {
        this.mICartDaoView = iCartDaoView;
        cartDaoModel = new CartDaoModel(this);
    }

    public void getAllData() {
        cartDaoModel.getAll();
    }

    @Override
    public void getAll(ArrayList<MailBean> data) {
        mICartDaoView.getAll(data);
    }

    public void updata(MailBean bean) {
        cartDaoModel.updata(bean);
    }

    public void delete(MailBean bean) {
        cartDaoModel.delete(bean);
    }

    public void add(MailBean bean) {
        cartDaoModel.add(bean);
    }
}
