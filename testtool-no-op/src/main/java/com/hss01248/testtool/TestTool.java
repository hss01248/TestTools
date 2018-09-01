package com.hss01248.testtool;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Trace;

import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by huangshuisheng on 2018/8/16.
 */

public class TestTool {

    private static boolean isDebug;
    private static Context context;

    public static boolean isDebug() {
        return isDebug;
    }

    public static Context getContext() {
        return context;
    }




    public static void init(Application app,boolean isDebug,boolean openLeakCanary){
        TestTool.context = context;
        TestTool.isDebug = isDebug;
        /*if(isDebug){
            app.registerActivityLifecycleCallbacks(new TestToolLifeCycleCallback());
            LeakCanary.install(app);
            Stetho.initializeWithDefaults(app);
        }*/
    }

    public static void addStethoInterceptorForOkhttp(OkHttpClient.Builder builder){

    }

    public static void addChuckInterceptorForOkhttp(OkHttpClient.Builder builder){
        if(isDebug){
            builder.addNetworkInterceptor(new ChuckInterceptor(context));
        }
    }

    /**
     * 控制Method Trace开始
     * 需要debug包
     * @param tag
     */
    public static void startTraceMethod(String tag){
        if(isDebug)
            Debug.startMethodTracing(tag);
    }

    public static void stopTraceMethod(){
        if(isDebug)
            Debug.stopMethodTracing();
    }

    /**
     * 仅用于SysTrace开启tag,不是用于控制SysTrace的开启,你也控制不了
     * 一定要配合命令行-a 使用,否则自定义tag无效
     * 需要debug包
     * Trace的begin与end必须在同一线程之中执行
     * @param tag
     */
    public static void startSysTraceSection(String tag){
        if(isDebug){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Trace.beginSection(tag);
            }
        }

    }

    public static void stopSysTraceSection(){
        if(isDebug){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Trace.endSection();
            }
        }

    }



}
