package com.seezoon.properties;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 用户路由规则，尾号路由
 *
 * @author dfenghuang
 * @date 2023/6/7 22:48
 */
@ConfigurationProperties(prefix = "router")
@Validated
@Getter
@Setter
public class RouterProperties {

    @Valid
    @NotNull
    private List<RouterItemProperties> items = Collections.emptyList();

    @Getter
    @Setter
    public static class RouterItemProperties {

        /**
         * 原始路由计算字段，比如user-id
         */
        @NotEmpty
        private String srcHeader;
        /**
         * 目的路由字段，比如user-set
         */
        @NotEmpty
        private String dstHeader;
        /**
         * 号段长度
         */
        @NotNull
        private Integer segmentLength;

        @Valid
        @NotNull
        private List<RouterRuleProperties> rules = Collections.emptyList();

    }

    @Getter
    @Setter
    public static class RouterRuleProperties {

        /**
         * 号段
         */
        @NotNull
        private Integer start;
        @NotNull
        private Integer end;
        @NotEmpty
        private String set;
    }
}
