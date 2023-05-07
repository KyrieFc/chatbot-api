package org.fcproject.chatbot.api.domain.zsxq.model.req;

/**
 * @Description: 请求问答接口信息
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class AnswerReq {

    private ReqData req_data;

    public AnswerReq(ReqData req_data) {
        this.req_data = req_data;
    }

    public ReqData getReq_data() {
        return req_data;
    }

    public void setReq_data(ReqData req_data) {
        this.req_data = req_data;
    }
}
