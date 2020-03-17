package com.example.fixlib.tinker;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * Tinker集成第二步，定义TinkerManager封装Tinker初始化方法等
 * 对tinker所有内部api做一层封装
 */
public class TinkerManager {
    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;
    private static CustomPatchListener mPatchListener;

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

        mPatchListener = new CustomPatchListener(getApplicationContext());
        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());

        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        //完成tinker初始化
        TinkerInstaller.install(
                mAppLike,
                loadReporter,
                patchReporter,
                mPatchListener,
                CustomResultService.class,
                upgradePatchProcessor);
        isInstalled = true;
    }

    /**
     * 完成Patch文件的加载
     * @param path
     */
    public static void loadPatch(String path, String md5Value) {
        if (Tinker.isTinkerInstalled()) {
            mPatchListener.setCurrentMD5(md5Value);
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 通过ApplicationLike获取Context
     * @return
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication();//.getApplicationContext();//getApplicationContext 返回 null,也可参考https://blog.csdn.net/ybf326/article/details/89950015
        }
        return null;
    }
}
