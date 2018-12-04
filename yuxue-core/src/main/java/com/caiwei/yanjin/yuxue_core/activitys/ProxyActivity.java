package com.caiwei.yanjin.yuxue_core.activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.caiwei.yanjin.yuxue_core.R;
import com.caiwei.yanjin.yuxue_core.delegates.YuxueDelegate;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class ProxyActivity extends SupportActivity{

    public abstract YuxueDelegate setRootDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initContainer(savedInstanceState);
    }

    //初始化视图
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
        System.gc();
        System.runFinalization();
    }
}
