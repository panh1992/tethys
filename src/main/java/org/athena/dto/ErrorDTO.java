package org.athena.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 统一错误返回实体
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDTO {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("host_id")
    private String hostId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

}
