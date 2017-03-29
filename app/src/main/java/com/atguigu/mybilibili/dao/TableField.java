package com.atguigu.mybilibili.dao;

/**
 * Created by 李金桐 on 2017/3/27.
 * QQ: 474297694
 * 功能: xxxx
 */

public class TableField {

    public static final String MAIL_ID = "id";
    public static final String MAIL_NUM = "number";
    public static final String TABLE_NAME = "mail";

    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "("
                    + MAIL_ID + " INTEGER ,"
                    + MAIL_NUM + " INTEGER)";

}
