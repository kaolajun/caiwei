package com.caiwei.yanjin.yuxue_core.app;

import android.content.Context;

import java.util.WeakHashMap;

public final class Yuxue {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static WeakHashMap<Object, Object> getConfigurations(){
        return Configurator.getInstance().getYuxueConfigs();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT.name());
    }
}
