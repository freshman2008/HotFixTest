package com.example.fixlib.service;

import java.io.Serializable;

class PatchInfo implements Serializable {
    //不为空则表明有更新
    public String downloadUrl;

    //本次patch包的版本号
    public String versionName;

    //本次patch包含的相关信息，例如：主要做了哪些改动
    public String patchMessage;

    //patch文件的md5值
    public String md5;
}
