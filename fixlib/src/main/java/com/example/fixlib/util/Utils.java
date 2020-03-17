package com.example.fixlib.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.tinker.lib.util.TinkerLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private static final String TAG = "Utils";
    private static MessageDigest messageDigest = null;
    private static boolean background = false;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBackground() {
        return background;
    }

    public static void setBackground(boolean back) {
        background = back;
    }

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

    public static boolean isFileMD5Matched(String filePath, String originalMD5) {
        try {
            InputStream fis;
            fis = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead=fis.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, numRead);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] digest = messageDigest.digest();
        return originalMD5.equals(byteToHex(digest));
    }

    /**
     * byte数组转hex
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

//    private String bytesToHex(byte[] bytes) {
//        String hex = new BigInteger(1, bytes).toString(16);
//        return hex;
//    }

    // 监听屏幕状态
    public static class ScreenState {
        public interface IOnScreenOff {
            void onScreenOff();
        }
        public ScreenState(final Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    TinkerLog.i(TAG, "ScreenReceiver action [%s] ", action);
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                    }
                    context.unregisterReceiver(this);
                }
            }, filter);
        }
    }
}
