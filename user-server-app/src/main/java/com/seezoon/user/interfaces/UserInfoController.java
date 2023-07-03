package com.seezoon.user.interfaces;

import com.seezoon.ddd.dto.Response;
import com.seezoon.user.application.dto.UserProfileCO;
import com.seezoon.user.application.dto.UserProfileQry;
import com.seezoon.user.application.executor.UserProfileQryExe;
import com.seezoon.user.infrastructure.utils.UidHeaderExtractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息服务
 *
 * @author dfenghuang
 * @date 2023/3/25 22:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
@Tag(name = "用户信息", description = "查询用户信息")
public class UserInfoController {

    private final UserProfileQryExe userProfileQryExe;

    @Operation(summary = "用户资料", description = "用户资料，手机号、姓名、头像等")
    @PostMapping("/profile")
    public Response<UserProfileCO> profile(HttpServletRequest request) {
        Long uid = UidHeaderExtractor.getMust(request);
        UserProfileQry qry = new UserProfileQry(uid);
        return userProfileQryExe.execute(qry);
    }

}
