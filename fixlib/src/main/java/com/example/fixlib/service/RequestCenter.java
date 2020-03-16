package com.example.fixlib.service;

import com.example.fixlib.util.MyOkHttpClient;
import com.example.fixlib.util.OnCommonResponse;
import com.example.fixlib.util.OnDownloadListener;

public class RequestCenter {
    private static final String UPDATE_PATCH_URL = "http://192.168.8.66:3000/users/getPatchInfo";
//    private static final String DOWNLOAD_PATCH_URL = "http://192.168.8.67:3000/images/1.png";

    public static void requestPatchUpdateInfo(OnCommonResponse listener) {
        MyOkHttpClient.getAsync(UPDATE_PATCH_URL, listener);
    }

    public static void downloadFile(String url, String path, String fileName, OnDownloadListener listener) {
        MyOkHttpClient.download(url, path, fileName, listener);
    }
}
