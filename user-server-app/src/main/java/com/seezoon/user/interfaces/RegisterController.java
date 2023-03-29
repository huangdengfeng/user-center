package com.seezoon.user.interfaces;

import com.seezoon.ddd.dto.Response;
import com.seezoon.user.application.dto.MobileRegisterCmd;
import com.seezoon.user.application.executor.MobileRegisterCmdExe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册服务
 *
 * @author dfenghuang
 * @date 2023/3/25 22:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
@Tag(name = "注册服务", description = "可提供多种注册方式")
public class RegisterController {

    private final MobileRegisterCmdExe mobileRegisterCmdExe;

    @Operation(summary = "手机号注册", description = "需要加强防刷逻辑")
    @PostMapping("/mobile")
    public Response mobileRegister(@RequestBody MobileRegisterCmd cmd) {
        return mobileRegisterCmdExe.execute(cmd);
    }

}
