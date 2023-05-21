package com.seezoon.user.domain.repository.mapper;

import com.seezoon.mybatis.repository.mapper.CrudMapper;
import com.seezoon.user.domain.repository.po.OauthPO;
import com.seezoon.user.domain.repository.po.OauthPO.OauthPK;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Mapper
public interface OauthMapper extends CrudMapper<OauthPO, OauthPK> {

}