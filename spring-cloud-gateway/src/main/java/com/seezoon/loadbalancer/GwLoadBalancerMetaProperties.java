package com.seezoon.loadbalancer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 网关元素路由配置，通常是为了实现单元化及单元化对等切换使用
 *
 * @author dfenghuang
 * @date 2023/4/24 22:42
 */
@Validated
@ConfigurationProperties(prefix = "spring.cloud.loadbalancer.meta")
@Getter
@Setter
public class GwLoadBalancerMetaProperties {

    private boolean enable = true;
    /**
     * set 标签，根据请求中的数据计算set 属性则高于该值
     */
    private String set;
    private String idc;
    private String city;

    /**
     * 自定义负载均衡元数据
     */
    private Map<String, String> custom = Collections.emptyMap();

    /**
     * 对等切换，可以使用配置中心动态下发
     */
    private ArrayList<PeerSet> peerSetSwitch;

    /**
     * 对等切换
     */
    @Getter
    @Setter
    public static class PeerSet {

        private String from;
        private String to;
        /**
         * 0-100
         */
        private int rate;

        @Override
        public String toString() {
            return "PeerSet{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", rate=" + rate +
                    '}';
        }
    }

}
