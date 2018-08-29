package com.jone.devspace;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.json.basewebview.AppHelper;

/**
 * Created by user on 2018/8/29.
 */

public class DevspaceApp extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess(android.os.Process.myPid())) {
            mContext = getApplicationContext();
            //Moudle BaseWebView的初始化
            AppHelper.init(this);
        }
    }

    /**
     * 判断当前进程是否为主进程
     * @param pid
     * @return
     */
    private boolean isMainProcess(int pid) {
        String processNameString = "";
        ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processNameString = appProcess.processName;
            }
        }
        if (getPackageName().equals(processNameString)) {
            return true;
        }
        return false;
    }
}
