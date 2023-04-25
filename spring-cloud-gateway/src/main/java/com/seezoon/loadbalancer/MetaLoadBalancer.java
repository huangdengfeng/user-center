package com.seezoon.loadbalancer;

import com.seezoon.loadbalancer.GwLoadBalancerMetaProperties.PeerSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

/**
 * 元数据路由 + 单元化 + 对等切换
 *
 * @author dfenghuang
 * @date 2023/4/24 22:35
 * @see org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer
 */
@Slf4j
public class MetaLoadBalancer implements ReactorServiceInstanceLoadBalancer {


    private static final String metaPrefix = "meta.";
    private static final String metaCity = metaPrefix + "city";
    private static final String metaIdc = metaPrefix + "idc";
    private static final String metaSet = metaPrefix + "set";
    // 通过header动态路由，放set的值
    private static final String dynamicRouterHeader = "X-Router";
    private final GwLoadBalancerMetaProperties gwLoadBalancerMetaProperties;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private final String serviceId;
    private final AtomicInteger position;

    public MetaLoadBalancer(GwLoadBalancerMetaProperties gwLoadBalancerMetaProperties,
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            String serviceId) {
        this(gwLoadBalancerMetaProperties, serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    public MetaLoadBalancer(GwLoadBalancerMetaProperties gwLoadBalancerMetaProperties,
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId,
            int seedPosition) {
        this.gwLoadBalancerMetaProperties = gwLoadBalancerMetaProperties;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
        this.position = new AtomicInteger(seedPosition);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        String dynamicRouterHeaderValue = this.getDynamicRouterHeaderValue(request);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, dynamicRouterHeaderValue));
    }

    private String getDynamicRouterHeaderValue(Request request) {
        RequestDataContext context = (RequestDataContext) request.getContext();
        HttpHeaders headers = context.getClientRequest().getHeaders();
        List<String> dynamicRouterHeaders = headers.get(dynamicRouterHeader);
        String dynamicRouterHeaderValue =
                CollectionUtils.isEmpty(dynamicRouterHeaders) ? null : dynamicRouterHeaders.get(0);
        return dynamicRouterHeaderValue;
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
            List<ServiceInstance> serviceInstances, String dynamicRouterHeaderValue) {
        List<ServiceInstance> metaInstances = this.getMetaInstances(serviceInstances, dynamicRouterHeaderValue);
        Response<ServiceInstance> serviceInstanceResponse = getRoundRobinInstanceResponse(metaInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    /**
     * 根据元数据获取实例
     *
     * @param serviceInstances
     * @param dynamicRouterHeaderVlaue 动态路由
     * @return
     */
    private List<ServiceInstance> getMetaInstances(List<ServiceInstance> serviceInstances,
            String dynamicRouterHeaderVlaue) {
        if (serviceInstances.isEmpty()) {
            return Collections.emptyList();
        }
        String city = gwLoadBalancerMetaProperties.getCity();
        String idc = gwLoadBalancerMetaProperties.getIdc();
        String set = gwLoadBalancerMetaProperties.getSet();
        // 自定义的路由元数据
        Map<String, String> custom = gwLoadBalancerMetaProperties.getCustom();
        Map<String, String> metas = new HashMap<>(4);
        if (StringUtils.isNotEmpty(city)) {
            metas.put(metaCity, city);
        }
        if (StringUtils.isNotEmpty(idc)) {
            metas.put(metaIdc, idc);
        }
        if (StringUtils.isNotEmpty(set)) {
            metas.put(metaSet, set);
        }
        // 以动态路由的set为准
        if (StringUtils.isNotEmpty(dynamicRouterHeaderVlaue)) {
            metas.put(metaSet, dynamicRouterHeaderVlaue);
        }
        PeerSet peerSet = this.peerSwitch(metas.get(metaSet));
        // 有对等切换
        if (null != peerSet && StringUtils.isNotEmpty(peerSet.getTo())) {
            int rate = peerSet.getRate();
            // 1- 100
            int seed = new Random().nextInt(100) + 1;
            if (seed <= rate) {
                metas.put(metaSet, peerSet.getTo());
                log.info("serviceId:{},currentSet:{}, peer switch to:{}", serviceId, peerSet.getFrom(),
                        peerSet.getTo());
            }
        }
        metas.putAll(custom);
        if (log.isDebugEnabled()) {
            log.debug("serviceId:{},meta:{}", serviceId, metas);
        }
        List<ServiceInstance> result = new ArrayList<>(serviceInstances.size());
        for (ServiceInstance serviceInstance : serviceInstances) {
            Map<String, String> metadata = serviceInstance.getMetadata();
            boolean matched = true;
            for (Entry<String, String> entry : metas.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                if (!value.equals(metadata.get(key))) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                result.add(serviceInstance);
            }
        }
        return result;
    }

    private PeerSet peerSwitch(String currentSet) {
        if (StringUtils.isEmpty(currentSet)) {
            return null;
        }
        List<PeerSet> peerSetSwitch = gwLoadBalancerMetaProperties.getPeerSetSwitch();
        if (CollectionUtils.isEmpty(peerSetSwitch)) {
            return null;
        }
        log.info("serviceId:{},currentSet:{},peerSetSwitch config:{}", serviceId, currentSet, peerSetSwitch);
        Optional<PeerSet> first = peerSetSwitch.stream().filter(v -> currentSet.equals(v.getFrom())).findFirst();
        return first.orElse(null);
    }

    private Response<ServiceInstance> getRoundRobinInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        // Ignore the sign bit, this allows pos to loop sequentially from 0 to
        // Integer.MAX_VALUE
        int pos = this.position.incrementAndGet() & Integer.MAX_VALUE;

        ServiceInstance instance = instances.get(pos % instances.size());

        return new DefaultResponse(instance);
    }

}
