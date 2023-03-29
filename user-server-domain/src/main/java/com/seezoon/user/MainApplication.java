package com.seezoon.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 入口程序
 *
 * @author dfenghuang
 * @date 2023/3/4 00:39
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.seezoon.user.interfaces")
public class MainApplication {

    public static void main(String[] args) {

        // for nacos
        System.setProperty("project.name", "user-server-domain");
        SpringApplication.run(MainApplication.class, args);
    }
}
