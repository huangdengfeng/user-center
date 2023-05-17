package com.seezoon;

import com.seezoon.configuration.MetaLoadBalancerConfiguration;
import com.seezoon.loadbalancer.GwLoadBalancerMetaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@SpringBootApplication
@LoadBalancerClients(defaultConfiguration = MetaLoadBalancerConfiguration.class)
@EnableConfigurationProperties(GwLoadBalancerMetaProperties.class)
public class MainApplication {

    public static void main(String[] args) {
        // for nacos
        System.setProperty("project.name", "external-gateway");
        SpringApplication.run(MainApplication.class, args);
    }
}
