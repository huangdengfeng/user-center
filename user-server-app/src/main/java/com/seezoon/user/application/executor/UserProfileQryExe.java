package com.seezoon.user.application.executor;

import com.seezoon.ddd.dto.Response;
import com.seezoon.dubbo.utils.PbTimestampUtil;
import com.seezoon.user.application.dto.UserProfileCO;
import com.seezoon.user.application.dto.UserProfileQry;
import com.seezoon.user.infrastructure.error.ErrorCode;
import com.seezoon.user.infrastructure.rpc.UserInfoServiceRpc;
import com.seezoon.user.server.domain.stub.UserProfile;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 用户资料查询
 *
 * @author dfenghuang
 * @date 2023/6/22 00:21
 */
@Component
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserProfileQryExe {

    private final UserInfoServiceRpc userInfoServiceRpc;

    public Response<UserProfileCO> execute(@Valid @NotNull UserProfileQry qry) {
        UserProfile userProfile = userInfoServiceRpc.qryUserProfile(qry.getUid());
        if (null == userProfile) {
            log.error("uid:{} profile not exists");
            return Response.error(ErrorCode.USER_NOT_EXISTS.code(), ErrorCode.USER_NOT_EXISTS.msg());
        }

        UserProfileCO co = new UserProfileCO();
        co.setName(userProfile.getName());
        co.setMobile(userProfile.getMobile());
        co.setAvatar(userProfile.getAvatar());
        co.setEmail(userProfile.getEmail());
        co.setBirthday(null);
        co.setAddress(userProfile.getAddress());
        co.setCreateTime(PbTimestampUtil.toLocalDateTime(userProfile.getCreateTime()));
        return Response.success(co);
    }
}
