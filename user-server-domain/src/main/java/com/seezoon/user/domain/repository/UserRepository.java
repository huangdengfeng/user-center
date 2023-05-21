package com.seezoon.user.domain.repository;


import org.springframework.stereotype.Repository;

import com.seezoon.mybatis.repository.AbstractCrudRepository;
import com.seezoon.user.domain.repository.mapper.UserMapper;
import com.seezoon.user.domain.repository.po.UserPO;

/**
 * 
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Repository
public class UserRepository extends AbstractCrudRepository<UserMapper, UserPO, Long> {
}
