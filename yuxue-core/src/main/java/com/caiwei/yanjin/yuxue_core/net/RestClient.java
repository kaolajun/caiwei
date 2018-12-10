package com.caiwei.yanjin.yuxue_core.net;

import android.content.Context;
import android.util.Log;

import com.caiwei.yanjin.yuxue_core.app.Yuxue;
import com.caiwei.yanjin.yuxue_core.net.callback.IError;
import com.caiwei.yanjin.yuxue_core.net.callback.IFailure;
import com.caiwei.yanjin.yuxue_core.net.callback.IRequest;
import com.caiwei.yanjin.yuxue_core.net.callback.ISuccess;
import com.caiwei.yanjin.yuxue_core.net.callback.RequestCallbacks;
import com.caiwei.yanjin.yuxue_core.ui.LoaderStyle;
import com.caiwei.yanjin.yuxue_core.ui.YuxueLoader;

import java.io.File;
import java.util.WeakHashMap;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;

public class RestClient {


    //每次RestClientBuilder构建一次就生成一个全新的RestClient实例
    //此类中的属性一旦声明就不可更改,用final修饰在多线程中比较安全
    private final String URL;
    private static final  WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final RequestBody BODY;
    private final IFailure FAILURE;
    //加载动画
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    //用于文件上传
    private final File FILE;

    public RestClient(String url
            , WeakHashMap<String, Object> params
            , IRequest request
            , ISuccess success
            , IError error
            , RequestBody body
            , IFailure failure
            ,LoaderStyle loaderStyle
            ,Context context
            ,File file) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FILE = file;
        this.BODY = body;
        this.FAILURE = failure;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null){
            REQUEST.onRequesetStart();
        }

        Log.i("加载1","y");
        //在加载开始时调用loader
        if (LOADER_STYLE != null){
            YuxueLoader.showLoading(CONTEXT,LOADER_STYLE);
            Log.i("加载2","y");
        }
        switch(method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;
        }

        if (call != null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                ERROR,
                FAILURE,
                LOADER_STYLE
        );
    }

    //具体使用方法
    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY == null) {
            request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }
}
