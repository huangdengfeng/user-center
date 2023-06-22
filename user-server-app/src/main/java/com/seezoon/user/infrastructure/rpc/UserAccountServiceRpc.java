package com.seezoon.user.infrastructure.rpc;

import com.seezoon.user.infrastructure.constants.Constants;
import com.seezoon.user.server.domain.stub.MobileRegisterReq;
import com.seezoon.user.server.domain.stub.MobileRegisterResp;
import com.seezoon.user.server.domain.stub.UserAccountService;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContextAttachment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

/**
 * 调用远程服务
 *
 * @author dfenghuang
 * @date 2023/3/25 23:24
 */
@Service
@Validated
public class UserAccountServiceRpc {

    @DubboReference
    private UserAccountService userAccountService;

    public long mobileRegister(long uid, @NotEmpty String mobile, @NotEmpty String code) {
        RpcContextAttachment.getClientAttachment().setAttachment(Constants.USER_ROUTE_HEADER_KEY, uid);
        MobileRegisterReq cmd = MobileRegisterReq.newBuilder().setUid(uid).setMobile(mobile).setCode(code).build();
        MobileRegisterResp co = userAccountService.mobileRegister(cmd);
        Assert.isTrue(Objects.equals(cmd.getUid(), cmd.getUid()));
        return co.getUid();
    }
}
