package com.example.tinkertest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.tinkertest.R;
import com.example.tinkertest.tinker.TinkerManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String SUFFIX = ".apk";
    private String mPathDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
