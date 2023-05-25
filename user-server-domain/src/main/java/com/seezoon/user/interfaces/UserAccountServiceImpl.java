package com.seezoon.user.interfaces;

import com.google.protobuf.Empty;
import com.seezoon.protocol.user.server.domain.MobileRegisterCmd;
import com.seezoon.protocol.user.server.domain.UserAccountService;
import com.seezoon.user.application.executor.MobileRegisterCmdExe;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户服务
 *
 * @author dfenghuang
 * @date 2023/3/21 23:50
 */
@DubboService
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final MobileRegisterCmdExe mobileRegisterCmdExe;

    @Override
    public Empty mobileRegister(MobileRegisterCmd request) {
        return mobileRegisterCmdExe.execute(request);
    }
}
