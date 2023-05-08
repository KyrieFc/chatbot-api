package org.fcproject.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import org.fcproject.chatbot.api.domain.ai.IOpenAI;
import org.fcproject.chatbot.api.domain.zsxq.IZsxqApi;
import org.fcproject.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.fcproject.chatbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Author: KyrieFc
 * @Date: 2023/5/8
 */

@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void run() {
        logger.info("开始执行定时任务");
        try {
            if (new Random().nextBoolean()) {
                logger.info("打烊中......");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
                logger.info("AI下班了");
                return;
            }

            //1.检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            logger.info("检索结果: {}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();

            if (null == topics || topics.isEmpty()) {
                logger.info("没有未回答的问题");
                return;
            }

            //2.AI回答
            Topics topic = topics.get(0);
            String answer = openAI.doChatGPT(topic.getQuestion().getText().trim());
            //3.回答问题
            boolean status = zsxqApi.answer(groupId, topic.getTopic_id(), cookie, answer, false);
            logger.info("编号：{} 问题：{} 回答：{} 状态：{}", topic.getTopic_id(), topic.getQuestion().getText(), answer, status);
        } catch (Exception e) {
            logger.error("执行回答问题定时任务异常", e);
        }
        logger.info("结束执行定时任务");
    }

}
