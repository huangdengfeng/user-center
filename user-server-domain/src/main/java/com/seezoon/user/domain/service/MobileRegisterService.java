package com.seezoon.user.domain.service;

import com.seezoon.ddd.exception.ExceptionFactory;
import com.seezoon.mybatis.repository.constants.Constants;
import com.seezoon.user.domain.repository.RelationRepository;
import com.seezoon.user.domain.repository.UserProfileRepository;
import com.seezoon.user.domain.repository.UserRepository;
import com.seezoon.user.domain.repository.po.RelationPO;
import com.seezoon.user.domain.repository.po.UserPO;
import com.seezoon.user.domain.repository.po.UserProfilePO;
import com.seezoon.user.infrastructure.constants.RelationType;
import com.seezoon.user.infrastructure.constants.UserStatus;
import com.seezoon.user.infrastructure.error.ErrorCode;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 手机号注册
 *
 * @author dfenghuang
 * @date 2023/5/19 22:29
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Validated
@Transactional
public class MobileRegisterService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final UserProfileRepository userProfileRepository;

    public long register(@NotNull Long uid, @NotEmpty String mobile) {
        UserPO userPO = userRepository.findForUpdate(uid);
        if (null != userPO) {
            log.error("mobile register error due to uid [{}] used", uid);
            throw ExceptionFactory.bizException(ErrorCode.UID_USED.code(), ErrorCode.UID_USED.msg());
        }
        // 生成主记录
        this.createUser(uid);
        // 保存relation
        RelationPO relationPO = new RelationPO();
        relationPO.setUid(uid);
        relationPO.setRelationType(RelationType.MOBILE);
        relationPO.setRelationValue(mobile);
        relationPO.setStatus(Constants.NORMAL);
        relationRepository.save(relationPO);
        // 保存用户信息
        UserProfilePO userProfilePO = new UserProfilePO();
        userProfilePO.setUid(uid);
        userProfilePO.setMobile(mobile);
        userProfileRepository.save(userProfilePO);
        return uid;
    }

    private void createUser(Long uid) {
        UserPO userPO;
        // 生成用户记录
        userPO = new UserPO();
        userPO.setUid(uid);
        userPO.setStatus(UserStatus.NORMAL);
        userRepository.save(userPO);
    }
}
