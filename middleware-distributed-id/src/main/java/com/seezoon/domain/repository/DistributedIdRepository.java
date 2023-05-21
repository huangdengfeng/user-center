package com.seezoon.domain.repository;


import com.seezoon.domain.repository.mapper.DistributedIdMapper;
import com.seezoon.domain.repository.po.DistributedIdPO;
import com.seezoon.mybatis.repository.AbstractCrudRepository;
import javax.validation.constraints.NotEmpty;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * @author seezoon-generator 2022年2月7日 下午3:11:30
 */
@Repository
public class DistributedIdRepository extends AbstractCrudRepository<DistributedIdMapper, DistributedIdPO, String> {

    public DistributedIdPO updateMaxIdAndGet(@NotEmpty String bizTag) {
        int cnt = this.d.updateMaxId(bizTag);
        Assert.isTrue(cnt <= 1, "updateMaxIdAndGet bizTag [" + bizTag + "] affected rows rows " + cnt);
        return super.find(bizTag);
    }

    public DistributedIdPO updateMaxIdByStepAndGet(@NotEmpty String bizTag, int step) {
        int cnt = this.d.updateMaxIdByStep(bizTag, step);
        Assert.isTrue(cnt <= 1, "updateMaxIdByStepAndGet bizTag [" + bizTag + "] affected rows rows " + cnt);
        return super.find(bizTag);
    }
}
