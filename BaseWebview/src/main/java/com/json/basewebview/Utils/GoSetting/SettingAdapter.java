package com.json.basewebview.Utils.GoSetting;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.learnrn_1.BuildConfig;

import java.util.List;

public class SettingAdapter {
    public  void toSetting(Context mContext){
        // SDK号
        String sdk = android.os.Build.VERSION.SDK;
        // 手机型号
        String model = android.os.Build.MODEL;
        // android系统版本号
        String release = android.os.Build.VERSION.RELEASE;
        //手机厂商
        String brand = Build.BRAND;
            //小米
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(mContext);
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(mContext);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(mContext);
        } else {
            mContext.startActivity(getAppDetailSettingIntent(mContext));
        }

    }
    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission(Context mContext) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", mContext.getPackageName());
            mContext.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", mContext.getPackageName());
                mContext.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                mContext.startActivity(getAppDetailSettingIntent(mContext));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission(Context mContext) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            mContext.startActivity(getAppDetailSettingIntent(mContext));
        }
    }

    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission(Context mContext) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            mContext.startActivity(getAppDetailSettingIntent(mContext));
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
    private Intent getAppDetailSettingIntent(Context mContext) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(mContext), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName(mContext));
        }
        return localIntent;
    }

    private String getPackageName(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getPackageName();
    }

}
