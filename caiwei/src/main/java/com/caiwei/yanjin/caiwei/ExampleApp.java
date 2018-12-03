package com.caiwei.yanjin.caiwei;

import android.app.Application;

import com.caiwei.yanjin.yuxue_core.app.Yuxue;

public class ExampleApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Yuxue.init(this)
                .withApiHost("http://127.0.0.1")
                .configure();
    }
}
