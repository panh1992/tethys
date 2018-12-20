package org.athena.config.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class RedisConfig {

    @NotEmpty
    private String address;

    @NotEmpty
    private String password;

    @Min(0)
    @Max(15)
    @JsonProperty
    private int db = 0;

}
