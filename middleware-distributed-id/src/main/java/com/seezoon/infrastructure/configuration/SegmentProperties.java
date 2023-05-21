package com.seezoon.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "id.server")
@Getter
@Setter
public class SegmentProperties {

    /**
     * 启动后获取一次提前缓存
     */
    private boolean initGet = true;
    /**
     * 启用鉴权
     */
    private boolean enableAuth;

}
