package org.fcproject.chatbot.api.domain.zsxq;

import org.fcproject.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * @Description: 知识星球API接口
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public interface IZsxqApi {
    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String topicId, String cookie, String text, boolean silenced) throws IOException;
}
