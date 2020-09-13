package com.example.administrator.myapplication;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int m =0;
        setContentView(R.layout.activity_main);
        System.out.println(Environment.getExternalStorageDirectory());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StuDBHelper dbHelper = new StuDBHelper(MainActivity.this, "stu_db", null, 1);
//得到一个可读的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues cv = new ContentValues();
//往ContentValues对象存放数据，键-值对模式
        cv.put("id", 1);
        cv.put("sname", "xiaoming");
        cv.put("sage", 21);
        cv.put("ssex", "male");
//调用insert方法，将数据插入数据库
        db.insert("stu_table", null, cv);
//关闭数据库
        Cursor cursor = db.query("stu_table", new String[]{"id", "sname", "sage", "ssex"}, "id=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("sname"));
            String age = cursor.getString(cursor.getColumnIndex("sage"));
            String sex = cursor.getString(cursor.getColumnIndex("ssex"));
            System.out.println("query------->" + "姓名：" + name + " " + "年龄：" + age + " " + "性别：" + sex);
        }
//关闭数据库
        db.close();


        SetWallPaper();
        SetLockWallPaper();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void queryData() {

        StuDBHelper dbHelper = new StuDBHelper(MainActivity.this, "stu_db", null, 1);
//得到一个可写的数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//参数1：表名
//参数2：要想显示的列
//参数3：where子句
//参数4：where子句对应的条件值
//参数5：分组方式
//参数6：having条件
//参数7：排序方式
        Cursor cursor = db.query("stu_table", new String[]{"id", "sname", "sage", "ssex"}, "id=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("sname"));
            String age = cursor.getString(cursor.getColumnIndex("sage"));
            String sex = cursor.getString(cursor.getColumnIndex("ssex"));
            System.out.println("query------->" + "姓名：" + name + " " + "年龄：" + age + " " + "性别：" + sex);
        }
//关闭数据库
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void SetWallPaper() {
        WallpaperManager mWallManager = WallpaperManager.getInstance(this);
        String imageFilesPath = "timg.jpg";
        Bitmap bitmap = getBitmapFromAsset(this, imageFilesPath);
        try {
            mWallManager.setBitmap(bitmap);
            System.out.println("设置成功");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SetLockWallPaper() {
        // TODO Auto-generated method stub
        String imageFilesPath = "timg.jpg";
        try {
            WallpaperManager mWallManager = WallpaperManager.getInstance(this);
            Class class1 = mWallManager.getClass();//获取类名
            Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);//获取设置锁屏壁纸的函数
            setWallPaperMethod.invoke(mWallManager, getBitmapFromAsset(this, imageFilesPath));//调用锁屏壁纸的函数，并指定壁纸的路径imageFilesPath
            System.out.println("设置锁屏成功");
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException ignored) {
                }
            }
        }

        return bitmap;
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
