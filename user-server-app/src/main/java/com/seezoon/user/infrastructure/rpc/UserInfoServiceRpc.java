package com.seezoon.user.infrastructure.rpc;

import com.seezoon.user.server.domain.stub.QryUserProfileReq;
import com.seezoon.user.server.domain.stub.QryUserProfileResp;
import com.seezoon.user.server.domain.stub.UserInfoService;
import com.seezoon.user.server.domain.stub.UserProfile;
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
public class UserInfoServiceRpc {

    @DubboReference
    private UserInfoService userInfoService;

    public UserProfile qryUserProfile(long uid) {
        QryUserProfileReq req = QryUserProfileReq.newBuilder().setUid(uid).build();
        QryUserProfileResp resp = userInfoService.qryUserProfile(req);
        return resp.hasUserProfile() ? resp.getUserProfile() : null;
    }
}
