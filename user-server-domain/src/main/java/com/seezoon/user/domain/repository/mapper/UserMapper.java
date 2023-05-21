package com.seezoon.user.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.seezoon.mybatis.repository.mapper.CrudMapper;
import com.seezoon.user.domain.repository.po.UserPO;

/**
 * 
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Mapper
public interface UserMapper extends CrudMapper<UserPO, Long> {

}