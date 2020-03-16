package com.example.fixlib.tinker;

import android.content.Context;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * Tinker集成第二步，定义TinkerManager封装Tinker初始化方法等
 * 对tinker所有内部api做一层封装
 */
public class TinkerManager {
    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;

    /**
     * 完成tinker初始化
     *
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }
        //完成tinker初始化
        TinkerInstaller.install(mAppLike);
        isInstalled = true;
    }

    /**
     * 完成Patch文件的加载
     * @param path
     */
    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 通过ApplicationLike获取Context
     * @return
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}
