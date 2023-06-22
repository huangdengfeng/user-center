package com.seezoon.user.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

public enum ErrorCode implements ErrorDefinition {

    /**
     * 通用错误
     */
    UNSPECIFIED(1100, "unspecified error"),

    PARAM_INVALID(1101, "param invalid"),

    SQL_ERROR(1102, "sql error"),

    CALL_REMOTE_RPC_ERROR(1103, "call remote error：%s"),

    /**
     * 业务错误
     */
    USER_NOT_EXISTS(2100, "用户不存在");
    private static final int prefix = 20000 * 1000;

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
