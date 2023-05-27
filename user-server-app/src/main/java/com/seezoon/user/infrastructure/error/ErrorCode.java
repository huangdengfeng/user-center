package com.seezoon.user.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {

    /**
     * 通用错误
     */
    UNSPECIFIED(100, "unspecified error"),

    PARAM_INVALID(101, "param invalid"),

    SQL_ERROR(102, "sql error"),

    CALL_REMOTE_RPC_ERROR(103, "call remote error：%s"),

    /**
     * 业务错误
     */
    USER_NOT_EXISTS(200, "用户不存在");
    private static final int prefix = 20000 << 3;

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
