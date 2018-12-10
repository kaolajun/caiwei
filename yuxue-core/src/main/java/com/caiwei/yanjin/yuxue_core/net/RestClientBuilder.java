package com.caiwei.yanjin.yuxue_core.net;

import android.content.Context;

import com.caiwei.yanjin.yuxue_core.net.callback.IError;
import com.caiwei.yanjin.yuxue_core.net.callback.IFailure;
import com.caiwei.yanjin.yuxue_core.net.callback.IRequest;
import com.caiwei.yanjin.yuxue_core.net.callback.ISuccess;
import com.caiwei.yanjin.yuxue_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {

    private String mUrl;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest;
    private ISuccess mISuccess;
    private IError mIError;
    private IFailure mIFailure;
    private RequestBody mBody;
    private Context mContext;
    private File mFile;
    private LoaderStyle mLoaderStyle = null;

    private String mDownloadDir = null;
    private String mExension = null;
    private String mName = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params) {
        PARAMS.putAll(params);
        return this;
    }

//    //还可以用键值对的形式对params赋值
    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    //直接传入file对象
    public final RestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    //传入文件路径
    public final RestClientBuilder file(String file){
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mIError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    //在retrofit中不允许空的Map,在此执行检查
    private Map<String,Object> checkParams(){
        if (PARAMS == null){
            return new WeakHashMap<>();
        }
        return PARAMS;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle style){
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    //使用默认的loader
    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String exension){
        this.mExension = exension;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.mName = name;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISuccess,mIError
                ,mBody,mIFailure,mLoaderStyle,mContext,mFile
                ,mDownloadDir,mExension,mName);
    }
}