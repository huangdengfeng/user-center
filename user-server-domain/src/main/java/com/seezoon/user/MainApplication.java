package com.seezoon.user;

import com.seezoon.mybatis.repository.mapper.BaseMapper;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 入口程序
 *
 * @author dfenghuang
 * @date 2023/3/4 00:39
 */
@SpringBootApplication
@MapperScan(basePackages = "com.seezoon.user.domain.repository.mapper", markerInterface = BaseMapper.class)
@EnableDubbo(scanBasePackages = "com.seezoon.user.interfaces")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
