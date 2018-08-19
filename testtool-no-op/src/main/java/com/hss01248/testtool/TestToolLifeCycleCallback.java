package com.hss01248.testtool;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by huangshuisheng on 2018/8/16.
 */

public class TestToolLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

    //UETool使用
    private int visibleActivityCount;
    private int uetoolDismissY = -1;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(TestTool.isDebug()){
            uetoolDismissY = 10;
            visibleActivityCount++;
            if (visibleActivityCount == 1 && uetoolDismissY >= 0) {
               // UETool.showUETMenu(uetoolDismissY);
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(TestTool.isDebug()){
            visibleActivityCount--;
            if (visibleActivityCount == 0) {
               // uetoolDismissY = UETool.dismissUETMenu();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
