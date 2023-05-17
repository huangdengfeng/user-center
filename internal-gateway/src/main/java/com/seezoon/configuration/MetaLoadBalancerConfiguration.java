package com.seezoon.configuration;

import com.seezoon.loadbalancer.GwLoadBalancerMetaProperties;
import com.seezoon.loadbalancer.MetaLoadBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 配置自定义的负载均衡
 *
 * @author dfenghuang
 * @date 2023/4/25 22:26
 * @see <a
 *         href="https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#switching-between-the-load-balancing-algorithms">https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#switching-between-the-load-balancing-algorithms</a>
 */
public class MetaLoadBalancerConfiguration {

    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(GwLoadBalancerMetaProperties gwLoadBalancerMetaProperties,
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        ObjectProvider<ServiceInstanceListSupplier> lazyProvider = loadBalancerClientFactory.getLazyProvider(name,
                ServiceInstanceListSupplier.class);
        if (gwLoadBalancerMetaProperties.isEnable()) {
            return new MetaLoadBalancer(gwLoadBalancerMetaProperties,
                    lazyProvider,
                    name);
        } else {
            return new RoundRobinLoadBalancer(lazyProvider, name);
        }

    }
}
