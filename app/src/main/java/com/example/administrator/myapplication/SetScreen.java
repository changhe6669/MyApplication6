package com.example.administrator.myapplication;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;

public class SetScreen {
    public static void SetWallPaper(Context context, Bitmap bitmap) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        try {
            DisplayMetrics outMetrics = context.getResources().getDisplayMetrics();
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;
            System.out.println(width+"   das   "+height);
            mWallManager.suggestDesiredDimensions(width, height);
            bitmap=sBitmap(bitmap,width,height);
            mWallManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("设置成功");

    }
    public static Bitmap sBitmap(Bitmap b, int w, int h) {
        int width = b.getWidth();
        int height = b.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);//缩放
        return Bitmap.createBitmap(b, 0, 0, width, height, matrix, true);
    }
}
