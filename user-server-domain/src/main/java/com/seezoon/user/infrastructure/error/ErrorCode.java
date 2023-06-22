package com.seezoon.user.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

/**
 * 如需细化错误，请在该文件中维护对外错误，小项目一般不需要
 */
public enum ErrorCode implements ErrorDefinition {


    UNKOWN(1100, "system error：%s"),
    SQL_ERROR(1101, "sql error：%s"),
    PARAM_INVALID(1102, "param invalid：%s"),
    CALL_REMOTE_RPC_ERROR(1103, "call remote error：%s"),
    USER_STATUS_INVALID(2206, "用户状态不正常"),
    UID_USED(2201, "UID已注册");


    private static final int prefix = 21000 * 1000;
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
