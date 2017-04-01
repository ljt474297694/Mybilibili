package com.atguigu.mybilibili.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 李金桐 on 2017/3/27.
 * QQ: 474297694
 * 功能: xxxx
 */

public class MailDAO {
    private MailDB mailDB;

    public MailDAO(Context mContext) {
        mailDB = new MailDB(mContext);
    }

    public void addMail(MailBean mailBean) {
        if (mailBean == null) return;

        SQLiteDatabase readableDatabase = mailDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableField.MAIL_NUM, mailBean.getNumber());
        values.put(TableField.MAIL_ID, mailBean.getId());
        readableDatabase.replace(TableField.TABLE_NAME, null, values);

    }
    public void updataMail(MailBean mailBean){
        if (mailBean == null) return;

        SQLiteDatabase readableDatabase = mailDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableField.MAIL_NUM, mailBean.getNumber());
        readableDatabase.update(TableField.TABLE_NAME,values,"id=?",new String[]{mailBean.getId()+""});
    }

    public void deleteMail(MailBean mailBean){
        if (mailBean == null) return;
        SQLiteDatabase database = mailDB.getReadableDatabase();
        database.delete(TableField.TABLE_NAME,"id=?",new String[]{mailBean.getId()+""});
    }

    public ArrayList<MailBean> getAllMail(){
        SQLiteDatabase database = mailDB.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TableField.TABLE_NAME, null);
        if(cursor==null) return null;

        ArrayList<MailBean> datas = new ArrayList<>();

        MailBean bean;
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(TableField.MAIL_ID));
            int number = cursor.getInt(cursor.getColumnIndex(TableField.MAIL_NUM));
            bean = new MailBean(id,number);

            datas.add(bean);
        }
        cursor.close();
        return datas;
    }
}
