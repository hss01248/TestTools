package com.hss01248.testtool;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.os.StrictMode;
import android.os.Trace;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.pedrovgs.lynx.LynxActivity;
import com.github.pedrovgs.lynx.LynxConfig;
import com.github.pedrovgs.lynx.LynxShakeDetector;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;

import okhttp3.OkHttpClient;

/**
 * Created by huangshuisheng on 2017/10/13.
 */

public class TestTools {

    public  static boolean DEBUG ;
    private static Context context;

    public static void init(boolean isDebug,Context context){
        DEBUG = isDebug;
        TestTools.context = context;
        startAll();
    }

    private static void startAll(){

            openStickModeIfIsDebug();
            initStetho();
            MyLog.init(DEBUG);
            showLogcat();
            initLeakcanary();

    }

    private static void initLeakcanary() {
        ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
            .instanceField("android.view.inputmethod.InputMethodManager", "sInstance")
            .instanceField("android.view.inputmethod.InputMethodManager", "mLastSrvView")
            .instanceField("com.android.internal.policy.PhoneWindow$DecorView", "mContext")
            .instanceField("android.support.v7.widget.SearchView$SearchAutoComplete", "mContext")
            .build();

        LeakCanary.refWatcher(context)
            .listenerServiceClass(DisplayLeakService.class)
            .excludedRefs(excludedRefs)
            .buildAndInstall();
    }

    public static void openStickModeIfIsDebug(){
        if(DEBUG){
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
    }

    /**
     * 控制Method Trace开始
     * 需要debug包
     * @param tag
     */
    public static void startTraceMethod(String tag){
        if(DEBUG)
        Debug.startMethodTracing(tag);
    }

    public static void stopTraceMethod(){
        if(DEBUG)
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
        if(DEBUG){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Trace.beginSection(tag);
            }
        }

    }

    public static void stopSysTraceSection(){
        if(DEBUG){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Trace.endSection();
            }
        }

    }

    public static void initStetho(){
        if(DEBUG){
            //Stetho.initializeWithDefaults(application);
            Stetho.initialize(Stetho.newInitializerBuilder(context)
                    /*.enableDumpapp(new DumperPluginsProvider() {
                        @Override
                        public Iterable<DumperPlugin> get() {
                            return new Stetho.DefaultDumperPluginsBuilder(context)
                                    .provide(new MyDumperPlugin())
                                    .finish();
                        }
                    })*/
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                    .build());
        }
    }


    /**网络抓包:
     *
     * 添加Stetho的chrome抓包
     * 添加chuck的应用内抓包,显示在通知栏,ui很友好
     * @param builder
     */
    public static void addInterceptorForOkhttp(OkHttpClient.Builder builder){
        if(DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor())
                    . addInterceptor(new ChuckInterceptor(context));
        }
    }


    /**
     * https://github.com/pedrovgs/Lynx
     */
    public static void showLogcat(){
        if(DEBUG){
            LynxShakeDetector lynxShakeDetector = new LynxShakeDetector(context);
            lynxShakeDetector.init();

            openLynxActivity();
            //LogcatViewer.showLogcatLoggerView(context);
        }
    }



    private static void openLynxActivity() {
        LynxConfig lynxConfig = new LynxConfig();
        lynxConfig.setMaxNumberOfTracesToShow(4000)
            .setFilter("");

        Intent lynxActivityIntent = LynxActivity.getIntent(context, lynxConfig);
        lynxActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(lynxActivityIntent);
    }


}
