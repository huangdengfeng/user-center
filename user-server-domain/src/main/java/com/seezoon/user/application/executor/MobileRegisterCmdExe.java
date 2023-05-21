package com.seezoon.user.application.executor;

import com.google.protobuf.Empty;
import com.seezoon.dubbo.utils.PbToJson;
import com.seezoon.protocol.user.server.domain.MobileRegisterCmd;
import com.seezoon.user.domain.service.MobileRegisterService;
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
public class MobileRegisterCmdExe {

    private final MobileRegisterService mobileRegisterService;

    public Empty execute(@NotNull MobileRegisterCmd cmd) {
        if (log.isDebugEnabled()) {
            log.debug("mobile register cmd:{}", PbToJson.toJson(cmd));
        }
        // TODO 远程服务校验短信

        // 注册
        mobileRegisterService.register(cmd.getUid(), cmd.getMobile());
        return Empty.newBuilder().build();
    }
}

