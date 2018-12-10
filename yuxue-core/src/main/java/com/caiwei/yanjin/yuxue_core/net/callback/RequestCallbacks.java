package com.caiwei.yanjin.yuxue_core.net.callback;

import android.os.Handler;

import com.caiwei.yanjin.yuxue_core.app.Yuxue;
import com.caiwei.yanjin.yuxue_core.ui.LoaderStyle;
import com.caiwei.yanjin.yuxue_core.ui.YuxueLoader;


import me.yokeyword.fragmentation.SupportActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallbacks implements Callback<String>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoaderStyle LOADER_STYLE;
    //handler声明成static避免内存泄漏
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request, ISuccess success, IError error, IFailure failure,LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.OnSuccess(response.body());
                }
            }
        }else {
            if (ERROR != null){
                ERROR.onError(response.code(),response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null){
            FAILURE.OnFailure();
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }
        stopLoading();
    }

    private void stopLoading(){
        //网络加载结束
        if (LOADER_STYLE != null){
            //加入一秒钟的延迟，使观察更清楚
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    YuxueLoader.stopLoading();
                }
            },1000);
        }
    }
}
