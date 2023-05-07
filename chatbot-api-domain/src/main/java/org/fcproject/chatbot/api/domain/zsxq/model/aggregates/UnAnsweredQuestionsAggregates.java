package org.fcproject.chatbot.api.domain.zsxq.model.aggregates;

import org.fcproject.chatbot.api.domain.zsxq.model.res.RespData;

/**
 * @Description: 未回答的问题聚合信息
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class UnAnsweredQuestionsAggregates {
    private boolean succeeded;
    private RespData resp_data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RespData getResp_data() {
        return resp_data;
    }

    public void setResp_data(RespData resp_data) {
        this.resp_data = resp_data;
    }
}
