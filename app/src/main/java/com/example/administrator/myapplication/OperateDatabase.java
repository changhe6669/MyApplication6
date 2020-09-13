package com.example.administrator.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateDatabase {

    //构建初始化可写入权限的数据库
    public static SQLiteDatabase initDatabase(Context context) {
        StuDBHelper dbHelper = new StuDBHelper(context, "my_job", null, 1);
//得到一个可读的SQLiteDatabase对象
        dbHelper.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS  my_job (date varchar(40), job varchar(300))");
        return dbHelper.getWritableDatabase();
    }
}
