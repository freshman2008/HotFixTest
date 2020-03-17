package com.example.fixlib.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.fixlib.tinker.TinkerManager;
import com.example.fixlib.util.OnCommonResponse;
import com.example.fixlib.util.OnDownloadListener;
import com.example.fixlib.util.Utils;

import java.io.File;

public class MyService extends IntentService {
    private String mPatchFileDir;
    private String mPatchFile;
    private static final String SUFFIX = ".apk";
    private Context mContext;
    private BasePatch mBasePatchInfo;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyService() {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        init();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        checkPatchUpdate();
    }

    /**
     * 完成文件目录的构造
     */
    public void init() {
        Context context=getApplicationContext();
        mPatchFileDir = context.getExternalCacheDir().getAbsolutePath() + "/tpatch";
        File patchDir = new File(mPatchFileDir);
        try {
            if (patchDir == null || !patchDir.exists()) {
                patchDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查服务器是否有patch文件
     */
    private void checkPatchUpdate() {
        RequestCenter.requestPatchUpdateInfo(new OnCommonResponse() {
            @Override
            public void onSuccess(String data) {
                mBasePatchInfo = JSON.parseObject(data, BasePatch.class);
                if (!mBasePatchInfo.data.versionName.equals(Utils.getVersionName(mContext))) {
                    Log.i("hello", " patch版本与应用版本不同.");
                    return;
                }
                if (!TextUtils.isEmpty(mBasePatchInfo.data.downloadUrl)) {
                    Log.i("hello", " 开始下载patch文件, url:" + mBasePatchInfo.data.downloadUrl);
                    downloadPatch(mBasePatchInfo.data.downloadUrl);
                    Log.i("hello", " patch文件下载完成.");
                }
            }

            @Override
            public void onFailure(String data) {
                Log.i("hello", "requestPatchUpdateInfo failed.");
            }
        });
    }

    private void downloadPatch(String url) {
//        mPatchFile = mPatchFileDir.concat(String.valueOf(System.currentTimeMillis())).concat(SUFFIX);
//        String pathName = String.valueOf(System.currentTimeMillis()).concat(SUFFIX);
        String pathName = url.substring(url.lastIndexOf("/")+1);
        RequestCenter.downloadFile(url, mPatchFileDir, pathName, new OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Log.i("hello", "加载补丁包:" + file.getAbsolutePath());
                mPatchFile = file.getAbsolutePath();
//                AndFixPatchManager.getInstance().addPatch(file.getAbsolutePath());
                TinkerManager.loadPatch(mPatchFile, mBasePatchInfo.data.md5);
            }

            @Override
            public void onDownloading(int progress) {
                Log.i("hello", "current progress:" + progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                Log.i("hello", "downloadPatch failed.");
            }
        });
    }
}
