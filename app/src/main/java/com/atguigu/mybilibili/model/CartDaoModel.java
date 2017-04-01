package com.atguigu.mybilibili.model;

import com.atguigu.mybilibili.app.MyApplication;
import com.atguigu.mybilibili.model.dao.MailBean;
import com.atguigu.mybilibili.model.dao.MailDAO;
import com.atguigu.mybilibili.presenter.ICartDaoPresenter;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public class CartDaoModel implements ICartDaoModel {

    private ICartDaoPresenter mICartDaoPresenter;
    private MailDAO dao;

    public CartDaoModel(ICartDaoPresenter iCartDaoPresenter) {
        this.mICartDaoPresenter = iCartDaoPresenter;
        dao = new MailDAO(MyApplication.getInstance());
    }


    @Override
    public void getAll() {
        mICartDaoPresenter.getAll(dao.getAllMail());
    }

    @Override
    public void updata(MailBean bean) {
        dao.updataMail(bean);
    }

    @Override
    public void delete(MailBean bean) {
        dao.deleteMail(bean);
    }

    @Override
    public void add(MailBean bean) {
        dao.addMail(bean);
    }
}
