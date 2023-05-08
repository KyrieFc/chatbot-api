package org.fcproject.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.fcproject.chatbot.api.domain.ai.IOpenAI;
import org.fcproject.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import org.fcproject.chatbot.api.domain.ai.model.vo.Choices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: KyrieFc
 * @Date: 2023/5/8
 */

@Service
public class OpenAI implements IOpenAI {
    private Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Override
    public String doChatGPT(String question) throws IOException {
        HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setProxy(proxy)
                .build();

        //https://api.openai.com/v1/chat/
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("content-type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAiKey);

        //String paramJson = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"帮我实现一个java冒泡排序\"}],\"temperature\":0.7}";
        String paramJson = "{\\\"model\\\": \\\"text-davinci-003\\\", \\\"prompt\\\": \\\"" + question + "\\\", \\\"temperature\\\": 0, \\\"max_tokens\\\": 1024}";
        System.out.println(paramJson);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getText());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.openai.com Err code is " + response.getStatusLine().getStatusCode());
        }
    }

}
