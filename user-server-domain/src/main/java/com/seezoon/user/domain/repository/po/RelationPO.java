package com.seezoon.user.domain.repository.po;

import com.seezoon.mybatis.repository.po.BasePO;
import com.seezoon.user.domain.repository.po.RelationPO.RelationPK;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Getter
@Setter
@ToString
public class RelationPO extends BasePO<RelationPK> {

    private Long uid;

    private Integer relationType;

    private String relationValue;


    @Override
    public RelationPK getId() {
        return new RelationPK(uid, relationType, relationValue);
    }

    @Override
    public void setId(RelationPK pk) {
        super.setId(pk);
    }


    @Getter
    public static class RelationPK {

        @NotNull
        private Long uid;
        @NotNull
        private Integer relationType;
        @NotEmpty
        private String relationValue;

        public RelationPK(Long uid, Integer relationType, String relationValue) {
            this.uid = uid;
            this.relationType = relationType;
            this.relationValue = relationValue;
        }
    }
}