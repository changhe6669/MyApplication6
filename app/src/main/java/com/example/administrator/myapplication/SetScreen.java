package com.example.administrator.myapplication;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.IOException;

public class SetScreen {
    public static void SetWallPaper(Context context, Bitmap bitmap) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        try {
            mWallManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("设置成功");

    }
}
