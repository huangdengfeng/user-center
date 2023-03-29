package com.seezoon.user.application.executor;

import com.google.protobuf.Empty;
import com.seezoon.dubbo.utils.PbToJson;
import com.seezoon.protocol.user.server.domain.MobileRegisterCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 手机号注册
 *
 * @author dfenghuang
 * @date 2023/3/21 23:52
 */
@Slf4j
@Component
@Validated
public class MobileRegisterCmdExe {

    public Empty execute(MobileRegisterCmd cmd) {
        log.info("regist success cmd:{}", PbToJson.toJson(cmd));
        return Empty.newBuilder().build();
    }
}

