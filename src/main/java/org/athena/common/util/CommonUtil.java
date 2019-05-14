package org.athena.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.jackson.Jackson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.athena.common.jackson.InstantDeserializer;
import org.athena.common.jackson.InstantSerializer;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * 通用帮助类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtil {

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

        SimpleModule instantModule = new SimpleModule("instant");
        instantModule.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        instantModule.addDeserializer(Instant.class, InstantDeserializer.INSTANCE);

        mapper.registerModule(instantModule);

        return mapper;
    }

    /**
     * 将数据转换为 byte 数组
     *
     * @param data 待转换字符串
     * @return byte 数组
     */
    public static byte[] convertBytes(String data) {

        return data.getBytes(StandardCharsets.UTF_8);

    }

}
