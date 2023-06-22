package com.seezoon.user.application.executor;

import com.seezoon.dubbo.utils.PbToJson;
import com.seezoon.user.domain.service.MobileRegisterService;
import com.seezoon.user.server.domain.stub.MobileRegisterReq;
import com.seezoon.user.server.domain.stub.MobileRegisterResp;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 手机号注册
 *
 * @author dfenghuang
 * @date 2023/3/21 23:52
 */
@Component
@Validated
@Slf4j
@RequiredArgsConstructor
public class MobileRegisterExe {

    private final MobileRegisterService mobileRegisterService;

    public MobileRegisterResp execute(@NotNull MobileRegisterReq req) {
        if (log.isDebugEnabled()) {
            log.debug("mobile register req:{}", PbToJson.toJson(req));
        }
        // TODO 远程服务校验短信
        // 注册
        long uid = mobileRegisterService.register(req.getUid(), req.getMobile());
        MobileRegisterResp resp = MobileRegisterResp.newBuilder().setUid(uid).build();
        if (log.isDebugEnabled()) {
            log.debug("mobile register resp:{}", PbToJson.toJson(resp));
        }
        return resp;
    }
}

