package org.athena.common.storage;

/**
 * 认证凭据
 */
public interface Credential {

    String getEndpoint();

    String getAccessKeyId();

    String getAccessKeySecret();

    String getSecurityToken();

}
