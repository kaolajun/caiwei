package com.caiwei.yanjin.yuxue_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.caiwei.yanjin.yuxue_core.app.Yuxue;

public class DimenUtil {

    //得到屏幕宽度，方法写成public static便于使用
    public static int getScreenWidth(){
        final Resources resources = Yuxue.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    //得到屏幕高度
    public static int getScreenHeight(){
        final Resources resources = Yuxue.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
