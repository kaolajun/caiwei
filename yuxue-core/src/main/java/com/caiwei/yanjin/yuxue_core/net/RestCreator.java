package com.caiwei.yanjin.yuxue_core.net;

import com.caiwei.yanjin.yuxue_core.app.ConfigKeys;
import com.caiwei.yanjin.yuxue_core.app.Configurator;
import com.caiwei.yanjin.yuxue_core.app.Yuxue;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    //params不必每次都修改，所以声明为静态常量
    private static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String, Object> getParams(){
        return ParamsHolder.PARAMS;
    }
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RetrofitHolder{
        //自己请求自己，完成自举
        private static final String BASE_URL = (String) Yuxue.getConfigurations().get(ConfigKeys.API_HOST.name());
        //简化的建造者模式,这里建立一个request
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())  //转换器返回String类型
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS =Yuxue.getConfiguration(ConfigKeys.INTERCEPTOR);
        //通过循环把拦截器传入OKHttp
        private static  OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS){
                }
            }
            return BUILDER;
        }

        //这里也是建造者模式
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        //通过retrofit和定义的有网络访问方法的接口关联
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

}
