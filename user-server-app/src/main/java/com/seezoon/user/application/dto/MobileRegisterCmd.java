package com.seezoon.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据号注册
 *
 * @author dfenghuang
 * @date 2023/3/25 22:52
 */
@Getter
@Setter
public class MobileRegisterCmd {

    @Schema(title = "手机号")
    @NotEmpty
    private String mobile;

    @Schema(title = "验证码")
    @NotEmpty
    private String code;

}
