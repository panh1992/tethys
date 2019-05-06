package org.athena.common.storage;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class StsServiceSample {

    public static void main(String[] args) {
        String endpoint = "sts.aliyuncs.com";
        String accessKeyId = "<AccessKeyId>";
        String accessKeySecret = "<AccessKeySecret>";
        String roleSessionName = "athena";
        String roleArn = "<RoleArn>";
        String policy = "{\n \"Version\": \"1\",\n \"Statement\": [\n {\n \"Effect\": \"Allow\",\n "
                + "\"Action\": [\n \"oss:Get*\"\n ],\n \"Resource\": [\n \"acs:oss:*:*:athena-stoage\"\n ],\n"
                + " \"Condition\": {}\n }\n ]\n }";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "STS", endpoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy); // Optional
            final AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
    }

}
