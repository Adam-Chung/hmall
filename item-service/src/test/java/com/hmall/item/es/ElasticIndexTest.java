package com.hmall.item.es;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElasticIndexTest {

    private RestHighLevelClient client;

    @Test
    void testConnect() {
        System.out.println("client = " + client);
    }

    @Test
    void testCreateIndex() throws IOException {
        // 1. 準備request對象
        CreateIndexRequest request = new CreateIndexRequest("items");
        // 2. 準備請求安數
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3. 發送請求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testGetIndex() throws IOException {
        // 1. 準備request對象
        GetIndexRequest request = new GetIndexRequest("items");
        
        // 2. 發送請求
        // GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        // System.out.println("getIndexResponse = " + getIndexResponse);

        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("exists = " + exists);
    }

    @Test
    void testDeleteIndex() throws IOException {
        // 1. 準備request對象
        DeleteIndexRequest request = new DeleteIndexRequest("items");
        // 3. 發送請求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp() {
        // 创建一个客户端
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.111.128:9200")));
    }

    @AfterEach
    void tearDown() throws Exception {
        // 关闭客户端
        if (client != null) {
            client.close();
        }
    }

    private static final String MAPPING_TEMPLATE = "{\n" + //
            "  \"mappings\": {\n" + //
            "    \"properties\": {\n" + //
            "      \"id\": {\n" + //
            "        \"type\": \"keyword\"\n" + //
            "      },\n" + //
            "      \"name\":{\n" + //
            "        \"type\": \"text\",\n" + //
            "        \"analyzer\": \"ik_max_word\"\n" + //
            "      },\n" + //
            "      \"price\":{\n" + //
            "        \"type\": \"integer\"\n" + //
            "      },\n" + //
            "      \"stock\":{\n" + //
            "        \"type\": \"integer\"\n" + //
            "      },\n" + //
            "      \"image\":{\n" + //
            "        \"type\": \"keyword\",\n" + //
            "        \"index\": false\n" + //
            "      },\n" + //
            "      \"category\":{\n" + //
            "        \"type\": \"keyword\"\n" + //
            "      },\n" + //
            "      \"brand\":{\n" + //
            "        \"type\": \"keyword\"\n" + //
            "      },\n" + //
            "      \"sold\":{\n" + //
            "        \"type\": \"integer\"\n" + //
            "      },\n" + //
            "      \"commentCount\":{\n" + //
            "        \"type\": \"integer\",\n" + //
            "        \"index\": false\n" + //
            "      },\n" + //
            "      \"isAD\":{\n" + //
            "        \"type\": \"boolean\"\n" + //
            "      },\n" + //
            "      \"updateTime\":{\n" + //
            "        \"type\": \"date\"\n" + //
            "      }\n" + //
            "    }\n" + //
            "  }\n" + //
            "}";

}
