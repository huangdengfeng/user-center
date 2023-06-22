package com.seezoon.filter;

import com.seezoon.properties.RouterProperties;
import com.seezoon.properties.RouterProperties.RouterItemProperties;
import com.seezoon.properties.RouterProperties.RouterRuleProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自动路由
 *
 * @author dfenghuang
 * @date 2023/6/8 00:04
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class AddRouterHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final RouterProperties routerProperties;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            for (RouterItemProperties item : routerProperties.getItems()) {
                ServerWebExchange serverWebExchange = this.route(exchange, item);
                if (null != serverWebExchange) {
                    return chain.filter(serverWebExchange);
                }
            }
            return chain.filter(exchange);
        };
    }


    private ServerWebExchange route(ServerWebExchange exchange, RouterItemProperties item) {
        ServerHttpRequest request = exchange.getRequest();
        List<String> srcRouterHeaders = request.getHeaders().get(item.getSrcHeader());
        if (CollectionUtils.isEmpty(srcRouterHeaders) || item.getRules().isEmpty()) {
            return null;
        }
        String srcRouterValue = srcRouterHeaders.get(0);
        if (srcRouterValue.length() < item.getSegmentLength()) {
            return null;
        }
        String segment = srcRouterValue.substring(
                srcRouterValue.length() - item.getSegmentLength());
        if (!NumberUtils.isCreatable(segment)) {
            return null;
        }
        Integer segmentInt = NumberUtils.createInteger(segment);
        for (RouterRuleProperties rule : item.getRules()) {
            if (segmentInt >= rule.getStart() && segmentInt <= rule.getEnd()) {
                final String set = rule.getSet();
                if (StringUtils.isNotEmpty(set)) {
                    log.debug("src router header:{} value:{},dst router header:{} value:{}",
                            item.getSrcHeader(), segment,
                            item.getDstHeader(), set);
                    ServerHttpRequest newRequest = exchange.getRequest().mutate()
                            .headers(httpHeaders -> {
                                httpHeaders.remove(item.getDstHeader());
                                httpHeaders.add(item.getDstHeader(), set);
                            }).build();
                    return exchange.mutate().request(newRequest).build();
                }
            }
        }
        return null;
    }
}
