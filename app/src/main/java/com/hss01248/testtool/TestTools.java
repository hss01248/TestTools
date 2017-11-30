package com.hss01248.testtool;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.StrictMode;
import android.os.Trace;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fatangare.logcatviewer.utils.LogcatViewer;
import com.github.pedrovgs.lynx.LynxShakeDetector;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by huangshuisheng on 2017/10/13.
 */

public class TestTools {

    public  static boolean DEBUG ;
    private static Context context;

    public static void init(boolean isDebug,Context context){
        DEBUG = isDebug;
    }

    public static void startAll(){
        if(DEBUG){
            openStickModeIfIsDebug();
            initStetho();
            showLogcat();
        }
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

    public static void showLogcat(){
        if(DEBUG){
            new LynxShakeDetector(context).init();
            LogcatViewer.showLogcatLoggerView(context);
        }
    }


}
