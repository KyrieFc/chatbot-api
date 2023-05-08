package org.fcproject.chatbot.api.test;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Description: Api test
 * Author: KyrieFc
 * Date: 2023/5/6
 */

public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88885255254212/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "zsxq_access_token=C4C1F6F1-1A60-8B1A-FAD7-C8AC407B4C1E_9ECAFBACA08D072F; zsxqsessionid=a33103bff4d488f5540a5c197a292bd0; abtest_env=product");
        get.addHeader("content-type", "application/json; charset=utf-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //topics后面这个是topic_id，需要替换成你自己的
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/584585548455854/answer");

        post.addHeader("cookie", "zsxq_access_token=C4C1F6F1-1A60-8B1A-FAD7-C8AC407B4C1E_9ECAFBACA08D072F; zsxqsessionid=a33103bff4d488f5540a5c197a292bd0; abtest_env=product");
        post.addHeader("content-type", "application/json; charset=utf-8");

        String paramJson = "{\"req_data\":{\"text\":\"自己去百度！\\n\",\"image_ids\":[],\"silenced\":false}}\n";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setProxy(proxy)
                .build();

        //https://api.openai.com/v1/chat/
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("content-type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        //String paramJson = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"帮我实现一个java冒泡排序\"}],\"temperature\":0.7}";
        String paramJson = "{\\\"model\\\": \\\"text-davinci-003\\\", \\\"prompt\\\": \\\"帮我写一个java冒泡排序\\\", \\\"temperature\\\": 0, \\\"max_tokens\\\": 1024}";
        System.out.println(paramJson);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
