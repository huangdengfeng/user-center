package com.seezoon.user.domain.repository;


import com.seezoon.mybatis.repository.AbstractCrudRepository;
import com.seezoon.user.domain.repository.mapper.OauthMapper;
import com.seezoon.user.domain.repository.po.OauthPO;
import com.seezoon.user.domain.repository.po.OauthPO.OauthPK;
import org.springframework.stereotype.Repository;

/**
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Repository
public class OauthRepository extends AbstractCrudRepository<OauthMapper, OauthPO, OauthPK> {

}
