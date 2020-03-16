package com.example.fixlib.service;

import java.io.Serializable;

public class BasePatch implements Serializable {
    public int code;
    public String message;
    public PatchInfo data;
}
