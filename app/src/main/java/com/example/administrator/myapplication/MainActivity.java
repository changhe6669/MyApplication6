package com.example.administrator.myapplication;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnAddData = null;
    private Button btnCheckNum = null;
    private EditText editText = null;
    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddData = (Button) findViewById(R.id.addData);
        btnCheckNum = (Button) findViewById(R.id.checkNum);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.jobShow);

        initPreface();


        btnCheckNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CheckNum.class);
                startActivity(intent);
            }
        });


        btnAddData.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myJob = editText.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                ContentValues cv = new ContentValues();
//往ContentValues对象存放数据，键-值对模式
                cv.put("date", formatter.format(date));
                cv.put("job", myJob);
                cv.put("num","0");
                OperateDatabase.initJobDatabase(MainActivity.this).insert("my_job", null, cv);

//
                SQLiteDatabase db = OperateDatabase.initJobDatabase(MainActivity.this);
                Cursor cursor = db.query("my_job", new String[]{"date","num","job"}, "date=?", new String[]{String.valueOf(formatter.format(date))}, null, null, null);
                String job = formatter.format(date) + "：预处理或者Java代码1500行，至2021年10月1日！\n\n";
                while (cursor.moveToNext()) {
                    if(!cursor.getString(cursor.getColumnIndex("job")).isEmpty()) {
                        job = job + cursor.getString(cursor.getColumnIndex("date")) + "：";
                        job = job + cursor.getString(cursor.getColumnIndex("job")) + "\n";
                    }

                }
                textView.setText(job);
                System.out.println(job);
//                Bitmap bitmap=BitMapPro.createWatermark(ReturnBitmap.getBitmapFromAsset(MainActivity.this,"timg.jpg"),job);
                Bitmap bitmap = BitMapPro.addWaterMark1(ReturnBitmap.getBitmapFromAsset(MainActivity.this, "timg.jpg"), job, MainActivity.this);

                SetScreen.SetWallPaper(MainActivity.this, bitmap);
                db.close();

            }
        });
    }


    public void initPreface() {
        SQLiteDatabase db = OperateDatabase.initJobDatabase(MainActivity.this);
//        Cursor cursor = db.query("my_job", new String[]{"date", "job"}, "date=?", new String[]{String.valueOf(new Date()).substring(0, 10)}, null, null, null);
        Cursor cursor = db.query("my_job", new String[]{"date","num","job"}, null, null, null, null, null);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String job = formatter.format(date) + "：预处理或者Java代码1500行，至2021年10月1日！\n\n";
        while (cursor.moveToNext()) {
            if(!cursor.getString(cursor.getColumnIndex("job")).isEmpty()) {
                job = job + cursor.getString(cursor.getColumnIndex("date")) + "：";
                job = job + cursor.getString(cursor.getColumnIndex("job")) + "\n";
            }
        }
        textView.setText(job);
        job = formatter.format(date) + "：预处理或者Java代码1500行，至2021年10月1日！\n\n";
        cursor = db.query("my_job", new String[]{"date","num","job"}, "date=?", new String[]{String.valueOf(formatter.format(date))}, null, null, null);
//        Cursor cursor = db.query("my_job", new String[]{"date", "job"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            if(!cursor.getString(cursor.getColumnIndex("job")).isEmpty()) {
                job = job + cursor.getString(cursor.getColumnIndex("date")) + "：";
                job = job + cursor.getString(cursor.getColumnIndex("job")) + "\n";
            }
        }
        Bitmap bitmap = BitMapPro.addWaterMark1(ReturnBitmap.getBitmapFromAsset(MainActivity.this, "timg.jpg"), job, MainActivity.this);

        SetScreen.SetWallPaper(MainActivity.this, bitmap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}