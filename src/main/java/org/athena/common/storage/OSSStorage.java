package org.athena.common.storage;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import org.athena.common.storage.exception.StorageException;

/**
 * 阿里云 oss 存储
 */
public class OSSStorage implements Storage {

    // 默认分块大小 100KB
    private static final long PART_SIZE = 1024 * 100L;
    private static final int TASK_NUM = Runtime.getRuntime().availableProcessors() * 2;
    private OSS oss;

    /**
     * 根据认证参数实例化oss存储
     *
     * @param credential 认证参数
     */
    public OSSStorage(Credential credential) {

        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration config = new ClientBuilderConfiguration();

        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        config.setMaxConnections(200);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        config.setSocketTimeout(10000);
        // 设置建立连接的超时时间，默认为50000毫秒。
        config.setConnectionTimeout(10000);
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        config.setConnectionRequestTimeout(1000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        config.setIdleConnectionTime(10000);
        // 设置失败请求重试次数，默认为3次。
        config.setMaxErrorRetry(5);
        // 设置是否支持将自定义域名作为Endpoint，默认支持。
        config.setSupportCname(true);
        // 设置是否开启二级域名的访问方式，默认不开启。
        config.setSLDEnabled(true);
        // 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
        config.setProtocol(Protocol.HTTPS);

        // 创建OSSClient实例。
        OSSBuilder ossBuilder = new OSSClientBuilder();
        this.oss = ossBuilder.build(credential.getEndpoint(), credential.getAccessKeyId(),
                credential.getAccessKeySecret(), config);
    }

    /**
     * 将本地文件路径上传到 oss 存储上
     *
     * @param filePath  文件路径
     * @param container 存储空间
     * @param key       存储key
     * @return 存储文件的etg
     */
    public String upload(String filePath, String container, String key) {
        UploadFileRequest request = new UploadFileRequest(container, key, filePath, PART_SIZE, TASK_NUM);
        UploadFileResult result;
        try {
            result = oss.uploadFile(request);
        } catch (Throwable throwable) {
            throw new StorageException(throwable.getMessage());
        }
        oss.shutdown();
        return result.getMultipartUploadResult().getETag();
    }

}
