package org.athena.common.storage.credential;

/**
 * 认证凭据
 */
public interface Credential {

    String getType();

    String getEndpoint();

    String getAccessKeyId();

    String getAccessKeySecret();

    String getSecurityToken();

}
