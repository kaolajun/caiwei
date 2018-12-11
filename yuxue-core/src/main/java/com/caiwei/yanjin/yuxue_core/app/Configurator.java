package com.caiwei.yanjin.yuxue_core.app;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

//本类用于进行配置文件存储和获取,使用单例设计模式
public class Configurator{

    public static  final WeakHashMap<Object, Object> YUXUE_CONFIGS = new WeakHashMap<Object, Object>();
    private Configurator(){
        YUXUE_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
    }
    //用于拦截器
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final WeakHashMap<Object, Object> getYuxueConfigs(){
        return YUXUE_CONFIGS;
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        YUXUE_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }

    public final Configurator withApiHost(String host){
        YUXUE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        YUXUE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        YUXUE_CONFIGS.put(ConfigKeys.INTERCEPTOR,interceptors);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean) YUXUE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady){
            throw new RuntimeException("Configuration is not Ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = YUXUE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) YUXUE_CONFIGS.get(key);
    }


}
