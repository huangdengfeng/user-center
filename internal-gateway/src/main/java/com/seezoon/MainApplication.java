package com.seezoon;

import com.seezoon.properties.RouterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.HttpProtocol;

@SpringBootApplication
@EnableConfigurationProperties(RouterProperties.class)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public HttpClientCustomizer h2ClientCustomizer() {
        return httpClient -> httpClient.protocol(HttpProtocol.H2C);
    }

}
