package com.example.tinkertest.activity;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tinkertest.R;
import com.example.tinkertest.tinker.TinkerManager;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private static final String SUFFIX = ".apk";
    private String mPathDir;

    //Android6.0以上读写sd卡需要动态申请权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

        mPathDir = getExternalCacheDir().getAbsolutePath() + "/tpatch";
        File file = new File(mPathDir);
        if (file == null || !file.exists()) {
            file.mkdir();
        }
    }

    public void loadPatch(View view) {
        TinkerManager.loadPatch(getPatchName());
    }

    /**
     * 构造Patch文件名
     *
     * @return
     */
    private String getPatchName() {
        return mPathDir.concat("/hs").concat(SUFFIX);
    }

    /*
     * android 动态权限申请
     * */
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
