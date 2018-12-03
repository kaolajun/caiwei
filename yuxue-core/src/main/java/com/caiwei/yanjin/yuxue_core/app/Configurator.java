package com.caiwei.yanjin.yuxue_core.app;

import java.util.WeakHashMap;

//本类用于进行配置文件存储和获取,使用单例设计模式
public class Configurator{

    private static  final WeakHashMap<String,Object> YUXUE_CONFIGS = new WeakHashMap<>();
    private Configurator(){
        YUXUE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final WeakHashMap<String,Object> getYuxueConfigs(){
        return YUXUE_CONFIGS;
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        YUXUE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    public final Configurator withApiHost(String host){
        YUXUE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean) YUXUE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not Ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) YUXUE_CONFIGS.get(key.name());
    }
}
