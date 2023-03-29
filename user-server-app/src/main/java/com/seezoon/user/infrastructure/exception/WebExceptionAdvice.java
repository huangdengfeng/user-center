package com.seezoon.user.infrastructure.exception;

import com.seezoon.ddd.dto.Response;
import com.seezoon.ddd.exception.BizException;
import com.seezoon.ddd.exception.SysException;
import com.seezoon.dubbo.utils.ErrorCodeUtil;
import com.seezoon.user.infrastructure.error.ErrorCode;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * web exception advice
 *
 * @author huangdengfeng
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionAdvice {

    /**
     * for Receiving parameters and verification
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, ValidationException.class,
            MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, BindException.class})
    public Response parameterInvalidException(Exception e) {
        return Response.error(ErrorCode.PARAM_INVALID.code(), e.getMessage());
    }

    /**
     * for using {@link org.springframework.util.Assert}
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public Response illegalArgumentException(IllegalArgumentException e) {
        return Response.error(ErrorCode.PARAM_INVALID.code(), e.getMessage());
    }


    @ExceptionHandler(BizException.class)
    public Response bizException(BizException e) {
        return Response.error(e.getcode(), e.getMessage());
    }

    @ExceptionHandler(SysException.class)
    public Response sysException(SysException e) {
        log.error("sys exception", e);
        return Response.error(e.getcode(), e.getMessage());
    }


    @ExceptionHandler(RpcException.class)
    public Response rpcException(RpcException e) {
        if (ErrorCodeUtil.isFrame(e.getCode())) {
            log.error("call remote rpc exception", e);
            return Response.error(ErrorCode.CALL_REMOTE_RPC_ERROR.code(),
                    String.format(ErrorCode.CALL_REMOTE_RPC_ERROR.msg(),
                            "[" + e.getCode() + "]" + e.getMessage()));
        }
        // 透传下游业务错误
        return Response.error(e.getCode(), e.getMessage());
    }

    /**
     * using this if there is no match.
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.error("unspecified exception", e);
        return Response.error(ErrorCode.UNSPECIFIED.code(), e.getMessage());
    }
}
