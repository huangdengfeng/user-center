package com.seezoon.user.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

/**
 * 如需细化错误，请在该文件中维护对外错误，小项目一般不需要
 */
public enum ErrorCode implements ErrorDefinition {


    UNKOWN(100, "system error：%s"),

    SQL_ERROR(101, "sql error：%s"),

    PARAM_INVALID(102, "param invalid：%s"),
    CALL_REMOTE_RPC_ERROR(103, "call remote error：%s"),
    /**
     * 业务错误
     */
    USER_STATUS_INVALID(206, "用户状态不正常"),

    UID_USED(201, "UID已注册");


    private static final int prefix = 21000 << 3;
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
