package com.seezoon.user.application.executor;

import com.seezoon.ddd.dto.Response;
import com.seezoon.user.application.dto.MobileRegisterCmd;
import com.seezoon.user.infrastructure.rpc.DistributedIdServiceRpc;
import com.seezoon.user.infrastructure.rpc.UserServiceRpc;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 手机号注册
 *
 * @author dfenghuang
 * @date 2023/3/25 23:00
 */
@Component
@Validated
@Slf4j
@RequiredArgsConstructor
public class MobileRegisterCmdExe {

    private final DistributedIdServiceRpc distributedIdServiceRpc;
    private final UserServiceRpc userServiceRpc;

    public Response execute(@NotNull @Valid MobileRegisterCmd cmd) {
        // 生成UID
        long uid = distributedIdServiceRpc.genDistributedId(null);
        // UID路由后注册到对应区域
        userServiceRpc.mobileRegister(uid, cmd.getMobile(), cmd.getCode());
        return Response.success();
    }
}
