package com.seezoon.user.infrastructure.rpc;

import com.seezoon.middleware.distributed.id.stub.DistributedIdService;
import com.seezoon.middleware.distributed.id.stub.GenDistributedIdReq;
import com.seezoon.middleware.distributed.id.stub.GenDistributedIdResp;
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
        GenDistributedIdReq req = GenDistributedIdReq.newBuilder().setBizTag(bigTag)
                .setToken(StringUtils.trimToEmpty(token)).build();
        GenDistributedIdResp resp = distributedIdService.genDistributedId(req);
        if (resp.getValue() <= 0) {
            throw new IllegalArgumentException("genDistributedId must > 0 ");
        }
        return resp.getValue();
    }
}
