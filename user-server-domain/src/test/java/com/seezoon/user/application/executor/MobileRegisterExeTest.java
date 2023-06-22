package com.seezoon.user.application.executor;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.seezoon.user.BaseSpringApplicationTest;
import com.seezoon.user.domain.service.MobileRegisterService;
import com.seezoon.user.server.domain.stub.MobileRegisterReq;
import com.seezoon.user.server.domain.stub.MobileRegisterReq.Builder;
import com.seezoon.user.server.domain.stub.MobileRegisterResp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author dfenghuang
 * @date 2023/6/21 23:46
 */
class MobileRegisterExeTest extends BaseSpringApplicationTest {

    @Autowired
    private MobileRegisterExe mobileRegisterExe;
    @MockBean
    private MobileRegisterService mobileRegisterService;

    @BeforeEach
    void before() {
        when(mobileRegisterService.register(anyLong(), anyString())).thenReturn(1000L);
    }

    @Test
    void execute() {
        Builder builder = MobileRegisterReq.newBuilder();
        builder.setUid(1000L);
        builder.setMobile("mobile");
        builder.setCode("code");
        MobileRegisterResp resp = mobileRegisterExe.execute(builder.build());
        Assertions.assertEquals(builder.getUid(), resp.getUid());
    }
}