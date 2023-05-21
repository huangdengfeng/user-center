package com.seezoon.user.domain.repository;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Repository;

import com.seezoon.mybatis.repository.AbstractCrudRepository;
import com.seezoon.user.domain.repository.mapper.UserProfileMapper;
import com.seezoon.user.domain.repository.po.UserProfilePO;
import com.seezoon.user.domain.repository.po.UserProfilePOCondition;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Repository
public class UserProfileRepository extends AbstractCrudRepository<UserProfileMapper, UserProfilePO, Long> {

    @Transactional(readOnly = true)
    public UserProfilePO findByMobile(@NotBlank String mobile) {
        UserProfilePOCondition userProfilePOCondition = new UserProfilePOCondition();
        userProfilePOCondition.setMobile(mobile);
        return this.findOne(userProfilePOCondition);
    }
}
