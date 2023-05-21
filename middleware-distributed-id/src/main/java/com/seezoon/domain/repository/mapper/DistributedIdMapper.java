package com.seezoon.domain.repository.mapper;

import com.seezoon.domain.repository.po.DistributedIdPO;
import com.seezoon.mybatis.repository.mapper.CrudMapper;
import javax.validation.constraints.NotEmpty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author seezoon-generator 2022年2月7日 下午3:11:30
 */
@Mapper
public interface DistributedIdMapper extends CrudMapper<DistributedIdPO, String> {

    int updateMaxId(@NotEmpty String bizTag);

    int updateMaxIdByStep(@NotEmpty @Param("bizTag") String bizTag, @Param("step") int step);
}