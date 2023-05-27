package com.seezoon.infrastructure.exception;

import com.seezoon.ddd.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {


    UNKOWN(100, "system error：%s"),

    PARAM_INVALID(101, "param invalid：%s"),


    // 逻辑错误
    BIZ_TAG_NOT_EXISTS(200, "biz tag not exists"),

    SEGMENT_NOT_READY(201, "segment not ready"),

    AUTH_FAILED(203, "auth failed");

    private static final int prefix = 90000 << 3;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return prefix() + code;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public int prefix() {
        return prefix;
    }
}
