package com.seezoon.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * 登录态处理
 *
 * @author dfenghuang
 * @date 2023/6/14 22:47
 * @see org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory
 * @see <a
 *         href="https://docs.spring.io/spring-cloud-gateway/docs/3.1.7/reference/html/#the-retry-gatewayfilter-factory">...</a>
 */
@Component
@Slf4j
public class SessionGatewayFilterFactory extends AbstractGatewayFilterFactory<SessionGatewayFilterFactory.Config> {

    private static final String COMMA = ",";
    private static final String SESSION_KEY = "sessionId";
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public SessionGatewayFilterFactory() {
        super(Config.class);
    }

    public static boolean setResponseStatus(ServerWebExchange exchange, HttpStatus httpStatus) {
        boolean response = exchange.getResponse().setStatusCode(httpStatus);
        if (!response && log.isWarnEnabled()) {
            log.warn("Unable to set status code to " + httpStatus + ". Response already committed.");
        }
        return response;
    }

    @Override
    public GatewayFilter apply(Config config) {
        config.validate();
        return (exchange, chain) -> {
            String excludes = config.getExcludes();
            if (StringUtils.isEmpty(excludes)) {
                ServerHttpRequest request = removeUserHeader(config, exchange);
                return chain.filter(exchange.mutate().request(request).build());
            }
            String path = exchange.getRequest().getPath().toString();
            String[] patterns = StringUtils.split(excludes, COMMA);
            boolean matched = false;
            for (String pattern : patterns) {
                if (antPathMatcher.match(pattern, path)) {
                    matched = true;
                }
            }
            // 不验证登录态
            if (matched) {
                ServerHttpRequest request = removeUserHeader(config, exchange);
                return chain.filter(exchange.mutate().request(request).build());
            } else {
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .headers(httpHeaders -> {
                            httpHeaders.remove(config.getUserHeader());
                            httpHeaders.add(config.getUserHeader(), "1000000341");
                        }).build();
//                if (1 == 2) {// 未登录
//                    setResponseStatus(exchange, HttpStatus.UNAUTHORIZED);
//                    return exchange.getResponse().setComplete();
//                }
                return chain.filter(exchange.mutate().request(request).build());
            }
        };
    }

    private ServerHttpRequest removeUserHeader(Config config, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.remove(config.getUserHeader())).build();
        return request;
    }

    @Getter
    @Setter
    public static class Config {

        /**
         * 用户信息头
         */
        private String userHeader;
        /**
         * 不包含路径，多个逗号分割。{@link org.springframework.util.AntPathMatcher}
         */
        private String excludes;

        public void validate() {
            Assert.isTrue(StringUtils.isNotEmpty(userHeader), "userHeader must be not empty");
        }

    }
}
