package com.hmall.item.es;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hmall.common.utils.BeanUtils;
import com.hmall.item.domain.po.Item;
import com.hmall.item.domain.po.ItemDoc;
import com.hmall.item.service.IItemService;

import cn.hutool.json.JSONUtil;

@SpringBootTest(properties = "spring.profiles.active=local")
public class ElasticDocumentTest {

    private RestHighLevelClient client;
    
    @Autowired
    private IItemService itemService;

    @Test
    void testConnect() {
        System.out.println("client = " + client);
    }

    @Test
    void testIndexDoc() throws IOException {
        // 0. 准备文档数据
        Item item = itemService.getById(100002644680L);
        ItemDoc itemDoc = BeanUtils.copyProperties(item, ItemDoc.class);
        System.out.println("itemDoc = " + itemDoc);

        //1. 准备request对象
        IndexRequest request = new IndexRequest("items").id(item.getId().toString());
        //2. 准备文档数据
        
        request.source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON);

        //3. 发送请求
        client.index(request, RequestOptions.DEFAULT);
       
    }

    @Test
    void testGetDoc() throws IOException {

        //1. 准备request对象
        GetRequest request = new GetRequest("items", "100002644680");
        //2. 发送请求
        GetResponse response = client.get(request, RequestOptions.DEFAULT);     
        //3. 解析结果
        String json = response.getSourceAsString();
        ItemDoc itemDoc = JSONUtil.toBean(json, ItemDoc.class);
        System.out.println("itemDoc = " + itemDoc);
    }

    @Test
    void testDeleteDoc() throws IOException {
        //1. 准备request对象
        DeleteRequest request = new DeleteRequest("items", "100002644680");
        //2. 发送请求
        client.delete(request, RequestOptions.DEFAULT);     
    }

    @Test
    void testUpdateDoc() throws IOException {
        //1. 准备request对象
        UpdateRequest request = new UpdateRequest("items", "100002644680");
        //2. 准备文档数据
        request.doc("price", 1000);
        //3. 发送请求
        client.update(request, RequestOptions.DEFAULT);     
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

    private static final String MAPPING_TEMPLATE = "";

}
