package com.seezoon.user.interfaces;

import com.seezoon.user.application.executor.MobileRegisterExe;
import com.seezoon.user.server.domain.stub.MobileRegisterReq;
import com.seezoon.user.server.domain.stub.MobileRegisterResp;
import com.seezoon.user.server.domain.stub.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户账号服务
 *
 * @author dfenghuang
 * @date 2023/3/21 23:50
 */
@DubboService
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final MobileRegisterExe mobileRegisterExe;

    @Override
    public MobileRegisterResp mobileRegister(MobileRegisterReq req) {
        return mobileRegisterExe.execute(req);
    }
}
