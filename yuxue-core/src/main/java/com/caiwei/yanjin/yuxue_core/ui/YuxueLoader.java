package com.caiwei.yanjin.yuxue_core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.caiwei.yanjin.yuxue_core.R;
import com.caiwei.yanjin.yuxue_core.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class YuxueLoader {

    //宽高比默认值,即相对于屏幕的比值
    private static final int LOADER_SIZE_SCALE = 8;

    //偏移量
    private static final int LOADER_OFFSET_SCALE = 10;

    //用于存放所有的loader，便于统一管理
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //设置一个默认的loader
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    //让调用更方便
    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    private static void showLoading(Context context,String type){

        final AppCompatDialog dialog = new AppCompatDialog(context,R.style.dialog);
        Log.i("加载5","y");
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);

        //添加到dialog的根视图
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        Log.i("加载3","y");
        LOADERS.add(dialog);
        dialog.show();
        Log.i("加载4","y");
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS){
            if (dialog != null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
}
