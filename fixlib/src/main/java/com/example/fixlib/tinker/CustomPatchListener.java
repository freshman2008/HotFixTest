package com.example.fixlib.tinker;

import android.content.Context;
import android.text.TextUtils;

import com.example.fixlib.util.Utils;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 1.校验patch文件是否合法 2.启动service去安装patch文件
 */
public class CustomPatchListener extends DefaultPatchListener {
    private String currentMD5;

    public void setCurrentMD5(String md5Value) {
        this.currentMD5 = md5Value;
    }

    public CustomPatchListener(Context context) {
        super(context);
    }

    @Override
    protected int patchCheck(String path, String patchMd5) {
        if (!TextUtils.isEmpty(patchMd5) && !TextUtils.isEmpty(currentMD5)) {
            if (!Utils.isFileMD5Matched(path, currentMD5)) {
                return ShareConstants.ERROR_PATCH_DISABLE;//可以自定义错误值
            }
        } else {
            return ShareConstants.ERROR_PATCH_DISABLE;
        }
        return super.patchCheck(path, patchMd5);
    }
}
