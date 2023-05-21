package com.seezoon.interfaces;

import com.seezoon.application.SegmentService;
import com.seezoon.protocol.middleware.distributed.id.DistributedIdCO;
import com.seezoon.protocol.middleware.distributed.id.DistributedIdCmd;
import com.seezoon.protocol.middleware.distributed.id.DistributedIdService;
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
    public DistributedIdCO genDistributedId(DistributedIdCmd request) {
        long id = segmentService.getId(request.getBizTag(), request.getToken());
        return DistributedIdCO.newBuilder().setValue(id).build();
    }
}
