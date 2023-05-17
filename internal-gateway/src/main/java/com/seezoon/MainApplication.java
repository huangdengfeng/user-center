package com.seezoon;

import com.seezoon.configuration.MetaLoadBalancerConfiguration;
import com.seezoon.loadbalancer.GwLoadBalancerMetaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.HttpProtocol;

@SpringBootApplication
@LoadBalancerClients(defaultConfiguration = MetaLoadBalancerConfiguration.class)
@EnableConfigurationProperties(GwLoadBalancerMetaProperties.class)
public class MainApplication {

    public static void main(String[] args) {
        // for nacos
        System.setProperty("project.name", "internal-gateway");
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public HttpClientCustomizer h2ClientCustomizer() {
        return httpClient -> httpClient.protocol(HttpProtocol.H2C);
    }

}
