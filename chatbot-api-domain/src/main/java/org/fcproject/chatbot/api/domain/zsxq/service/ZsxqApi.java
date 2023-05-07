package org.fcproject.chatbot.api.domain.zsxq.service;

import org.fcproject.chatbot.api.domain.zsxq.IZsxqApi;
import org.fcproject.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * @Description:
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class ZsxqApi implements IZsxqApi {


    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        return null;
    }

    @Override
    public boolean answer(String groupId, String topicId, String cookie, String text, boolean silenced) throws IOException {
        return false;
    }
}
