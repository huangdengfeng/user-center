package com.seezoon.user.domain.repository.po;

import com.seezoon.mybatis.repository.po.BasePO;
import com.seezoon.user.domain.repository.po.OauthPO.OauthPK;
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
public class OauthPO extends BasePO<OauthPK> {

    private Long uid;

    /**
     * auth类型
     */
    private Integer oauthType;

    private String oauthId;

    private String unionId;


    @Override
    public OauthPK getId() {
        return new OauthPK(uid, oauthType, oauthId);
    }

    @Override
    public void setId(OauthPK pk) {
        super.setId(pk);
    }

    @Getter
    public static class OauthPK {

        @NotNull
        private Long uid;
        @NotNull
        private Integer oauthType;
        @NotEmpty
        private String oauthId;

        public OauthPK(Long uid, Integer oauthType, String oauthId) {
            this.uid = uid;
            this.oauthType = oauthType;
            this.oauthId = oauthId;
        }
    }
}