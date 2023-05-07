package org.fcproject.chatbot.api.domain.zsxq.model.res;

import org.fcproject.chatbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * @Description: 请求的结果数据
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class RespData {

    private List<Topics> topics;

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }
}
