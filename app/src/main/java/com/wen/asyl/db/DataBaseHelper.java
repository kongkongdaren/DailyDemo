package com.wen.asyl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wen.asyl.entity.ConstBean;


/**
 * Description：xx <br/>
 * Copyright (c) 2018<br/>
 * This program is protected by copyright laws <br/>
 * Date:2018-07-10 17:26
 *
 * @author 姜文莒
 * @version : 1.0
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATA = "cost_data";
    public static final String COST_TITLE = "cost_title";
    public static final String DAILY_COST = "tb_daily";
    public DataBaseHelper(Context context) {
        super(context, "daily.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists tb_daily("+
                "id integer primary key,"+
                "cost_title varchar,"+
                "cost_data varchar,"+
                "cost_money varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //插入方法
    public void insertCost(ConstBean constBean){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COST_TITLE,constBean.constTitle);
        cv.put(COST_DATA,constBean.constDate);
        cv.put(COST_MONEY,constBean.constMoney);
        database.insert(DAILY_COST,null,cv);
    }
    //查询方法
    public Cursor getAllCursorData(){
        SQLiteDatabase db=getWritableDatabase();
        //按时间进行排序
        return db.query(DAILY_COST,null,null,null,null,null,"cost_data " +"ASC");
    }
    //删除整张表
    public void deleteAllData(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(DAILY_COST,null,null);

    }
    //删除整张表
    public void deleteData(String data){
        SQLiteDatabase db=getWritableDatabase();
        String sql="delete from "+DAILY_COST+" where cost_data=?";
        db.execSQL(sql,new Object[]{data});

    }

}
