package org.athena.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElasticsearchUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    private static RestHighLevelClient client;

    public static RestHighLevelClient getClient(String hostname, int port, String scheme) {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        return client;
    }

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));

        SearchRequest request = new SearchRequest("demo");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("age", "44"));

        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println("response status = " + response.status());

        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> map = hit.getSourceAsMap();
            System.out.println(mapper.writeValueAsString(map));

        }

        client.close();
    }

    private static void index() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));

        IndexRequest request = new IndexRequest("demo");
        request.source("{\n" +
                "    \"userId\": 56787643589840997,\n" +
                "    \"name\": \"欧阳振华\",\n" +
                "    \"age\": 44\n" +
                "}", XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println("response status = " + response.status());

        client.close();
    }

    private void delete() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));

        DeleteRequest request = new DeleteRequest("demo", "1");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        System.out.println("response status = " + response.status());

        client.close();
    }

    private void createIndex() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));


        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest("demo");

        // 2、设置索引的settings
        request.settings(Settings.builder().put("index.number_of_shards", 1) // 分片数
                .put("index.number_of_replicas", 1) // 副本数
                .put("analysis.analyzer.default.tokenizer", "ik_smart") // 默认分词器
        );

        String source = "{\n" +
                "    \"properties\": {\n" +
                "        \"userId\": {\n" +
                "            \"type\": \"long\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "            \"type\": \"text\"\n" +
                "        },\n" +
                "        \"age\": {\n" +
                "            \"type\": \"integer\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        // 3、设置索引的mappings
        request.mapping(source, XContentType.JSON);

        // 5、 发送请求
        // 5.1 同步方式发送请求
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

        // 6、处理响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse
                .isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);

        // 5.1 异步方式发送请求
            /*ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
                @Override
                public void onResponse(
                        CreateIndexResponse createIndexResponse) {
                    // 6、处理响应
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    boolean shardsAcknowledged = createIndexResponse
                            .isShardsAcknowledged();
                    System.out.println("acknowledged = " + acknowledged);
                    System.out.println(
                            "shardsAcknowledged = " + shardsAcknowledged);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("创建索引异常：" + e.getMessage());
                }
            };

            client.indices().createAsync(request, listener);
            */
        client.close();
    }

}
