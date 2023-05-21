package com.seezoon.user.domain.repository.mapper;

import com.seezoon.mybatis.repository.mapper.CrudMapper;
import com.seezoon.user.domain.repository.po.RelationPO;
import com.seezoon.user.domain.repository.po.RelationPO.RelationPK;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Mapper
public interface RelationMapper extends CrudMapper<RelationPO, RelationPK> {

}