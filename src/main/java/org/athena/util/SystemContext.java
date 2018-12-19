package org.athena.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.jackson.Jackson;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 系统上下文
 */
public final class SystemContext {

    private static final ThreadLocal<String> USER_ID_LOCAL = new ThreadLocal<>();

    private SystemContext() {
    }

    /**
     * 获取当前用户id
     */
    public static String getUserId() {
        return USER_ID_LOCAL.get();
    }

    /**
     * 设置当前用户id
     */
    public static void setUserId(String userId) {
        USER_ID_LOCAL.set(userId);
    }

    /**
     * 清楚当前用户id
     */
    public static void removeUserId() {
        USER_ID_LOCAL.remove();
    }

    /**
     * 获取系统 jackson 配置的 ObjectMapper 信息
     */
    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = Jackson.newObjectMapper();
        // 设置null值不参与序列化(字段不被显示)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 禁用空对象转换json校验
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置时间格式
        mapper.setDateFormat(new SimpleDateFormat(Constant.DATE_TIME_FORMAT));
        mapper.setTimeZone(TimeZone.getTimeZone(Constant.TIME_ZONE));
        return mapper;
    }

}
