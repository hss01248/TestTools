package com.hss01248.testtool;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.StrictMode;
import android.os.Trace;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.squareup.leakcanary.LeakCanary;
import me.ele.uetool.UETool;
import okhttp3.OkHttpClient;

/**
 * 已集成:
 * 1.UETool:查看view和顶层activity工具
 * 2.leakcanary内存泄漏查看工具
 * 3.stecho 网络,sp,数据库查看工具. 使用:  运行App, 打开Chrome输入chrome://inspect/#devices（别忘了用数据线把手机和电脑连起来哦）
 *
 * Created by huangshuisheng on 2018/8/16.
 *
 * 备注: stecho并没有提供no op的包,解决方法如下:todo...
 * https://stackoverflow.com/questions/30172308/include-stetho-only-in-the-debug-build-variant/30172663#30172663
 * https://github.com/facebook/stetho/blob/master/stetho-sample/src/debug/java/com/facebook/stetho/sample/SampleDebugApplication.java
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
        TestTool.context = app;
        TestTool.isDebug = isDebug;
        if(!isDebug){
            return;
        }

        UETool.init(app);
        if(openLeakCanary){
            LeakCanary.install(app);
        }

        //Stetho.initializeWithDefaults(app);
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                .build());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()//开启所有的detectXX系列方法
                //.penaltyDialog()//弹出违规提示框
                .penaltyLog()//在Logcat中打印违规日志
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

    }

    public static void addStethoInterceptorForOkhttp(OkHttpClient.Builder builder){
        if(isDebug){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
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
