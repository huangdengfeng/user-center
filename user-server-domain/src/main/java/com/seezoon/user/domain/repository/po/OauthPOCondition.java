package com.seezoon.user.domain.repository.po;


import com.seezoon.mybatis.repository.po.PagePOCondition;
import com.seezoon.mybatis.repository.sort.annotation.SortField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Getter
@Setter
@ToString
@SortField({"createTime:t.create_time"})
public class OauthPOCondition extends PagePOCondition {


}