package com.seezoon.user.application.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/6/22 00:21
 */
@Getter
@Setter
public class UserProfileQry {

    @NotNull
    private Long uid;

    public UserProfileQry(Long uid) {
        this.uid = uid;
    }
}
