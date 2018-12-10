package com.caiwei.yanjin.caiwei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.caiwei.yanjin.yuxue_core.delegates.YuxueDelegate;
import com.caiwei.yanjin.yuxue_core.net.RestClient;
import com.caiwei.yanjin.yuxue_core.net.callback.IError;
import com.caiwei.yanjin.yuxue_core.net.callback.IFailure;
import com.caiwei.yanjin.yuxue_core.net.callback.ISuccess;

public class ExampleDelegate extends YuxueDelegate {

    @Override
    public Object setLayout() {
        Log.d("设置了布局","Y");
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    //建造者模式生成RestClient
    private void testRestClient(){
        RestClient.builder()
                .url("https://baidu.com/")
                .loader(getContext())
                .params("","")
                .success(new ISuccess() {
                    @Override
                    public void OnSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
            @Override
            public void onError(int code, String msg) {

            }
                })
                .failure(new IFailure() {
                    @Override
                    public void OnFailure() {

                    }
                }).build()
                .get();
    }
}
