package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class BitMapPro {
    public static Bitmap createWatermark(Bitmap bitmap, String mark) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setTextSize(10);
        textPaint.setAntiAlias(true);
        StaticLayout layoutopen = new StaticLayout("要显示的子串", textPaint, (int) 300 , Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);








        canvas.save();


        canvas.translate(20, 30);
        layoutopen.draw(canvas);
        canvas.save();
        return bmp;
    }



    public static Bitmap addWaterMark1(Bitmap src, String water, Context context){
        Bitmap tarBitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        int w = tarBitmap.getWidth();
        int h = tarBitmap.getHeight();
        Canvas canvas = new Canvas(tarBitmap);
        //启用抗锯齿和使用设备的文本字距
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        //字体的相关设置
        textPaint.setTextSize(35.0f);//字体大小
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.RED);
        textPaint.setShadowLayer(3f, 1, 1,context.getResources().getColor(android.R.color.background_dark));
        StaticLayout layout = new StaticLayout(water, textPaint, 500,
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        canvas.save();
        canvas.translate((float)(w*0.1), (float) (h*0.1));//
        layout.draw(canvas);
        canvas.save();
        return tarBitmap;
    }

}
