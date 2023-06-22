package com.seezoon.interfaces;

import com.seezoon.application.SegmentService;
import com.seezoon.middleware.distributed.id.stub.DistributedIdService;
import com.seezoon.middleware.distributed.id.stub.GenDistributedIdReq;
import com.seezoon.middleware.distributed.id.stub.GenDistributedIdResp;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author dfenghuang
 * @date 2023/5/20 10:34
 */
@DubboService
@RequiredArgsConstructor
public class DistributedIdServiceImpl implements DistributedIdService {

    private final SegmentService segmentService;

    @Override
    public GenDistributedIdResp genDistributedId(GenDistributedIdReq request) {
        long id = segmentService.getId(request.getBizTag(), request.getToken());
        return GenDistributedIdResp.newBuilder().setValue(id).build();
    }

}
