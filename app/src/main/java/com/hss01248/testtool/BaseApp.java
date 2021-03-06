package com.hss01248.testtool;

import android.app.Application;
import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.IJsonToStr;
import com.orhanobut.logger.MyLog;

/**
 * Created by huangshuisheng on 2017/12/12.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.init(true, "mylog", 1,new IJsonToStr() {
            @Override
            public String toStr(Object o) {
                return JSON.toJSONString(o);
            }
        });
        //TestTools.init(true,this);
        TestTool.init(this,true,false);

    }
}
