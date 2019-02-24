package org.athena.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response<T> {

    @JsonProperty("code")
    private String code = "SUCCESS";

    @JsonProperty("data")
    private T data;

    @JsonProperty("message")
    private String message;

    /**
     * 创建成功响应结果
     */
    public static <T> Response<T> build() {
        Response<T> response = new Response<>();
        response.setMessage("操作成功");
        return response;
    }

    /**
     * 根据返回数据创建响应结果
     *
     * @param data 返回数据
     */
    public static <T> Response<T> build(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setMessage("操作成功");
        return response;
    }

    /**
     * 根据返回结果及返回描述创建响应结果
     *
     * @param data    返回数据
     * @param message 描述信息
     */
    public static <T> Response<T> build(T data, String message) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    /**
     * 根据返回 错误代码、结果、描述 创建响应结果
     *
     * @param code    错误代码
     * @param data    返回数据
     * @param message 描述信息
     */
    public static <T> Response<T> build(String code, T data, String message) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

}
