package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckNum extends Activity {
    private Button addNum = null;
    private EditText currentNum = null;
    private TextView showDetail = null;
    static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        addNum = (Button) findViewById(R.id.addNum);
        currentNum = (EditText) findViewById(R.id.currentNum);
        showDetail = (TextView) findViewById(R.id.showDetail);
        initData(CheckNum.this,showDetail);

        addNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = currentNum.getText().toString();
                if(number.matches("[0-9]+")){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());

                    Cursor cursor = db.query("my_job", new String[]{"date","num","job"}, "date=?", new String[]{String.valueOf(formatter.format(date))}, null, null, null);
                    int count=0;
                    while (cursor.moveToNext()) {
                        if(cursor.getString(cursor.getColumnIndex("job")).isEmpty()&&!cursor.getString(cursor.getColumnIndex("num")).equals("0")){
                            count = count + Integer.valueOf(cursor.getString(cursor.getColumnIndex("num")));
                        }
                    }
                    if(count+Integer.valueOf(number)>1500){
                        alertMsg(CheckNum.this,"洗洗睡吧，超了："+(count+Integer.valueOf(number)-1500));

                    }else {
                        ContentValues cv = new ContentValues();
                        cv.put("date", formatter.format(date));
                        cv.put("job", "");
                        cv.put("num", number);
                        OperateDatabase.initJobDatabase(CheckNum.this).insert("my_job", null, cv);
                        alertMsg(CheckNum.this,"已经更新进数据库");
                    }

                }else {
                    alertMsg(CheckNum.this,"传递的傻子逼玩意嘛，看不懂！");
                }
            }
        });
    }

    public static void initData(Context context, TextView showDetail){
        db = OperateDatabase.initJobDatabase(context);
//        Cursor cursor = db.query("my_job", new String[]{"date", "job"}, "date=?", new String[]{String.valueOf(new Date()).substring(0, 10)}, null, null, null);
        Cursor cursor = db.query("my_job", new String[]{"date","num","job"}, null, null, null, null, null);
        String job = "";
        while (cursor.moveToNext()) {
            if(cursor.getString(cursor.getColumnIndex("job")).isEmpty()){
                job = job + cursor.getString(cursor.getColumnIndex("date")) + "：";
                job = job + cursor.getString(cursor.getColumnIndex("num")) + "\n";
            }
        }
        showDetail.setText(job);
    }

    public static void alertMsg(Context context,String warning){
        AlertDialog alertDialog1 = new AlertDialog.Builder(context)
                .setTitle("警告")//标题
                .setMessage(warning)
                .setIcon(R.mipmap.ic_launcher)//图标
                .create();
        alertDialog1.show();
    }
    public void onBackPressed() {

        db.close();
        System.out.println("我被调用了");
        super.onBackPressed();

    }
}
