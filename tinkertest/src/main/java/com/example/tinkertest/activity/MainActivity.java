package com.example.tinkertest.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.fixlib.service.MyService;
import com.example.fixlib.tinker.TinkerManager;
import com.example.tinkertest.R;

import java.io.File;

import cn.jpush.android.api.JPushInterface;


public class MainActivity extends AppCompatActivity {
    private static final String SUFFIX = ".apk";
    private String mPathDir;
    public static Handler mHandler = null;

    //Android6.0以上读写sd卡需要动态申请权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initJPush();
        verifyStoragePermissions(this);
//        startPatchService();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1001) {
                    Log.d("hello", "startPatchService");
                    startPatchService();
                }
            }
        };
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public void loadPatch(View view) {
        mPathDir = getExternalCacheDir().getAbsolutePath() + "/tpatch";
        File file = new File(mPathDir);
        if (file == null || !file.exists()) {
            file.mkdir();
        }
        TinkerManager.loadPatch(mPathDir.concat("/hs").concat(SUFFIX));
    }

    public void startPatchService() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
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
