package com.caiwei.yanjin.yuxue_core.net.download;

import android.os.AsyncTask;

import com.caiwei.yanjin.yuxue_core.net.RestCreator;
import com.caiwei.yanjin.yuxue_core.net.callback.IError;
import com.caiwei.yanjin.yuxue_core.net.callback.IFailure;
import com.caiwei.yanjin.yuxue_core.net.callback.IRequest;
import com.caiwei.yanjin.yuxue_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {

    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    //用于文件下载的地址、后缀名
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public DownloadHandler(String url
                        , IRequest request
                        , ISuccess success
                        , IError error
                        , IFailure failure
                        , String downloadDir
                        , String extension
                        , String name) {
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public final void handleDownload(){
       if (REQUEST != null){
           //开始下载
           REQUEST.onRequesetStart();
       }
        //这里使用异步的方法（enqueue），对应restservice中的@Streaming
       RestCreator.getRestService().download(URL,PARAMS).enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    final ResponseBody responseBody = response.body();

                    final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,responseBody,NAME);

                    //这里要注意判断，否则文件下载不全
                    if (task.isCancelled()){
                        if (REQUEST != null){
                            REQUEST.onRequestEnd();
                        }
                    }else {
                        if (ERROR != null){
                            ERROR.onError(response.code(),response.message());
                        }
                    }
                }

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (FAILURE != null){
                    FAILURE.onFailure();
                }
           }
       });
    }
}
