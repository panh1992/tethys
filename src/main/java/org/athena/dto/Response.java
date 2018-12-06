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

    public static <T> Response<T> build(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setMessage("success");
        return response;
    }

    public static <T> Response<T> build(T data, String message) {
        Response<T> response = new Response<>();
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> build(String code, T data, String message) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

}
