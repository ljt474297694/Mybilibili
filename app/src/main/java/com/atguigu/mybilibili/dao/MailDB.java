package com.atguigu.mybilibili.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 李金桐 on 2017/3/27.
 * QQ: 474297694
 * 功能: xxxx
 */

public class MailDB extends SQLiteOpenHelper {

    public MailDB(Context context) {
        super(context, "mail.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableField.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
