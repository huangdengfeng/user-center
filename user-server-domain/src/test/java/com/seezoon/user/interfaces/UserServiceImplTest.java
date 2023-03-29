package com.seezoon.user.interfaces;

import com.seezoon.protocol.user.server.domain.MobileRegisterCmd;
import com.seezoon.protocol.user.server.domain.UserService;
import com.seezoon.user.BaseSpringApplicationTest;
import java.io.IOException;
import java.util.Objects;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;

/**
 * 用户服务
 *
 * @author dfenghuang
 * @date 2023/3/22 00:27
 */
class UserServiceImplTest extends BaseSpringApplicationTest {

    @DubboReference
    private UserService userService;

    public static void main(String[] args) {
        Long i = 1000L;
        Long j = 1000L;
        System.out.println(Objects.equals(i, j));
    }

    @Test
    void mobileRegister() throws IOException {
        MobileRegisterCmd cmd = MobileRegisterCmd.newBuilder().setUid(1000L).setMobile("11111111").build();
        System.out.println(cmd.toString());
        try {
            userService.mobileRegister(cmd);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.in.read();
    }
}