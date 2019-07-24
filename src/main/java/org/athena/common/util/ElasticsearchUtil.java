package org.athena.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElasticsearchUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    private static RestHighLevelClient client;

    public static RestHighLevelClient getClient(String hostname, int port, String scheme) {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        return client;
    }

    /**
     * 建立 elasticsearch 索引
     *
     * @param index  索引
     * @param source 存储数据
     * @throws IOException 创建索引异常
     */
    public static void index(String index, String source) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.source(source, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

}
