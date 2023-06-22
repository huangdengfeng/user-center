package com.seezoon.user.interfaces;

import com.seezoon.user.application.executor.QryUserProfileExe;
import com.seezoon.user.server.domain.stub.QryUserProfileReq;
import com.seezoon.user.server.domain.stub.QryUserProfileResp;
import com.seezoon.user.server.domain.stub.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户信息服务
 *
 * @author dfenghuang
 * @date 2023/6/15 23:48
 */
@DubboService
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final QryUserProfileExe qryUserProfileExe;

    @Override
    public QryUserProfileResp qryUserProfile(QryUserProfileReq req) {
        return qryUserProfileExe.execute(req);
    }
}
