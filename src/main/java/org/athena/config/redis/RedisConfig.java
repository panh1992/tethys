package org.athena.config.redis;

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
    private int db = 0;

}
