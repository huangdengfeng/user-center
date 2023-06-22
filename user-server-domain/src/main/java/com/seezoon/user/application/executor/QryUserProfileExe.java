package com.seezoon.user.application.executor;

import com.google.protobuf.Timestamp;
import com.seezoon.dubbo.utils.PbTimestampUtil;
import com.seezoon.dubbo.utils.PbToJson;
import com.seezoon.user.domain.repository.UserProfileRepository;
import com.seezoon.user.domain.repository.po.UserProfilePO;
import com.seezoon.user.server.domain.stub.QryUserProfileReq;
import com.seezoon.user.server.domain.stub.QryUserProfileResp;
import com.seezoon.user.server.domain.stub.UserProfile;
import com.seezoon.user.server.domain.stub.UserProfile.Builder;
import java.time.Instant;
import java.time.ZoneId;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 查询用户profile
 *
 * @author dfenghuang
 * @date 2023/6/15 23:49
 */
@Component
@Validated
@Slf4j
@RequiredArgsConstructor
public class QryUserProfileExe {

    private final UserProfileRepository userProfileRepository;

    public QryUserProfileResp execute(@NotNull QryUserProfileReq req) {
        if (log.isDebugEnabled()) {
            log.debug("user profile req:{}", PbToJson.toJson(req));
        }
        UserProfilePO po = userProfileRepository.find(req.getUid());
        if (null == po) {
            log.warn("query user profile uid: {} not exists", req.getUid());
            return QryUserProfileResp.getDefaultInstance();
        }

        Builder builder = UserProfile.newBuilder();
        builder.setUid(po.getUid());
        if (null != po.getName()) {
            builder.setName(po.getName());
        }
        if (null != po.getMobile()) {
            builder.setMobile(po.getMobile());
        }
        if (null != po.getAvatar()) {
            builder.setAvatar(po.getAvatar());
        }
        if (null != po.getEmail()) {
            builder.setEmail(po.getEmail());
        }
        if (null != po.getBirthday()) {
            Instant instant = po.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant();
            builder.setBirthday(Timestamp.newBuilder().setSeconds(instant.getEpochSecond()).build());
        }
        if (null != po.getAddress()) {
            builder.setAddress(po.getAddress());
        }
        if (null != po.getCreateTime()) {
            builder.setCreateTime(PbTimestampUtil.to(po.getCreateTime()));
        }
        if (null != po.getUpdateTime()) {
            builder.setUpdateTime(PbTimestampUtil.to(po.getUpdateTime()));
        }
        QryUserProfileResp resp = QryUserProfileResp.newBuilder().setUserProfile(builder).build();

        if (log.isDebugEnabled()) {
            log.debug("user profile resp:{}", PbToJson.toJson(resp));
        }
        return resp;
    }
}
