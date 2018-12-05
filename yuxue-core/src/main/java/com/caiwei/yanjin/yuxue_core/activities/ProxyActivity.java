package com.caiwei.yanjin.yuxue_core.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;

import com.caiwei.yanjin.yuxue_core.R;
import com.caiwei.yanjin.yuxue_core.delegates.YuxueDelegate;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class ProxyActivity extends SupportActivity{

    //用于返回根delegate
    public abstract YuxueDelegate setRootDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("在设置容器之前","yes");
        initContainer(savedInstanceState);
        Log.d("设置容器","yes");
    }

    //初始化视图方法
    private void initContainer(@Nullable Bundle savedInstanceState){
        @SuppressLint("RestrictedApi") final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null){
            loadRootFragment(R.id.delegate_container,setRootDelegate());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //优化：进行垃圾回收
        System.gc();
        System.runFinalization();
    }
}
