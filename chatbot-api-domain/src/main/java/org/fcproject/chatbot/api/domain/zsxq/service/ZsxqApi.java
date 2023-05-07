package org.fcproject.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.fcproject.chatbot.api.domain.zsxq.IZsxqApi;
import org.fcproject.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.fcproject.chatbot.api.domain.zsxq.model.req.AnswerReq;
import org.fcproject.chatbot.api.domain.zsxq.model.req.ReqData;
import org.fcproject.chatbot.api.domain.zsxq.model.res.AnswerRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

@Service
public class ZsxqApi implements IZsxqApi {

    //注意这里的logger是org.slf4j.Logger，而不是java.util.logging.Logger
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", cookie);
        get.addHeader("content-type", "application/json; charset=utf-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
            //使用slf4j的logger打印日志
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err code is" + response.getStatusLine().getStatusCode());

        }
    }

    @Override
    public boolean answer(String groupId, String topicId, String cookie, String text, boolean silenced) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //topics后面这个是topic_id，需要替换成你自己的
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");

        post.addHeader("cookie", cookie);
        post.addHeader("content-type", "application/json; charset=utf-8");
        post.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10; Redmi K30 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.210 Mobile Safari/537.36");


/*      测试数据
        String paramJson = "{\"req_data\":{\"text\":\"自己去百度！\\n\",\"image_ids\":[],\"silenced\":false}}\n";
*/

        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        //视频中的JSONObject.fromObject()方法已经过时，这里使用的是JSONSerializer.toJSON()方法,将java对象转换成json字符串
        String paramJson = JSONSerializer.toJSON(answerReq).toString();

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "utf-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
            //create a log
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err code is" + response.getStatusLine().getStatusCode());
        }
    }
}
