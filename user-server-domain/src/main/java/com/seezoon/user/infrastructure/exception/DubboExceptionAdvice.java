package com.seezoon.user.infrastructure.exception;

import com.seezoon.ddd.dto.Response;
import com.seezoon.ddd.exception.BizException;
import com.seezoon.ddd.exception.SysException;
import com.seezoon.dubbo.advice.DubboAdvice;
import com.seezoon.dubbo.advice.DubboExceptionHandler;
import com.seezoon.dubbo.utils.ErrorCodeUtil;
import com.seezoon.user.infrastructure.error.ErrorCode;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;

/**
 * 全局异常处理
 *
 * @author dfenghuang
 * @date 2023/3/22 09:30
 */
@DubboAdvice
@Slf4j
public class DubboExceptionAdvice {

    @DubboExceptionHandler({IllegalArgumentException.class, ValidationException.class})
    public void illegalArgumentException(Exception e) {
        log.error("illegal argument", e);
        throw new RpcException(ErrorCode.PARAM_INVALID.code(),
                String.format(ErrorCode.PARAM_INVALID.msg(), e.getMessage()));
    }

    @DubboExceptionHandler(BizException.class)
    public void bizException(BizException e) {
        throw new RpcException(e.getcode(), e.getMessage());
    }

    @DubboExceptionHandler(SysException.class)
    public void sysException(SysException e) {
        log.error("sys exception", e);
        throw new RpcException(e.getcode(), e.getMessage());
    }

    /**
     * using this if there is no match.
     *
     * @param e
     * @return
     */
    @DubboExceptionHandler(RpcException.class)
    public Response rpcException(RpcException e) {
        if (ErrorCodeUtil.isFrame(e.getCode())) {
            log.error("call remote rpc exception", e);
            throw new RpcException(ErrorCode.CALL_REMOTE_RPC_ERROR.code(),
                    String.format(ErrorCode.CALL_REMOTE_RPC_ERROR.msg(), "[" + e.getCode() + "]" + e.getMessage()));
        }
        // 透传下游的错误
        throw e;
    }

    /**
     * using this if there is no match.
     *
     * @param e
     * @return
     */
    @DubboExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.error("unspecified exception", e);
        throw new RpcException(ErrorCode.UNKOWN.code(), String.format(ErrorCode.UNKOWN.msg(), e.getMessage()));
    }
}
