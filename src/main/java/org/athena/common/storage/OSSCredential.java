package org.athena.common.storage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OSSCredential implements Credential {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String securityToken;

}
