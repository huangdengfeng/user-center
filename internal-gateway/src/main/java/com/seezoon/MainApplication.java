package com.seezoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.HttpProtocol;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public HttpClientCustomizer h2ClientCustomizer() {
        return httpClient -> httpClient.protocol(HttpProtocol.H2C);
    }

}
