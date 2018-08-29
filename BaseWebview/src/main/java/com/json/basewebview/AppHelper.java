package com.json.basewebview;

import android.content.Context;
import android.content.IntentFilter;
import android.view.WindowManager;

import com.json.basewebview.NetFrame.NetStatusReceiver.NetworkStateReceiver;
import com.json.basewebview.widgets.CommonDialog;

/**
 * application的帮助类
 */

public class AppHelper {
    public static Context mContext;
    public static boolean isEnablaWifi;
    public static boolean isWifi;
    public static boolean isMobile;
    public static boolean isConnected;
    private static NetworkStateReceiver networkStateReceiver;

    public static void init(Context mContext) {
        AppHelper.mContext = mContext;
        registerNetworkState();
    }

    /**
     * 注册网络监听
     */

    private static void registerNetworkState() {
        networkStateReceiver = new NetworkStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        mContext.registerReceiver(networkStateReceiver, filter);
    }

    /**
     * WiFi状态
     *
     * @param isEnable
     */
    public static void setEnablaWifi(boolean isEnable) {
        AppHelper.isEnablaWifi = isEnable;
    }

    /**
     * 连接的是否是WiFi
     *
     * @param isWifi
     */
    public static void setWifi(boolean isWifi) {
        AppHelper.isWifi = isWifi;
    }

    /**
     * 连接的是否是移动数据
     *
     * @param isMobile
     */
    public static void setMobile(boolean isMobile) {
        AppHelper.isMobile = isMobile;
    }

    /**
     * 网络是否连接
     *
     * @param isConnected
     */
    public static void setConnected(boolean isConnected) {
        AppHelper.isConnected = isConnected;
        if (!isConnected){
            AppHelper.isWifi =false;
            AppHelper.isMobile=false;
        }

    }

    public static boolean isConnected() {
        return isWifi || isMobile;
    }

    public static boolean isMobile() {
        return isMobile;
    }

    public static boolean isWifi() {
        return isWifi;
    }

    /**
     * WiFi是否打开
     *
     * @return
     */
    public static  boolean isEnablaWifi() {
        return isEnablaWifi;
    }
}
