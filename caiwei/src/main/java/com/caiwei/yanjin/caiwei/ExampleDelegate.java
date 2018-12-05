package com.caiwei.yanjin.caiwei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.caiwei.yanjin.yuxue_core.delegates.YuxueDelegate;

public class ExampleDelegate extends YuxueDelegate {

    @Override
    public Object setLayout() {
        Log.d("设置了布局","Y");
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }
}
