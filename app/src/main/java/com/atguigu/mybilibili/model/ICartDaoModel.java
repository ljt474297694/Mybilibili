package com.atguigu.mybilibili.model;

import com.atguigu.mybilibili.model.dao.MailBean;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public interface ICartDaoModel {
    void   getAll();

    void updata(MailBean bean);

    void delete(MailBean bean);

    void add(MailBean bean);
}
