package com.seezoon.user.infrastructure.rpc;

import com.seezoon.protocol.user.server.domain.MobileRegisterCmd;
import com.seezoon.protocol.user.server.domain.UserService;
import javax.validation.constraints.NotEmpty;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 调用远程服务
 *
 * @author dfenghuang
 * @date 2023/3/25 23:24
 */
@Service
@Validated
public class UserServiceCaller {

    @DubboReference
    private UserService userService;

    public void mobileRegister(long uid, @NotEmpty String mobile, @NotEmpty String code) {
        MobileRegisterCmd cmd = MobileRegisterCmd.newBuilder().setUid(uid).setMobile(mobile).setCode(code).build();
        userService.mobileRegister(cmd);
    }
}
