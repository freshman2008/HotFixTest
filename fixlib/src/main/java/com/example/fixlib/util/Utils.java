package com.example.fixlib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Utils {
    public static String getVersionName(Context context) {
        String versionNumber = "1.0.0";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionNumber = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionNumber;
    }
}
