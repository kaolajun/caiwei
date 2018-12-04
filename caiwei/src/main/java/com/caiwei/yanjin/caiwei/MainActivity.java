package com.caiwei.yanjin.caiwei;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caiwei.yanjin.yuxue_core.activitys.ProxyActivity;
import com.caiwei.yanjin.yuxue_core.delegates.YuxueDelegate;

public class MainActivity extends ProxyActivity {


    @Override
    public YuxueDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
