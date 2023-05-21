package com.seezoon.application;

import com.seezoon.ddd.exception.BizException;
import com.seezoon.domain.service.IDGen;
import com.seezoon.infrastructure.configuration.SegmentProperties;
import com.seezoon.infrastructure.exception.ErrorCode;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class SegmentService {

    private final IDGen idGen;
    private final SegmentProperties segmentProperties;


    public long getId(@NotEmpty String bizTag, String token) {
        Assert.notNull(bizTag, "biz tag must not null");
        this.auth(bizTag, token);
        return this.getId(bizTag);
    }

    public long getId(@NotEmpty String bizTag) {
        Assert.notNull(bizTag, "biz tag must not null");
        return idGen.get(bizTag);
    }

    private void auth(String bizTag, String token) {
        Assert.notNull(bizTag, "biz tag must not null");
        if (!segmentProperties.isEnableAuth()) {
            return;
        }

        if (!Objects.equals(idGen.getToken(bizTag), token)) {
            throw new BizException(ErrorCode.AUTH_FAILED.code(), ErrorCode.AUTH_FAILED.msg());
        }
    }

    @PostConstruct
    public void init() {
        if (segmentProperties.isInitGet()) {
            for (String tag : idGen.getTags()) {
                long value = idGen.get(tag);
                log.info("init get biztag:{},value:{}", tag, value);
            }
        }
    }
}
