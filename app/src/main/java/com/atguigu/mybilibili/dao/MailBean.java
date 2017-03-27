package com.atguigu.mybilibili.dao;

/**
 * Created by 李金桐 on 2017/3/27.
 * QQ: 474297694
 * 功能: xxxx
 */

public class MailBean {
    private int id;
    private int number;
    private boolean isClick = true;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public MailBean(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
