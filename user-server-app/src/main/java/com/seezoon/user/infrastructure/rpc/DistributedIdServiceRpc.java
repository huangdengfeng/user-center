package com.seezoon.user.infrastructure.rpc;

import com.seezoon.protocol.middleware.distributed.id.DistributedIdCO;
import com.seezoon.protocol.middleware.distributed.id.DistributedIdCmd;
import com.seezoon.protocol.middleware.distributed.id.DistributedIdService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author dfenghuang
 * @date 2023/5/21 22:27
 */
@Service
@Validated
public class DistributedIdServiceRpc {

    private static final String bigTag = "uid";
    @DubboReference
    private DistributedIdService distributedIdService;

    public long genDistributedId(String token) {
        DistributedIdCmd cmd = DistributedIdCmd.newBuilder().setBizTag(bigTag)
                .setToken(StringUtils.trimToEmpty(token)).build();
        DistributedIdCO distributedIdCO = distributedIdService.genDistributedId(cmd);
        if (distributedIdCO.getValue() <= 0) {
            throw new IllegalArgumentException("genDistributedId must > 0 ");
        }
        return distributedIdCO.getValue();
    }
}
