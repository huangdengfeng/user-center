package com.seezoon.user.domain.repository.po;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.seezoon.mybatis.repository.po.BasePO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 *
 * @author seezoon-generator 2023年5月19日 下午9:51:20
 */
@Getter
@Setter
@ToString
public class UserProfilePO extends BasePO<Long> {

    private Long uid;

    private String name;

    private String mobile;

    private String avatar;

    private String email;

    private LocalDate birthday;

    private String address;


    @Override
    public Long getId() {
        return uid;
    }

    @Override
    public void setId(Long uid) {
        super.setId(uid);
        this.uid = uid;
    }
}