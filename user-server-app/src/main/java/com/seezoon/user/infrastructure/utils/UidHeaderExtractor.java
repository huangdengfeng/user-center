package com.seezoon.user.infrastructure.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * 提取uid
 *
 * @author dfenghuang
 * @date 2023/6/23 00:07
 */
public class UidHeaderExtractor {

    private static final String X_USER_ID = "x-user-id";

    public static Long getMust(HttpServletRequest request) {
        return get(request, true);
    }


    public static Long get(HttpServletRequest request, boolean must) {
        if (null != request) {
            String uid = request.getHeader(X_USER_ID);
            if (StringUtils.isNotEmpty(uid)) {
                return Long.parseLong(uid);
            }
        }
        if (must) {
            throw new IllegalArgumentException("uid header not exists");
        }
        return null;
    }

}
