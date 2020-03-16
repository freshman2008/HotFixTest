package com.example.fixlib.util;

public interface OnCommonResponse{
    /**
     * 下载成功之后的文件
     */
    void onSuccess(String data);

    void onFailure(String data);
}