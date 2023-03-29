package com.seezoon.user.infrastructure.error;

import com.seezoon.ddd.exception.ErrorDefinition;

/**
 * 如需细化错误，请在该文件中维护对外错误，小项目一般不需要
 */
public enum ErrorCode implements ErrorDefinition {

    UNKOWN(10000, "system error：%s"),

    SQL_ERROR(10001, "sql error：%s"),

    PARAM_INVALID(10002, "param invalid：%s"),
    CALL_REMOTE_RPC_ERROR(10003, "call remote error：%s"),
    /**
     * 业务错误
     */
    USER_STATUS_INVALID(20006, "用户状态不正常");


    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
