package com.seezoon.user.domain.repository;


import com.seezoon.mybatis.repository.AbstractCrudRepository;
import com.seezoon.user.domain.repository.mapper.RelationMapper;
import com.seezoon.user.domain.repository.po.RelationPO;
import com.seezoon.user.domain.repository.po.RelationPO.RelationPK;
import org.springframework.stereotype.Repository;

/**
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Repository
public class RelationRepository extends AbstractCrudRepository<RelationMapper, RelationPO, RelationPK> {

}
