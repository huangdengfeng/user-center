package com.seezoon.user.application.executor;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.seezoon.user.BaseSpringApplicationTest;
import com.seezoon.user.domain.repository.UserProfileRepository;
import com.seezoon.user.domain.repository.po.UserProfilePO;
import com.seezoon.user.server.domain.stub.QryUserProfileReq;
import com.seezoon.user.server.domain.stub.QryUserProfileResp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author dfenghuang
 * @date 2023/6/21 23:40
 */
class QryUserProfileExeTest extends BaseSpringApplicationTest {

    @Autowired
    private QryUserProfileExe qryUserProfileExe;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @BeforeEach
    void before() {
        UserProfilePO po = new UserProfilePO();
        po.setUid(1000L);
        po.setName("test");
        po.setMobile("");
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(LocalDateTime.now());
        when(userProfileRepository.find(anyLong())).thenReturn(po);
    }

    @Test
    void execute() {
        QryUserProfileReq req = QryUserProfileReq.newBuilder().setUid(1000L).build();
        QryUserProfileResp resp = qryUserProfileExe.execute(req);
        Assertions.assertEquals(req.getUid(), resp.getUserProfile().getUid());
    }
}