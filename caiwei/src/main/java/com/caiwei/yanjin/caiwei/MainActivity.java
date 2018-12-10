package com.caiwei.yanjin.caiwei;

import android.util.Log;

import com.caiwei.yanjin.yuxue_core.activities.ProxyActivity;
import com.caiwei.yanjin.yuxue_core.delegates.YuxueDelegate;

public class MainActivity extends ProxyActivity {


    @Override
    public YuxueDelegate setRootDelegate() {
        Log.d("设置了根fragment","Y");
        return new ExampleDelegate();
    }

}
