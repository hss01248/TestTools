package com.hss01248.testtool;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.LogBuilder;
import com.orhanobut.logger.LogPrintStyle;
import com.orhanobut.logger.Logger;

/**
 * by huangshuisheng
 */
public class MyLog {

    private   static boolean DEBUG = true;

    public static void init(boolean isDebug){
        DEBUG  = isDebug;
        initLogger();


    }

    /**
     * https://github.com/tianzhijiexian/LogDelegate
     */
    private static void initLogger() {

        Logger.initialize(
            new LogBuilder()
                .logPrintStyle(new LogPrintStyle())
                .showMethodLink(true)
                .showThreadInfo(true)
                .tagPrefix("")
                .globalTag("logger")
                .methodOffset(1)
                .logPriority(DEBUG ? Log.VERBOSE : Log.ERROR)
                .build());
    }

   

    public static void d(String tag, Object message) {
        if (DEBUG){
            Logger.d(tag,message);
        }

    }

    public static void i(String tag, Object message) {
        if (DEBUG)
            Logger.i(tag,message);
    }

    public static void w(String tag, Object message) {
        if (DEBUG)
            Logger.w(tag,message);
    }

    public static void e(String tag, Object message) {
        if (DEBUG)
            Logger.e(tag,message);
    }

    public static void v(String tag, Object message) {
        if (DEBUG)
            Logger.v(tag,message);
    }

    public static void d(String message) {
        if (DEBUG)
            Logger.e( message);
    }

    public static void i(String message) {
        if (DEBUG)
            Logger.i( message);
    }

    public static void w(String message) {
        if (DEBUG)
            Logger.w( message);
    }

    public static void e(String message) {
        if (DEBUG)
            Logger.e( message);
    }

    public static void v(String message) {
        if (DEBUG)
            Logger.v( message);
    }

    public static void json(String obj){
        if(DEBUG){
            Logger.json(obj);
        }
    }
    public static void xml(String xml){
        if(DEBUG)
            Logger.xml(TextUtils.isEmpty(xml)?"":xml);
    }

    public static void obj(Object obj){
        if(DEBUG){
            Logger.object(obj);
        }

    }

    public static void format(String format, Object... objects){
        if(DEBUG){
            String msg = String.format(format,objects);
            Logger.e(msg);
        }

    }


}
